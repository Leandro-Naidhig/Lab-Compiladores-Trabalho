/*
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package comp;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import ast.*;
import lexer.*;

public class Compiler {

	public Compiler() { }

	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		signalError = new ErrorSignaller(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, signalError);
		signalError.setLexer(lexer);
		Program program = null;
		next();
		program = program(compilationErrorList);
		return program;
	}

	private void error(String msg) {
		this.signalError.showError(msg, true);
	}

	private void next() {
		lexer.nextToken();
	}	

	private void check(Token shouldBe, String msg) {
		if(lexer.token != shouldBe) {
			error(msg);
		}
	}

	private Program program(ArrayList<CompilationError> compilationErrorList) {
		ArrayList<MetaobjectAnnotation> metaobjectCallList = new ArrayList<>();
		ArrayList<ClassDec> CianetoClassList = new ArrayList<>();
		Program program = new Program(CianetoClassList, metaobjectCallList, compilationErrorList);
		boolean thereWasAnError = false;
		while ( lexer.token == Token.CLASS ||
			   (lexer.token == Token.ID && lexer.getStringValue().equals("open") ) ||
			    lexer.token == Token.ANNOT ) {
			try {
				while ( lexer.token == Token.ANNOT ) {
					metaobjectAnnotation(metaobjectCallList);
				}
				CianetoClassList.add(classDec());
			}
			catch( CompilerError e) {
				// if there was an exception, there is a compilation error
				thereWasAnError = true;
		  }
		}

		if ( !thereWasAnError && lexer.token != Token.EOF ) {
			try {
				error("End of file expected");
			}
			catch( CompilerError e) {
			}
		}

		/*int flag = 0;

		//Verifica se existe a classe program
		for(ClassDec c: CianetoClassList) {
			if(c.getCname().equals("Program")){
				//System.out.println("LOL");
				flag = 1;
			}
		}

		//Caso nao encontrar a classe Program
		if(flag == 0) {
			System.out.println("LOL");
			error("Source code without a class 'Program'");
		}
		System.out.println("LOL");*/
		return program;
	}

	@SuppressWarnings("incomplete-switch")
	private void metaobjectAnnotation(ArrayList<MetaobjectAnnotation> metaobjectAnnotationList) {

		String name = lexer.getMetaobjectName();
		int lineNumber = lexer.getLineNumber();

		if(lexer.get_keywords(name) != null) {
			error(name + " is a keyword");
		}

		next();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		boolean getNextToken = false;

		if(lexer.token == Token.LEFTPAR) {		
			next();

			while(lexer.token == Token.LITERALINT || lexer.token == Token.LITERALSTRING ||
				  lexer.token == Token.ID) {
				switch(lexer.token) {
				
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case ID:
					metaobjectParamList.add(lexer.getStringValue());
				}

				next();

				if(lexer.token == Token.COMMA) {
					next();
				} else {
					break;
				}
			}

			if(lexer.token != Token.RIGHTPAR) {
				error("')' expected after annotation with parameters");
			} else { 
				getNextToken = true;
			}
		}

		switch(name) {

		case "nce":
			if(metaobjectParamList.size() != 0) {
				error("Annotation 'nce' does not take parameters");
			}
			break;
		
		case "cep":
			if(metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4){
				error("Annotation 'cep' takes three or four parameters");
			}			
			if(!( metaobjectParamList.get(0) instanceof Integer)) {
				error("The first parameter of annotation 'cep' should be an integer number");
			} else {
				int ln = (Integer ) metaobjectParamList.get(0);
				metaobjectParamList.set(0, ln + lineNumber);
			}
			if(!( metaobjectParamList.get(1) instanceof String) ||  !( metaobjectParamList.get(2) instanceof String)) {
				error("The second and third parameters of annotation 'cep' should be literal strings");
			}
			if(metaobjectParamList.size() >= 4 && !( metaobjectParamList.get(3) instanceof String)) {
				error("The fourth parameter of annotation 'cep' should be a literal string");
			}
			break;

		case "annot":
			if(metaobjectParamList.size() < 2 ) {
				error("Annotation 'annot' takes at least two parameters");
			}
			for(Object p : metaobjectParamList) {
				if(!(p instanceof String)) {
					error("Annotation 'annot' takes only String parameters");
				}
			}
			if(!((String ) metaobjectParamList.get(0)).equalsIgnoreCase("check"))  {
				error("Annotation 'annot' should have \"check\" as its first parameter");
			}
			break;
		
		default:
			error("Annotation '" + name + "' is illegal");
		}

		metaobjectAnnotationList.add(new MetaobjectAnnotation(name, metaobjectParamList));
		
		if(getNextToken){
			next();
		}
	}

	private ClassDec classDec() {
		
		String className = "";
		String superclassName = "";
		MemberList memberlist = null;
		ClassDec classe = null;
		ClassDec superclass = null;
		Boolean isopen = false;

		if(lexer.token == Token.ID && lexer.getStringValue().equals("open")) {	
			isopen = true;
			next();
		}

		if(lexer.token != Token.CLASS) {
			next();
			error("'class' expected");
		} else {
			next();
		}

		if (lexer.token != Token.ID) {
			error("Identifier expected");
		} else {
			className = lexer.getStringValue();
		}

		checkKeyWords(className);

		//Analise Semanica (verificacao de existencia da classe)
		if (symbolTable.getInGlobal(className) != null) {
			error("Class '" + className + "' has already been declared");
		}
		
		next();

		if (lexer.token == Token.EXTENDS) {
			next();
			superclassName = lexer.getStringValue();
			next();

			//Analise Semanica (verificacao se e uma palavra chave)
			if(lexer.get_keywords(superclassName) != null) {
				error("Superclass name '" + superclassName + "' is a keyword");
			}
		
			//Verifica se a classe existe na tabela de simbolos
			superclass = (ClassDec)symbolTable.getInGlobal(superclassName);
			
			//Analise Semanica (verificacao de existencia da superclasse)
			if(superclass == null) {
				error("Class '" + superclassName + "' has not been declared");
			}
		}

		currentClass = new ClassDec(className, superclass, memberlist, isopen);
		memberlist = memberList();

		if(lexer.token != Token.END) {
			next();
			error("'end' expected");
		} else {
			next();
		}

		//Verifica se existe o metodo run na classe Program
		if(currentClass.getCname().equals("Program")) {
			int flag = 0;
			for(Member s: memberlist.getArray()) {
				if(s instanceof MethodDec) {
					//Caso tiver o metodo
					if(((MethodDec)s).getName().equals("run")) {
						flag = 1;
					}
				}
			}
			//Caso nao foi encontrado
			if(flag == 0) {
				error("Method 'run' was not found in class 'Program'");
			}
		}

		//Limpeza de todas as tabelas de simbolos
		symbolTable.removeLocalIdentMethodFieldClass();
		symbolTable.removeLocalIdentMethodVariablesClass();
		symbolTable.removeLocalIdentMethodParents();
		symbolTable.removeLocalIdentMethodCurrentClass();

		classe = new ClassDec(className, superclass, memberlist, isopen);
		symbolTable.putInGlobal(className, classe);
		return classe;
	}

	private MemberList memberList() {

		ArrayList<Member> members = new ArrayList<>();
		Member membro = null;

		while(true) {

			String first = "";
			String second = "";
			String third = "";
			String qual = null;

			//Recupera o primeiro qualificador
			if (lexer.token == Token.PRIVATE) {
				first = lexer.getStringValue();
				qual = first;
				next();
			
			} else if (lexer.token == Token.PUBLIC) {
				first = lexer.getStringValue();
				qual = first;
				next();
			
			} else if (lexer.token == Token.OVERRIDE) {
				first = lexer.getStringValue();
				qual = first;
				next();
				
				if (lexer.token == Token.PUBLIC) {
					second = lexer.getStringValue();
					qual = qual + " " + second;
					next();
				
				} else if(lexer.token == Token.PRIVATE || lexer.token == Token.OVERRIDE 
					   || lexer.token == Token.FINAL){
					error("Illegal qualifier");
				
				} else {
					qual = qual + " public";
				}
			
			} else if (lexer.token == Token.FINAL) {
				first = lexer.getStringValue();
				qual = first;
				next();
				
				if (lexer.token == Token.PUBLIC) {
					second = lexer.getStringValue();
					qual = qual + " " + second;
					next();
				
				} else if (lexer.token == Token.OVERRIDE) {
					second = lexer.getStringValue();
					qual = qual + " " + second;
					next();
				
					if ( lexer.token == Token.PUBLIC ) {
						third = lexer.getStringValue();
						qual = qual + " " + third;
						next();
					
					} else if(lexer.token == Token.PRIVATE || lexer.token == Token.OVERRIDE 
						   || lexer.token == Token.FINAL){
						error("Illegal qualifier");
			  		}
				
				} else if(lexer.token == Token.PRIVATE || lexer.token == Token.FINAL){
			 		error("Illegal qualifier");
		 		}
			}

			if(lexer.token == Token.PUBLIC || lexer.token == Token.PRIVATE
			|| lexer.token == Token.OVERRIDE || lexer.token == Token.FINAL){
		 		error("Illegal qualifier");
	 		}
			
			if(lexer.token == Token.VAR) {

				if(qual != null) {
					if(!(qual.equals("private"))) {
						error("Instance variable cannot have qualifier other than 'private'");
					}
				}

				next();
				membro = fieldDec("private"); //Qualificador padrao do cianeto

			} else if(lexer.token == Token.FUNC) {
				next();

				if(first.equals("final") && (currentClass.getOpen() == false)) {
					error("Can not declare a final because class '" + currentClass.getCname() + "' is final");
				}

				if(qual == null) { //Qualificador padrao do cianeto
					qual = "public";
				}

				membro = methodDec(qual);
	
			} else {
				break;
			}

			members.add(membro);
			MemberList membros = new MemberList(members);

			//Coloca os membros atuais na classe atual temporaria
			currentClass.setMembros(membros);
		}
		
		return new MemberList(members);
	}

	private MethodDec methodDec(String qualificador) {
		
		Id id = null;
		int flag = 0;
		String name = null;
		Type tipo = null;
		FormalParamDec formparaDec = null;
		MethodDec metodo = null;

		if(lexer.token == Token.ID) {

			name = lexer.getStringValue();
			checkKeyWords(name);

			//Analise Semanica (verificacao de existencia da funcao ou de uma variavel com o mesmo nome)
			if(symbolTable.getInLocalMethodFieldClass(name) != null) {

				if(symbolTable.getInLocalMethodFieldClass(name) instanceof Variable) {
					error("Cannot declare a unary method with the same name as instance variable");
			
				} else {
					error("Method '" + name + "' has already been declared");
				}
			
			} else {
				id = new Id(name);
			}

			//Analise Semanica (verificacao de existencia da funcao com a mesma descricao na propria classe)
			if(symbolTable.getInLocalMethodFieldClass(id.getName()) != null) {
				if(symbolTable.getInLocalMethodFieldClass(id.getName()) instanceof MethodDec) {
					error("Method '" + id.getName() + "' has already been declared");
				}			
			}

			//Verificacao se existe um metodo com a mesma descricao na superclasse
			if(currentClass.getSuperClass() != null && currentClass.getSuperClass().getOpen() == true) {
				
				//Pega os metodos e variaveis da superclasse
				checkMethodsCurrentClass(currentClass.getSuperClass());

				ClassDec classe = currentClass.getSuperClass();

				if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) != null 
				&& symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {

					metodo = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());
					
					//Caso tiverem a mesma descricao os metodos
					if(classe.getSuperClass() == null) {
						if(!(qualificador.equals("override")) && !(qualificador.equals("override public")) 
						  && !(metodo.getQualifier().equals("private"))) {
							error("'override' expected before overridden method '" + id.getName() + "'");
						}
					} else {
						if((!(metodo.getQualifier().equals("override")) && !((metodo.getQualifier().equals("override public"))))) {
							
							//Se o metodo da superclasse nao for privado
							if(!(metodo.getQualifier().equals("private"))) {
								error("'override' expected before overridden method '" + id.getName() + "'");
							}
						}
					}
				}
			}

			next();

		} else if(lexer.token == Token.IDCOLON) {
			
			name = lexer.getStringValue();
			next();
			formparaDec = formalParamDec();
			id = new Id(name);
			checkKeyWords(id.getName());

			//Se a classe for program e o metodo run tiver parametros
			if(id.getName().equals("run")) {
				error("Method 'run' of class 'Program' cannot take parameters");
			}

			//Analise Semanica (verificacao de existencia da funcao com a mesma descricao na propria classe)
			if(symbolTable.getInLocalMethodFieldClass(id.getName()) != null) {
				
				if(symbolTable.getInLocalMethodFieldClass(id.getName()) instanceof MethodDec) {
					
					metodo = (MethodDec)symbolTable.getInLocalMethodFieldClass(id.getName());
				
					//Verifica o numero de parametros entre o metodo recuperado e o atual
					if(metodo.getNumParam() == formparaDec.getNumberParam()) {
						
						//Vetores de parametros
						ArrayList<ParamDec> parMetodo = new ArrayList<>();
						ArrayList<ParamDec> parMetodoAtual = new ArrayList<>();

						//Transforma em iteradores
						Iterator<ParamDec>parsMetodo = parMetodo.iterator();
						Iterator<ParamDec>parsMetodoAtual = parMetodoAtual.iterator();

						//flag de verificacao da igualdade de tipos
						boolean equals = false;

						//Percorre os dois vetores verificando se sao similares em seus tipos
						while(parsMetodo.hasNext() && parsMetodoAtual.hasNext()){
							if(parsMetodo.next().getType() != parsMetodoAtual.next().getType()) {
								equals = true;
								break;
							}
						}

						//Caso tiverem a mesma descricao os metodos
						if(equals){
							error("Method '" + id.getName() + "' has already been declared with the same parameters");
						}
					}
				}			
			}

			//Verificacao se existe um metodo com a mesma descricao na superclasse
			if(currentClass.getSuperClass() != null && currentClass.getSuperClass().getOpen() == true) {
				
				//Pega os metodos e variaveis da superclasse
				checkMethodsCurrentClass(currentClass.getSuperClass());

				if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) != null 
				&& symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {


					metodo = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());

					//Verifica o numero de parametros entre o metodo recuperado e o atual
					if(metodo.getNumParam() == formparaDec.getNumberParam()) {
						
						//Vetores de parametros
						ArrayList<ParamDec> parMetodo = new ArrayList<>();
						ArrayList<ParamDec> parMetodoAtual = new ArrayList<>();

						//Transforma em iteradores
						Iterator<ParamDec>parsMetodo = parMetodo.iterator();
						Iterator<ParamDec>parsMetodoAtual = parMetodoAtual.iterator();

						//flag de verificacao da igualdade de tipos
						boolean equals = false;

						//Percorre os dois vetores verificando se sao similares em seus tipos
						while(parsMetodo.hasNext() && parsMetodoAtual.hasNext()){
							if(parsMetodo.next().getType() != parsMetodoAtual.next().getType()) {
								equals = true;
								break;
							}
						}

						//Caso tiverem a mesma descricao os metodos
						if(equals && (!(metodo.getQualifier().equals("override")) || !((metodo.getQualifier().equals("override public"))) )) {
							error("'override' expected before overridden method '" + id.getName() + "'");
						}
					}
				}
			}	

		} else {
			error("An identifier or identifer: was expected after 'func'");
		}

		//Caso o metodo seja run e nao seja publico
		if(id.getName().equals("run")) {
			if(qualificador.equals("private")) {
				error("Method 'run' of class 'Program' cannot be private");
			}
		}

		if(lexer.token == Token.MINUS_GT) {
			next();
			tipo = type();
			next();
		}

		if(lexer.token != Token.LEFTCURBRACKET) {
			next();
			error("'{' expected");
		} else {
			next();
		}

		StatementList statlist = statementList();
		check(Token.RIGHTCURBRACKET, "'}' expected");
		next();

		//Flag com indicacao permanente
		int flagPermElse = 0;
		int flagPermIf = 0;
		int flagIf = 0;
		int flagElse = 0;

		//Verifica se pelo menos existe um retorno no metodo
		for(Statement s: statlist.getArray()) {
			
			//Para verificar se existe return em "IF"
			if(s instanceof IfStat) {
				flagIf = 1;
				for(Statement m: ((IfStat)s).getArrayIf()) {
					if(m instanceof ReturnStat) {
						flagPermIf = 1;
					}
				}
				if(((IfStat)s).getArrayElse() != null){
					for(Statement m: ((IfStat)s).getArrayElse()) {
						if(m instanceof ReturnStat) {
							flagPermElse = 1;
						}
					}	
				}
			}
			
			if(s instanceof ReturnStat) {
				flag = 1;
			}
		}

		//Caso nao tenha sido encontrado
		if(flag == 0 && tipo != null) {
			if( (flagIf == 1 && flagPermIf == 1) && 
				(flagElse == 1 && flagPermElse == 1) && 
				currentClass.getCname().equals("run")) {
				error("Method 'run' of class 'Program' with a return value type");

			} else if( (flagIf == 1 && flagPermIf == 0) ||
					   (flagElse == 1 && flagPermElse == 0) ||
					   (flagIf == 0) ) {
						error("Missing 'return' statement in method '" + id.getName()  + "'");
				
			} else if(flagIf == 0) {
				error("Missing 'return' statement in method '" + id.getName()  + "'");
			}
		
		} else if(flag == 1 && tipo == null) {
			error("Illegal 'return' statement. Method '"+ id.getName() +"' of class '" + currentClass.getCname() + "' return void");
		}
		metodo = new MethodDec(id, formparaDec, tipo, statlist, qualificador);

		//Coloca o metodo na tabela de simbolos locais da classe
		symbolTable.putInLocalMethodFieldClass(id.getName(), metodo);

		//Limpeza da tabela de simbolos do metodo
		symbolTable.removeLocalIdentMethodVariablesClass();

		return metodo;
	}

	private FieldDec fieldDec(String qualificador) {
		
		IdList idlist = null;
		Boolean isSemiColon = false;
		Type tipo = type();
		next();
		
		if(lexer.token != Token.ID) {
			error("A field name was expected");
		
		} else {
			idlist = idList(tipo, false);
		}

		if(lexer.token == Token.SEMICOLON) {
			isSemiColon = true;
			next();
		}

		return new FieldDec(tipo, idlist, isSemiColon, qualificador);
	}

	private IdList idList(Type tipo, Boolean varLocal) {

		ArrayList<Variable> identifiers = new ArrayList<>();	
		Variable id = null;
		MethodDec metodo = null;

		do{

			//Condicao responsavel por pular a virgula para continuar o loop
			if(lexer.token == Token.COMMA) {
				next();
			}

			if(lexer.token != Token.ID) {
				error("Identifier expected");
			} else {
	
				checkKeyWords(lexer.getStringValue());
	
				if(!varLocal) {
	
					//Analise Semantica (verificacao se ja existe o identificador)
					if (symbolTable.getInLocalMethodFieldClass(lexer.getStringValue()) != null) {
	
						//Caso for uma variavel
						if(symbolTable.getInLocalMethodFieldClass(lexer.getStringValue()) instanceof Variable) {
							error("Identifier '" + lexer.getStringValue() + "' has already been declared in class");
	
						//Caso for um metodo
						} else {
	
							metodo = (MethodDec)symbolTable.getInLocalMethodFieldClass(lexer.getStringValue());
	
							//Caso for um metodo unario
							if(metodo.getNumParam() == 0) {
								error("Cannot declare a unary method with the same name as instance variable");
							}
						}
					
					//Caso a variavel de instancia ainda nao foi declarada
					} else {
						id = new Variable(lexer.getStringValue(), tipo); 
						identifiers.add(id);
						symbolTable.putInLocalMethodFieldClass(lexer.getStringValue(), id);
					}
				
				} else {
					
					//Analise Semantica (verificacao se ja existe o identificador)
					if (symbolTable.getInLocalMethodVariablesClass(lexer.getStringValue()) != null) {
						error("Identifier '" + lexer.getStringValue() + "' has already been declared in method");
					
					//Caso a variavel local ainda nao foi declarada
					} else {
						id = new Variable(lexer.getStringValue(), tipo); 
						identifiers.add(id);
						symbolTable.putInLocalMethodVariablesClass(lexer.getStringValue(), id);
					}
				}	
			}
	
			next();

		} while(lexer.token == Token.COMMA);
		
		return new IdList(identifiers);
	}

	private FormalParamDec formalParamDec() {

		ArrayList<ParamDec> params = new ArrayList<>();
		params.add(paramDec());

		while(lexer.token == Token.COMMA) {
			next();
			params.add(paramDec());	
		}
		return new FormalParamDec(params);
	}

	private ParamDec paramDec() {

		Variable name = null;
		Type tipo = type();
		next();

		if(lexer.token == Token.ID) {

			checkKeyWords(lexer.getStringValue());
			name = new Variable(lexer.getStringValue(), tipo);
			symbolTable.putInLocalMethodVariablesClass(lexer.getStringValue(), name);

		} else {
			error("An identifier was expected after type");
		}

		next();
		return new ParamDec(name);
	}

	private StatementList statementList() {
		
		ArrayList<Statement> statements = new ArrayList<>();

		while(lexer.token == Token.IF || lexer.token == Token.WHILE || lexer.token == Token.RETURN 
		   || lexer.token == Token.BREAK || lexer.token == Token.REPEAT || lexer.token == Token.ID
		   || lexer.token == Token.VAR || lexer.token == Token.ASSERT || lexer.token == Token.SUPER 
		   || lexer.token == Token.SELF || lexer.token == Token.SEMICOLON
		   || (lexer.token == Token.ID && lexer.getStringValue().equals("Out")) ) {
			statement(statements);
		}

		if(lexer.token == Token.ELSE) {
			next();
			error("Statement expected");
		
		} else if((lexer.token == Token.IDCOLON && lexer.getStringValue().equals("println:")) ||
				  (lexer.token == Token.IDCOLON && lexer.getStringValue().equals("print:"))) {
			
			next();
			error("Missing 'Out.'");
		}

		return new StatementList(statements);
	}

	private void statement(ArrayList<Statement> statements) {

		boolean checkSemiColon = true;
		switch(lexer.token) {
			case IF:
				statements.add(ifStat());
				checkSemiColon = false;
				break;
			case WHILE:
				statements.add(whileStat());
				checkSemiColon = false;
				break;
			case RETURN:
				statements.add(returnStat());
				break;
			case BREAK:
				statements.add(breakStat());
				break;
			case SEMICOLON:
				break;
			case REPEAT:
				statements.add(repeatStat());
				break;
			case VAR:
				statements.add(localDec());
				break;
			case ASSERT:
				statements.add(assertStat());
				break;
			default:
				if(lexer.token == Token.ID && lexer.getStringValue().equals("Out")) {
					statements.add(writeStat());

				} else {	
					statements.add(assignExpr());
				}
		}

		if(checkSemiColon) {
			check(Token.SEMICOLON, "';' expected");
			next();
		}
	}

	private IfStat ifStat() {
		
		ArrayList<Statement> statIf = new ArrayList<>();
		ArrayList<Statement> statElse = null;
		next();
		Expr expressao = expression();

		if (expressao.getType() != Type.booleanType) {
			error("Expected 'Boolean' expression type");
		}

		check(Token.LEFTCURBRACKET, "'{' expected after the 'if' expression");
		next();
		
		while(lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END) {
			statement(statIf);
		}

		check(Token.RIGHTCURBRACKET, "'}' was expected");
		next();
		
		if (lexer.token == Token.ELSE) {
			statElse = new ArrayList<>();
			next();
			check(Token.LEFTCURBRACKET, "'{' expected after 'else'");
			next();
			while(lexer.token != Token.RIGHTCURBRACKET) {
				statement(statElse);
			}
			check(Token.RIGHTCURBRACKET, "'}' was expected");
			next();
		}
		return new IfStat(expressao, statIf, statElse);
	}

	private AssignExpr assignExpr() {

		Type classe = null;
		int flag = 0; 
		Expr exprLeft = expression();
		Expr exprRight = null;

		if(lexer.token == Token.ASSIGN) {
			next();
			exprRight = expression();

			//Analise Semantica (primeiro caso de verificacao)
			if( (exprLeft.getType() == Type.booleanType && exprLeft.getType() != exprRight.getType())
			|| (exprLeft.getType() == Type.intType && exprLeft.getType() != exprRight.getType())
			|| (exprLeft.getType() == Type.stringType && exprLeft.getType() != exprRight.getType()) ){
				error("Assignment of different types");
			}

			//Analise Semantica (segundo caso de verificacao)
			if(exprRight.getType() instanceof ClassDec && exprLeft.getType() instanceof ClassDec) {
				
				classe = exprRight.getType();

				while(exprLeft.getType() != classe) {
					
					//Se a classe herdar de outra classe
					if(((ClassDec)classe).getSuperClass() != null) {
						
						//Se a classe puder ser herdada
						if(((ClassDec)classe).getSuperClass().getOpen()) {
							classe = ((ClassDec)classe).getSuperClass();

							if(classe == exprLeft.getType()) {
								flag = 1;
							}

						} else {
							error("Right expression does not inherit from left expression");
						}
					
					} else {
						error("Right expression does not inherit from left expression");
					}	
				}

				if(exprLeft.getType() == classe && flag == 0) {
					flag = 1;
				}
			}

			//Analise Semantica (terceiro caso de verificacao)
			if(exprLeft.getType() instanceof ClassDec && !(exprRight instanceof NilExpr) && flag == 0) {
				error("Was expected to right expression inherit from the left, or a 'nil' assignment");
			}
		}

		//Analise Semantica (terceiro caso de verificacao)
		if(exprLeft.getType() instanceof ClassDec && !(exprRight instanceof NilExpr) && flag == 0) {
			error("Was expected to right expression inherit from the left, or a 'nil' assignment");
		}

		//Analise Semantica (terceiro caso de verificacao (fora))
		if( ((exprLeft.getType() == Type.booleanType) 
		   || (exprLeft.getType() == Type.intType)
		   || (exprLeft.getType() == Type.stringType)) 
		  && exprRight == null){
			error("Right expression from assignment not found");
		}
		
		return new AssignExpr(exprLeft, exprRight); 
	}

	private ExpressionList expressionList() {

		ArrayList<Expr> exprList = new ArrayList<>();
		exprList.add(expression());

		while(lexer.token == Token.COMMA) {
			next();
			exprList.add(expression());
		}
		return new ExpressionList(exprList);
	}

	private Expr expression() {

		Token relation = null;
		Expr exprRight = null;
		Expr exprLeft = simpleExpression();

		if(lexer.token == Token.EQ || lexer.token == Token.GT || lexer.token == Token.LT || 
		lexer.token == Token.GE || lexer.token == Token.LE || lexer.token == Token.NEQ) {
			relation = lexer.token;
			next();
			exprRight = simpleExpression();

			//Analise Semantica
			if (!checkRelExpr(exprLeft.getType(), exprRight.getType())) {
				error("Incompatible expressions type for relationship");
			}
			exprLeft = new CompositeExpr(exprLeft, relation, exprRight);
		}
		return exprLeft;
	}

	private Expr simpleExpression() {

		Token token = null;
		Expr exprRight = null;
		Expr exprLeft = sumSubExpression();
		
		while(lexer.token == Token.PLUSPLUS) {
			token = lexer.token;
			next();
			exprRight = sumSubExpression();


			//Analise Semantica
			if ( (exprLeft.getType() != Type.intType && exprLeft.getType() != Type.stringType)
			  || (exprRight.getType() != Type.intType && exprRight.getType() != Type.stringType)) {
				error("Incompatible expressions type for relationship");
			}

			exprLeft = new CompositeExpr(exprLeft, token, exprRight);
		}
		return exprLeft;
	}

	private Expr sumSubExpression() {

		Token Lowoperator = null;
		Expr exprLeft = term();
		Expr exprRight = null;

		while(lexer.token == Token.PLUS || lexer.token == Token.MINUS || lexer.token == Token.OR) {
			Lowoperator = lexer.token;
			next();
			exprRight = term();

			//Analise Semantica
			if(Lowoperator == Token.OR) {
				if (!checkRelExpr(exprLeft.getType(), exprRight.getType())) {
					error("Incompatible expressions type for relationship");
				}
			} else {
				if (!checkMathExpr(exprLeft.getType(), exprRight.getType())) {
					error("'Int' expression type expected to perform this operation");
				}
			}
			
			exprLeft = new CompositeExpr(exprLeft, Lowoperator, exprRight);
		}
		return exprLeft;
	}

	private Expr term() {

		Token highOperator = null;
		Expr exprRight = null;
		Expr exprLeft = signalFactor();

		while(lexer.token == Token.MULT || lexer.token == Token.DIV || lexer.token == Token.AND) {
			highOperator = lexer.token;
			next();
			exprRight = signalFactor();

			//Analise Semantica
			if(highOperator == Token.AND) {
				if (!checkRelExpr(exprLeft.getType(), exprRight.getType())) {
					error("Incompatible expressions type for relationship");
				}
			} else {
				if (!checkMathExpr(exprLeft.getType(), exprRight.getType())) {
					error("'Int' expression type expected to perform this operation");
				}
			}

			exprLeft = new CompositeExpr(exprLeft, highOperator, exprRight);
		}
		
		return exprLeft;
	}

	private Expr signalFactor() {

		Token signal = null;
		Expr exprRight = null;
		Expr expression = null;

		if(lexer.token == Token.PLUS || lexer.token == Token.MINUS) {
			signal = lexer.token;
			next();
			exprRight = factor();
			
			//Analise Semantica
			if (exprRight.getType() != Type.intType) {
				error("'Int' expression type expected to perform this operation");
			}

			expression = new CompositeExpr(exprRight, signal, exprRight);
		}
		expression = factor();
		return expression;
	}

	private Expr factor() {

		Expr expressao = null;
		String name1 = "";
		String name2 = "";
		Id id = null;
		Type tipo = null;
		Expr primexpr = null;
		Member membro1 = null;
		Member membro2 = null;
		ClassDec classe = null;
		MethodDec metodo = null;
		ExpressionList exprList = null;
		LiteralInt value = null;
		LiteralBoolean bool = null;
		LiteralString str = null;
		Variable variavel = null;

		if(lexer.token == Token.LITERALINT || lexer.token == Token.LITERALSTRING 
	    || lexer.token == Token.TRUE|| lexer.token == Token.FALSE) {
				
			if(lexer.token == Token.LITERALINT) {
				value = literalInt();
			} else if(lexer.token == Token.LITERALSTRING) {
				str = new LiteralString(lexer.getLiteralStringValue());
				
			} else {
				if(lexer.token == Token.TRUE) {
					bool = new LiteralBoolean(true);
				} else {
					bool = new LiteralBoolean(false);
				}
			}
			next();
			return new BasicValue(str, value, bool);
			
		} else if(lexer.token == Token.LEFTPAR) {
			
			next();
			expressao = expression();
			check(Token.RIGHTPAR, "')' expected");
			next();
			return new ExpressionFactor(expressao);

		} else if(lexer.token == Token.SELF || lexer.token == Token.SUPER) {

			primexpr = primaryExpr();
			return primexpr;

		} else if(lexer.token == Token.NOT) {
			next();
			return new NotFactor(factor());
			
		} else if(lexer.token == Token.ID) {

			name1 = lexer.getStringValue();
			checkKeyWords(name1);
			next();

			if(name1.equals("In")) {
				Expr readexpr = readExpr();
				return readexpr;
			}

			if(lexer.token == Token.DOT) {
				next();
				
				if(lexer.getStringValue().equals("new")) {
					next();

					//Caso a classe nao existir
					if(symbolTable.getInGlobal(name1) == null) {

						//Verifica se a classe e a classe atual
						if(currentClass.getCname().equals(name1)) {
							classe = currentClass;
							variavel = new Variable(classe.getCname(), classe);

						} else {
							error("Class '" + name1 + "' was not declared");
						}
						
					} else {
						classe = (ClassDec)symbolTable.getInGlobal(name1);
						variavel = new Variable(classe.getCname(), classe);
					}
					return new ObjectCreation(variavel);

				} else {
					
					if(lexer.token == Token.ID) {

						name2 = lexer.getStringValue();
						checkKeyWords(name2);
						next();

						//Analise Semantica (verifica se e um variavel local ou parametro)				
						if(symbolTable.getInLocalMethodVariablesClass(name1) == null) {
							error("Variable or Parameter '" + name1 + "' has not been declared in Method");
						
						} else if(symbolTable.getInLocalMethodVariablesClass(name1) != null){
							membro1 = variavel = (Variable)symbolTable.getInLocalMethodVariablesClass(name1);
							tipo = variavel.getType();
						
						} else if(symbolTable.getInLocalMethodFieldClass(name1) != null ){
							error("Not allowed to use object methods or fields without the use of 'self' qualifier, illegal access");
						}

						//Recupera a classe
						classe = (ClassDec)symbolTable.getInGlobal(tipo.getCname());

						//Recupera todos os metodos da propria class e dos parentes
						checkMethodsSuperClasses(classe);

						//Analise Semantica(verifica se existe na tabela de metodos da classe)
						if(symbolTable.getInLocalMethodParents(name2) == null){
							error("Method '" + name2 + "' was not found in the public interface of '" + classe.getCname()  +  "' or ts superclasses");
					
						//Analise Semantica (verifica se e um metodo de uma variavel de instancia)
						} else if(symbolTable.getInLocalMethodParents(name2) instanceof Variable) {
							error("Acess the attribute in class '" + name1 + "' is not allowed (private acess)");

						} else if(symbolTable.getInLocalMethodParents(name2) instanceof MethodDec) {

							membro2 = metodo = (MethodDec)symbolTable.getInLocalMethodParents(name2);

							//Verifica se o metodo e unario
							if(metodo.getNumParam() != 0) {
								error("Method '" + metodo.getName() + "' have a different description previously declared");				
							}
						}

						//Realiza a limpeza da tabela dos metodos da classe
						symbolTable.removeLocalIdentMethodParents();
						primexpr = new IdExpr(membro1, membro2, null);	
						
					} else if(lexer.token == Token.IDCOLON) {
						
						name2 = lexer.getStringValue();
						id = new Id(name2);
						next();

						//Analise Semantica (verifica se e um variavel local ou parametro)				
						if(symbolTable.getInLocalMethodVariablesClass(name1) == null) {
							error("Variable or Parameter '" + name1 + "' has not been declared in Method");
						
						} else if(symbolTable.getInLocalMethodVariablesClass(name1) != null){
							membro1 = variavel = (Variable)symbolTable.getInLocalMethodVariablesClass(name1);
							tipo = variavel.getType();
						
						} else if(symbolTable.getInLocalMethodFieldClass(name1) != null ){
							error("Not allowed to use object methods or fields without use 'self', illegal access");
						}

						//Recupera a lista de expressoes do metodo
						exprList = expressionList();

						//Recupera a classe
						classe = (ClassDec)symbolTable.getInGlobal(tipo.getCname());

						//Recupera todos os metodos da superclasse e da propria
						checkMethodsCurrentClass(classe);

						if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) == null){
							error("Method '" + id.getName() + "' was not found in the public interface of '" + classe.getCname() +  "' or ts superclasses");
						
						//Analise Semantica (verifica se e um metodo de uma variavel de instancia)
						} else if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof Variable) {
							error("identifier '" + id.getName() + "' is a variable, but a method is expected");
							
						//Analise Semantica (verifica se e um metodo de uma variavel de instancia)
						} else if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {

							membro2 = metodo = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());
							checkDescMethods(metodo, exprList);	
						}

						//Realiza a limpeza da tabela dos metodos da classe
						symbolTable.removeLocalIdentMethodCurrentClass();
						primexpr = new IdExpr(membro1, membro2, exprList);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
					return primexpr;
				}
			}

			//Analise Semantica (verifica se e um variavel local ou parametro)				
			if(symbolTable.getInLocalMethodVariablesClass(name1) == null) {
				error("Variable or Parameter '" + name1 + "' has not been declared in Method");
			
			} else if(symbolTable.getInLocalMethodVariablesClass(name1) != null){
				membro1 = variavel = (Variable)symbolTable.getInLocalMethodVariablesClass(name1);
			
			} else if(symbolTable.getInLocalMethodFieldClass(name1) != null ){
				error("Not allowed to use object methods or fields without the use of 'self' qualifier, illegal access");
			}

			primexpr = new IdExpr(membro1, null, null);
			return primexpr;

		} else if(lexer.token == Token.NIL) {
			next();
			return new NilExpr();
	
		} else {
			error("identifier, '(', super, In, '!', nil, string, int or boolean expected");
			return null;
		}
	}

	private Expr primaryExpr() {

		Id id = null;
		String name1 = "";
		String name2 = "";
		ExpressionList exprList = null;
		ClassDec classe = null;
		ClassDec superclass = null;
		Member membro1 = null;
		Member membro2 = null;
		MethodDec metodo = null;

		switch(lexer.token) {

			case SUPER:
				next();
				check(Token.DOT, "'.' expected after the 'super'");
				next();
				if(lexer.token != Token.ID && lexer.token != Token.IDCOLON) {
					error("An identifier or identifer: was expected after '.'");
			
				} else {
			
					if(lexer.token == Token.ID) {
			
						name1 = lexer.getStringValue();
						id = new Id(name1);
						next();
						checkKeyWords(id.getName());

						//Recupera a superclasse da clase atual
						superclass = currentClass.getSuperClass();

						//Analise Semantica (verificacao de existencia de uma superclasse na classe atual)
						if(superclass == null) {
							error("Class '" + currentClass.getCname() + "' does not inherit from a superclass");
						
						//Recupera todos os metodos da superclasse
						} else {

							checkMethodsSuperClasses(superclass);
							
							//Caso nao existir um metodo unario com esse nome
							if(symbolTable.getInLocalMethodParents(id.getName()) == null ) {
								error("There is no method '" + id.getName() + "' in the superclasse of class '" + currentClass.getCname() + "'");
						
							//Recupera o metodo correspondente
							} else {
								membro1 = metodo = (MethodDec)symbolTable.getInLocalMethodParents(id.getName());

								//Verifica se o metodo e unario
								if(metodo.getNumParam() != 0) {
									error("Method '" + metodo.getName() + "' have a different description previously declared");				
								}
							}
						}
						
						//Limpeza da tabela de simbolos da superclasse
						symbolTable.removeLocalIdentMethodParents();

						return new SuperExpr(membro1, null);
			
					} else if(lexer.token == Token.IDCOLON) {
						
						name1 = lexer.getStringValue();
						id = new Id(name1);
						next();
						checkKeyWords(id.getName());

						//Recupera a superclasse da clase atual
						superclass = currentClass.getSuperClass();

						//Analise Semantica (verificacao de existencia de uma superclasse na classe atual)
						if(symbolTable.getInGlobal(superclass.getCname()) == null) {
							error("Class '" + currentClass.getCname() + "' does not inherit from a superclass");
						
						//Recupera todos os metodos da superclasse
						} else {
							//Erro aqui
							checkMethodsSuperClasses(superclass);
						}

						exprList = expressionList();

						//Caso nao existir um metodo unario com esse nome
						if(symbolTable.getInLocalMethodParents(id.getName()) == null) {
							error("There is no method '" + id.getName() + "' in the superclasse of class '" + currentClass.getCname() + "'");
					
						//Recupera o metodo correspondente
						} else {
							membro1 = metodo = (MethodDec)symbolTable.getInLocalMethodParents(id.getName());
							checkDescMethods(metodo, exprList);
						}

						//Limpeza da tabela de simbolos da superclasse
						symbolTable.removeLocalIdentMethodParents();
						return new SuperExpr(membro1, exprList);
					}
				}
				break;
			
			case SELF:
				
				next();
				
				if(lexer.token == Token.DOT){

					next();
					
					if(lexer.token == Token.ID) {
						name1 = lexer.getStringValue();
						id = new Id(name1);
						checkKeyWords(id.getName());
						next();

						//Pega os metodos da class atual
						checkMethodsCurrentClass(currentClass);
						
						//Analise Semantica (verificacao de existencia da variavel na classe)
						if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) == null) {
							
							//Se herdar de alguma classe, verifica a superclasse
							if(currentClass.getSuperClass() != null) {
								
								checkMethodsSuperClasses(currentClass);

								if(symbolTable.getInLocalMethodParents(id.getName()) != null) {
									membro1 = metodo = (MethodDec)symbolTable.getInLocalMethodParents(id.getName());

								} else {
									error("Variable or method '" + id.getName() + "' has not being declared in class or method has not being declared in superclass");
								}
							}
							
						} else {
							
							//Caso for uma variavel
							if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof Variable) {
								membro1 = (Variable)symbolTable.getInLocalTableMethodCurrentClass(id.getName());

							//Caso for um metodo
							} else if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {
								membro1 = metodo = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());
							}  
						}

						if(lexer.token == Token.DOT) {
				
							next();
						
							if(lexer.token == Token.ID) {

								name2 = lexer.getStringValue();
								id = new Id(name2);
								checkKeyWords(id.getName());
								next();

								//Recupera a classe
								classe = (ClassDec)symbolTable.getInGlobal(name1);

								//Se a classe nao existe, entao e a classe atual
								if(classe == null) {
									classe = currentClass;
								}

								//Pega os metodos da class atual
								checkMethodsCurrentClass(classe);

								//Analise Semantica (verificacao de existecia do metodo unario)
								if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) == null) {

									//Se herdar de alguma classe, verifica a superclasse
									if(classe.getSuperClass() != null) {
										
										checkMethodsSuperClasses(classe);

										if(symbolTable.getInLocalMethodParents(id.getName()) != null) {
											membro1 = metodo = (MethodDec)symbolTable.getInLocalMethodParents(id.getName());

										} else {
											error("method '" + id.getName() + "' has not being declared in class " + name1 + " or method has not being declared in superclass");
										}
									}
								} else {
						
									if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof Variable) {
										error("Access the attribute in class '" + name1 + "' is not allowed (private access)");

									} else if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {
										membro2 = metodo = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());
										
										//Verifica se o metodo e unario
										if(metodo.getNumParam() != 0) {
											error("Method '" + metodo.getName() + "' have a different description previously declared");				
										}
									}
								}
								
								//Limpeza da tabela de simbolos da classe
								symbolTable.removeLocalIdentMethodCurrentClass();
								symbolTable.removeLocalIdentMethodParents();

								return new SelfExpr(membro1, membro2, null);
						
							} else if(lexer.token == Token.IDCOLON) {

								name2 = lexer.getStringValue();
								id = new Id(name2);
								checkKeyWords(id.getName());
								next();

								//Recupera a classe
								classe = (ClassDec)symbolTable.getInGlobal(name1);

								//Pega os metodos da class atual
								checkMethodsCurrentClass(classe);
								

								//Analise Semantica (verificacao de existecia do metodo unario)
								if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) == null) {
									error("Method '" + id.getName() + "' has not being declared in " + name1);
						
								} else {
						
									if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {
										membro2 = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());		

									} else {
										error("Identifier '" + id.getName() + "' has not a method");
									}
								}

								exprList = expressionList();
								metodo = (MethodDec)membro2;
								checkDescMethods(metodo, exprList);
								
								//Limpeza da tabela de simbolos da classe
								symbolTable.removeLocalIdentMethodCurrentClass();								
								symbolTable.removeLocalIdentMethodParents();
								
								return new SelfExpr(membro1, membro2, exprList);			
							
							} else {
								error("An identifier or identifer: was expected after '.'");		
							}
						}
						
						//Verifica se for um atributo, e um metodo unario
						if(metodo != null && metodo.getNumParam() != 0) {
							error("Current class method '" + metodo.getName() + "' has a different description previously declared");				
						}

						return new SelfExpr(membro1, null, null);

					} else if(lexer.token == Token.IDCOLON) {
						
						name1 = lexer.getStringValue();
						id = new Id(name1);
						checkKeyWords(id.getName());
						next();

						//Pega os metodos da class atual
						checkMethodsCurrentClass(currentClass);

						//Analise Semantica (verificacao de existecia do metodo unario)*
						if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) == null) {
							error("Method '" + id.getName() + "' has not being declared in class");
				
						} else {
				
							if(symbolTable.getInLocalTableMethodCurrentClass(id.getName()) instanceof MethodDec) {
								membro1 = (MethodDec)symbolTable.getInLocalTableMethodCurrentClass(id.getName());	
							} else {
								error("Identifier '" + id.getName() + "' is not a method");
							}
						}

						exprList = expressionList();
						metodo = (MethodDec)membro1;
						checkDescMethods(metodo, exprList);

						//Limpeza da tabela de simbolos da classe
						symbolTable.removeLocalIdentMethodCurrentClass();
						symbolTable.removeLocalIdentMethodParents();

						return new SelfExpr(membro1, null, exprList);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
				} else {
					return new SelfExpr(null, null, null);
				}
				break;
			default:
				break;
		}
		return null;
	}

	private Expr readExpr() {

		String name = "";
		check(Token.DOT, "'.' expected after the 'In'");
		next();
		name = lexer.getStringValue();
		checkKeyWords(name);
		next();

		if(!(name.equals("readInt")) && 
		   !(name.equals("readString"))) {
			error("Command 'In.' without arguments");
		} 

		return new ReadExpr(name);
	}

	private LocalDec localDec() {
		
		Expr expressao = null;
		next();
		Type tipo = type();
		next();
		IdList idlist = idList(tipo, true);

		if(lexer.token == Token.ASSIGN) {
			
			next();
			expressao = expression();
		
			if(expressao == null) {
				error("expression expected after the '='");
			}
		}

		/*//Analise Semantica
		if(tipo != expressao.getType()) {
			error("'" + expressao.getType() + "' type expected to assign to each method variable location");
		}*/

		return new LocalDec(tipo, idlist, expressao);
	}

	private RepeatStat repeatStat() {
		
		next();
		StatementList statList = statementList();

		if(lexer.token != Token.UNTIL) {
			next();
			error("missing keyword 'until'");	
		} else {
			next();
		}

		Expr expressao = expression();

		//Analise Semantica
		if(!(checkWhileExpr(expressao.getType()))) {
			error("'boolean' expression type expected to operation 'until'");
		}

		return new RepeatStat(statList, expressao);
	}

	private BreakStat breakStat() {
		
		next();
		return new BreakStat();
	}

	private ReturnStat returnStat() {

		next();
		Expr expressao = expression();
		return new ReturnStat(expressao);
	}

	private WhileStat whileStat() {

		next();
		Expr expressao = expression();

		//Analise Semantica
		if(!(checkWhileExpr(expressao.getType()))) {
			error("'boolean' expression type expected to operation 'while'");
		}

		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		next();
		StatementList statList = statementList();
		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");
		next();
		return new WhileStat(statList, expressao);
	}

	//Metodo para verificar a expressao no metodo WhileStat
	private boolean checkWhileExpr(Type exprType) {

		if (exprType == Type.booleanType) {
			return true;
		} else {
			return false;
		}
	}

	//Metodo para verificar se duas classes sao do mesmo tipo
	private boolean checkClasses(Type class1, Type class2) {	
		
		ClassDec classe = null;
		classe = (ClassDec)symbolTable.getInGlobal(class2.getCname());

		while((ClassDec)class1 != classe) {

			if(classe.getSuperClass() != null && classe.getSuperClass().getOpen()){
				classe = classe.getSuperClass();
			} else if(classe.getSuperClass() == null){
				break;
			}
		}

		if(classe != null && (ClassDec)class1 == classe){
			return true;
		}

		classe = (ClassDec)symbolTable.getInGlobal(class1.getCname());

		while((ClassDec)class2 != classe) {

			if(classe.getSuperClass() != null && classe.getSuperClass().getOpen()){
				classe = classe.getSuperClass();
			} else if(classe.getSuperClass() == null){
				break;
			}
		}

		if(classe != null && (ClassDec)class2 == classe){
			return true;
		}
		return false;
	}

	//Metodo para verificar a expressao e booleana
	private boolean checkRelExpr(Type left, Type right) {
		
		if(left instanceof ClassDec && right instanceof ClassDec) {
			return checkClasses(left, right);
		} else if (left == Type.undefinedType || right == Type.undefinedType) {
			return true;
		} else {
			return left == right;
		}
	}

	//Metodo para verificar a comparacao entre duas expressoes inteiras
	private boolean checkMathExpr(Type left, Type right) {

		boolean orLeft = left == Type.intType|| left == Type.undefinedType;
		boolean orRight = right == Type.intType || right == Type.undefinedType;
		return (orLeft && orRight);
	}

	//Metodo para verificar se dois metodos possuem a mesma descricao
	private boolean checkDescMethods(MethodDec metodo, ExpressionList exprList){
		
		//Verifica se o numero de argumentos da lista de expressao é igual do metodo
		if(metodo != null && (metodo.getNumParam() != exprList.getNumberExpr())) {
			error("The number of parameters of the method call is" + metodo.getNumParam() + ", while the number of expressions is " + exprList.getNumberExpr());
			return false;
		
		//Verifica se os tipos dos argumentos é igual da descricao do metodo 
		} else {

			ArrayList<ParamDec> parametros = metodo.getParamDec().getParamDec();
			ArrayList<Expr> expressoes = exprList.getArrayList();
			Iterator<ParamDec> param = parametros.iterator();
			Iterator<Expr> expr = expressoes.iterator();
			ParamDec param1 = null;
			Expr expr1 = null;
			Type tipo = null;
			ClassDec classe = null;
			int flag = 1;

			while(param.hasNext() && expr.hasNext()) {
				param1 = param.next();
				expr1 = expr.next();
				if(expr1.getType() != param1.getType()) {
					tipo = expr1.getType();

					if(symbolTable.getInGlobal(tipo.getCname()) != null) {
						classe = (ClassDec)symbolTable.getInGlobal(tipo.getCname());
						
						if(classe.getSuperClass() == null) {
							error("Expression type is a class and the same inherits no class with the same expression type");
							return false;
						}

						while(classe.getSuperClass() != null) {
							classe = classe.getSuperClass();

							if(classe == null) {
								error("Expression type is a class and the same inherits no class with the same expression type"); 
								return false;

							} else if(classe.getCname().equals(param1.getType().getCname())) {
								flag = 1;
								break;
							
							} else if(classe.getSuperClass() == null) {
								error("Expression type is a class and the same inherits no class with the same expression type");
								return false;
							} 
						}

						if(flag == 1) {
							return true;
						}
					
					} else {
						error("Expression and parameter have distinct types");
						return false;
					}
				}
			}

			return true;
		}
	}

	//Metodo para encontrar todos os metodos das superclasses da classe corrente
	private void checkMethodsSuperClasses(ClassDec superclass) {

		MemberList memberlist = null;
		int flag = 1;

		//Recupera todos os metodos das classes da hierarquia
		do{
			//Se a classe puder ser herdada
			if(superclass.getOpen() || flag == 1){
				
				flag = 0;
				memberlist = superclass.getMembros();
				ArrayList<Member> membrosSuperClass = memberlist.getArray();

				//Procura todos os metodos publicos da superclasse
				for(Member s: membrosSuperClass) {
					if(s instanceof MethodDec){

						if(((MethodDec)s).getQualifier().equals("public")){

							//Coloca o metodo na tabela de simbolos locais da classe em relacao a superclasse
							symbolTable.putInLocalMethodParents(((MethodDec)s).getName(), ((MethodDec)s));
						}
					}
				}
			}

			//Recupera a classe pai
			superclass = superclass.getSuperClass();

		} while(superclass != null);
	}

	//Metodo para recuperar todos os metodos de uma determinada classe
	private void checkMethodsCurrentClass(ClassDec classe) {

		MemberList membros = null;
		ArrayList<Variable> ids = null;
		membros = classe.getMembros();
		
		for(Member s: membros.getArray()) {
			if(s instanceof MethodDec) {
				symbolTable.putInLocalMethodCurrentClass(((MethodDec)s).getName(), (MethodDec)s);
			} else {

				//Inseri todas as variaveis na tabela de simbolos da classe
				ids = ((FieldDec)s).getIdList().getArray();
				for(Variable v: ids) {
					symbolTable.putInLocalMethodCurrentClass(((Variable)v).getName(), (Variable)v);
				}
			}
		}
	}

	//Metodo responsavel pela verificacao de ser uma palavra chave
	private void checkKeyWords(String name) {

		if(lexer.get_keywords(name) != null) {
			error("'" + name + "' is a keyword");
		}
	}

	private WriteStat writeStat() {
		next();
		check(Token.DOT, "'.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");
		String printName = lexer.getStringValue();
		Id id = new Id(printName);
		checkKeyWords(id.getName());
		next();
		ExpressionList exprList = expressionList();

		//Verifica se existe algum objeto nas expressoes
		for(Expr s: exprList.getArrayList()) {

			/*
			//Erro
			if(s.getType() instanceof ClassDec) {
				error("'Write' does not accept objects");
			}*/
		}
		return new WriteStat(exprList, printName);
	}

	private Type type() {

		Type tipo = null;
		ClassDec classe = null;
		Id id = null;
		String name = "";

		if(lexer.token == Token.INT || lexer.token == Token.BOOLEAN || lexer.token == Token.STRING) {
			
			if(lexer.token == Token.INT) {
				tipo = Type.intType;
			} else if(lexer.token == Token.STRING){
				tipo = Type.stringType;
			} else {
				tipo = Type.booleanType;
			}
			return tipo;
		
		} else if(lexer.token == Token.ID) {

			name = lexer.getStringValue();
			id = new Id(name);
			checkKeyWords(id.getName());

			if(currentClass.getName().equals(id.getName())){
				classe = currentClass;
				return classe;
			}

			classe = (ClassDec)symbolTable.getInGlobal(name);

			if(classe == null) {
				error("There is no class with name " + name);
			} 
			return classe;
			
		} else {
			error("A type was expected");
		}
		return null;
	}

	public Statement assertStat() {

		next();
		int lineNumber = lexer.getLineNumber();
		Expr expressao = expression();
		check(Token.COMMA, "',' expected after the expression of the 'assert' statement");
		next();
		check(Token.LITERALSTRING, "A literal string expected after the ',' of the 'assert' statement");
		String message = lexer.getLiteralStringValue();
		next();
		return new AssertStat(message, expressao);
	}

	//Metodo para recuperar um valor inteiro
	private LiteralInt literalInt() {

		LiteralInt e = null;
		int value = lexer.getNumberValue();
		return new LiteralInt(value);
	}

	
	private ClassDec		currentClass; //Atributo para a classe atual
	private SymbolTable		symbolTable; //Atributo para as tabelas de simbolos
	private Lexer			lexer; //Atributo para acessar o lexer
	private ErrorSignaller	signalError; //Atributo para insercao de erros

}