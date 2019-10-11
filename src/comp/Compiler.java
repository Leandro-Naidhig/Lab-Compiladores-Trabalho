/**
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
		this.signalError.showError(msg);
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

		check(Token.CLASS, "'Class' expected");
		next();
		
		if (lexer.token != Token.ID) {
			error("Identifier expected");
		} else {
			className = lexer.getStringValue();
		}

		//Analise Semanica (verificacao se e uma palavra chave)
		if(lexer.get_keywords(className) != null) {
			error(className + " is a keyword");
		}

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
				error("Superclass name " + superclassName + " is a keyword");
			}
			
			superclass = (ClassDec)symbolTable.getInGlobal(superclassName);
			
			//Analise Semanica (verificacao de existencia da superclasse)
			if(superclass == null) {
				error("Class '" + superclassName + "' has not been declared");
			
			} else {

				//Vamos recuperar todos os metodos das classes da hierarquia
				while(superclass.getSuperClass() != null) {

					//Recupera a classe pai
					superclass = superclass.getSuperClass();

					//Se a classe puder ser herdada
					if(superclass.getOpen()){
						
						memberlist = superclass.getMembros();
						ArrayList<Member> membrosSuperClass = memberlist.getArray();

						//Procura todos os metodos publicos da superclasse
						for(Member s: membrosSuperClass) {
							if(s instanceof MethodDec){
								if(((MethodDec)s).getQualifier().equals("public")){

									//Coloca o metodo na tabela de simbolos locais da classe em relacao a superclasse
									symbolTable.putInLocal(((MethodDec)s).getName(), ((MethodDec)s));
								}
							}
						}
					}
				}	
			}
		}

		memberlist = memberList();
		symbolTable.removeLocalIdent();
		check(Token.END, "'end' expected");
		next();
		classe = new ClassDec(className, superclass, memberlist, isopen);
		symbolTable.putInGlobal(className, classe);
		return classe;
	}

	private MemberList memberList() {

		ArrayList<Member> members = new ArrayList<>();
		Member membro = null;
		String first = "";
		String second = "";
		String third = "";
		String qual = "public"; //Por padrao de acordo com o cianeto

		while(true) {

			//Recupera o primeiro qualificador
			if(lexer.token == Token.PRIVATE || lexer.token == Token.PUBLIC || 
			   lexer.token == Token.OVERRIDE || lexer.token == Token.FINAL || 
			   lexer.token == Token.SHARED) {

				first = lexer.getStringValue();
				next();

				if( (first.equals("shared") && (!(lexer.token == Token.PRIVATE) || !(lexer.token == Token.PUBLIC))) 
				    ||  (first.equals("final") && (!(lexer.token == Token.PUBLIC) || !(lexer.token == Token.OVERRIDE)))
				    ||  (first.equals("override") && !(lexer.token != Token.PUBLIC)) ) {

					error("Qualifier illegal");
				
				//Recupera o segundo qualificador
				} else if(lexer.token != Token.VAR && lexer.token != Token.FUNC) { 

			   		second = lexer.getStringValue();
					next();

					if(first.equals("") && second.equals("") && !(lexer.token == Token.PUBLIC)) {
						error("Qualifier illegal");

					//Recupera o terceiro qualificador
					} else if(lexer.token == Token.PUBLIC) {
						third = lexer.getStringValue();
						next();
					}
				}

				//Juncao do qualificador final
				qual = first + second + third;	
			}
			
			if(lexer.token == Token.VAR) {
				next();
				membro = fieldDec(qual);

			} else if(lexer.token == Token.FUNC) {
				next();
				membro = methodDec(qual);
			} else {
				break;
			}

			members.add(membro);
		}
		return new MemberList(members);
	}

	private MethodDec methodDec(String qualificador) {
		
		Id id = null;
		String name = null;
		Type tipo = null;
		FormalParamDec formparaDec = null;
		MethodDec metodo = null;

		if(lexer.token == Token.ID) {
			name = lexer.getStringValue();

			//Analise Semanica (verificacao de existencia da funcao)
			if(symbolTable.getInLocal(name) != null) {
				error("Function '" + name + "' has already been declared");
			
			} else {
				id = new Id(name);
			}

			next();

		} else if(lexer.token == Token.IDCOLON) {
			name = lexer.getStringValue();
			next();
			formparaDec = formalParamDec();

			//Analise Semanica (verificacao de existencia da funcao)
			if(symbolTable.getInLocal(name) != null) {
				
				metodo = (MethodDec)symbolTable.getInLocal(name);
				
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
						error("Function '" + name + "' has already been declared with the same parameters");
					}
				}			
			
			}

		} else {
			error("An identifier or identifer: was expected after 'func'");
		}

		id = new Id(name);

		if(lexer.token == Token.MINUS_GT) {
			next();
			tipo = type();
			next();
		}

		check(Token.LEFTCURBRACKET, "'{' expected");
		next();
		StatementList statlist = statementList();
		check(Token.RIGHTCURBRACKET, "'}' expected");
		next();
		metodo = new MethodDec(id, formparaDec, tipo, statlist, qualificador);

		//Coloca o metodo na tabela de simbolos locais da classe
		symbolTable.putInLocal(id.getName(), metodo);
		
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
			idlist = idList(tipo);
		}

		if(lexer.token == Token.SEMICOLON) {
			isSemiColon = true;
			next();
		}

		return new FieldDec(tipo, idlist, isSemiColon, qualificador);
	}

	private IdList idList(Type tipo) {

		ArrayList<Variable> identifiers = new ArrayList<>();
			
		if(lexer.token != Token.ID) {
			error("Identifier expected");
		} else {

			//Analise Semantica (verificacao se e uma palavra chave)
			if(lexer.get_keywords(lexer.getStringValue()) != null) {
				error(lexer.getStringValue() + " is a keyword");
			
			//Analise Semantica (verificacao se ja existe o identificador)
			} else if (symbolTable.getInLocal(lexer.getStringValue()) != null) {
				error("Identifier '" + lexer.getStringValue() + "' has already been declared in class");
			
			//Analise Semantica (caso estiver livre)
			} else {
				Variable id = new Variable(lexer.getStringValue(), tipo); 
				identifiers.add(id);
				symbolTable.putInLocal(lexer.getStringValue(), id);
			}
		}

		next();

		while(lexer.token == Token.COMMA) {

			next();
			if(lexer.token != Token.ID) {
				error("Identifier expected");
			} else {

				//Analise Semantica (verificacao se e uma palavra chave)
				if(lexer.get_keywords(lexer.getStringValue()) != null) {
					error(lexer.getStringValue() + " is a keyword");
				
				//Analise Semantica (verificacao de declaracao de variavel)
				} else  if (symbolTable.getInLocal(lexer.getStringValue()) != null) {
					error("Identifier '" + lexer.getStringValue() + "' has already been declared in class");
				
				//Analise Semantica (caso estiver livre)
				} else {
					Variable id = new Variable(lexer.getStringValue(), tipo); 
					identifiers.add(id);
					symbolTable.putInLocal(lexer.getStringValue(), id);
				}
			}
			next();
		}
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

			//Analise Semantica (verificacao se e uma palavra chave)
			if(lexer.get_keywords(lexer.getStringValue()) != null) {
				error(lexer.getStringValue() + " is a keyword");
			
			} else {
				name = new Variable(lexer.getStringValue(), tipo);
				symbolTable.putInLocalMethod(lexer.getStringValue(), name);
			}

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
		   || lexer.token == Token.SELF || (lexer.token == Token.ID && lexer.getStringValue().equals("Out")) ) {
			statement(statements);
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
				next();
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

		/*
		if (expressao.getType() != Type.booleanType) {
			error("É esperada uma expressão do tipo 'Boolean'");
		}*/

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

		Expr exprLeft = expression();
		Expr exprRight = null;
		Token Op = null;

		if(lexer.token == Token.ASSIGN) {
			Op = lexer.token;
			next();
			exprRight = expression();

			/*
			if(Op == Symbol.READINT && ExpDir.getType() != Type.integerType) {
				error("Tipo da variável " + v.getName() + " incompátivel com 'readInt'. Deve ser do tipo 'Int'");
	
			} else if(Op == Symbol.READSTRING && ExpDir.getType() != Type.stringType) {
				error("Tipo da variável " + v.getName() + " incompátivel com 'readString'. Deve ser do tipo 'String'");
			}
	
			if(ExpEsq.getType() == Type.integerType && ExpDir.getType() != Type.integerType) {
				error("Função 'readInt' requer parâmetro do tipo Int");
			} else if(ExpEsq.getType() == Type.stringType && ExpDir.getType() != Type.stringType)   {
				error("Função 'readString' requer parâmetro do tipo String");
			}*/
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

			/*Analise Semantica*/
			/*if (!checkRelExpr(ExpEsq.getType(), ExpDir.getType())) {
				error("Tipo de expressões incompátiveis para relação");
			}*/
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

			/*Analise Semantica*/
			/*if (!checkRelExpr(ExpEsq.getType(), ExpDir.getType())) {
				error("Tipo de expressões incompátiveis para relação");
			}*/

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

			/*Analise Semantica*/
			/*if (!checkRelExpr(ExpEsq.getType(), ExpDir.getType())) {
				error("Tipo de expressões incompátiveis para relação");
			}*/
			
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

			/*Analise Semantica*/
			/*if (!checkMathExpr(ExpEsq.getType(), ExpDir.getType())) {
				error("Expression do tipo 'Int' esperada para realizar a operação");
			}*/
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
			
			/*Analise Semantica*/
			/*if (ExpDir.getType() != Type.integerType) {
				error("Expressão do tipo 'Int' é esperada para a operação");
			}*/

			expression = new CompositeExpr(exprRight, signal, exprRight);
		}
		expression = factor();
		return expression;
	}

	private Expr factor() {

		Expr expressao = null;
		String name = "";
		Id id = null;
		ArrayList<Id> ids = new ArrayList<>();
		Expr primexpr = null;
		ExpressionList exprList = null;
		LiteralInt value = null;
		LiteralBoolean bool = null;
		LiteralString str = null;

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

		} else if(lexer.token == Token.SELF || lexer.token == Token.SUPER 
		|| lexer.token == Token.IN) {
			primexpr = primaryExpr();
			return primexpr;

		} else if(lexer.token == Token.NOT) {
			next();
			return new NotFactor(factor());
			
		} else if(lexer.token == Token.ID) {
			name = lexer.getStringValue();
			id = new Id(name);
			ids.add(id);
			next();
			if(lexer.token == Token.DOT) {
				next();
				if(lexer.getStringValue().equals("new")) {
					next();
					ClassDec classe = (ClassDec)symbolTable.getInGlobal(name);
					if(classe == null) {
						error("Class " + name + " has not been declared");
					}
					return new ObjectCreation(classe);
				} else {
					if(lexer.token == Token.ID) {
						name = lexer.getStringValue();
						id = new Id(name);
						ids.add(id);
						next();
						primexpr = new PrimaryExpr(null, ids, null, null);	
						
					} else if(lexer.token == Token.IDCOLON) {
						name = lexer.getStringValue();
						id = new Id(name);
						next();
						exprList = expressionList();
						primexpr = new PrimaryExpr(null, ids, id, exprList);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
					return primexpr;
				}
			}
			primexpr = new PrimaryExpr(null, ids, null, null);
			return primexpr;

		} else if(lexer.token == Token.NIL) {
			next();
			return null;
	
		} else {
			error("identifier, '(', super, In, '!', nil, string, int or boolean expected");
			return null;
		}
	}

	private Expr primaryExpr() {

		String name1 = "";
		String name2 = "";
		ExpressionList exprList = null;
		Member membro1 = null;
		Member membro2 = null;

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
						next();
						return new SuperExpr(membro1, null);
			
					} else if(lexer.token == Token.IDCOLON) {
						name1 = lexer.getStringValue();
						next();
						exprList = expressionList();
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
						next();

						//Analise Semantica (verificacao de existencia da variavel na classe)
						if(symbolTable.getInLocal(name1) == null) {
							error("variable or Method '" + name1 + "' has not declared in class");
						} else {
							if(symbolTable.getInLocal(name1) instanceof Variable) {
								membro1 = (Variable)symbolTable.getInLocal(name1);
							} else if(symbolTable.getInLocal(name1) instanceof MethodDec) {
								membro1 = (MethodDec)symbolTable.getInLocal(name1);
							}  
						}

						if(lexer.token == Token.DOT) {
							next();
						
							if(lexer.token == Token.ID) {
								name2 = lexer.getStringValue();
								next();

								//Analise Semantica (verificacao de existecia do metodo unario)*
								if(symbolTable.getInLocal(name2) == null) {
									error("Method or Variable'" + name2 + "' has not declared in " + name1);
								} else {
						
									if(symbolTable.getInLocal(name2) instanceof Variable) {
										membro2 = (Variable)symbolTable.getInLocal(name2);
									} else if(symbolTable.getInLocal(name2) instanceof MethodDec) {
										membro2 = (MethodDec)symbolTable.getInLocal(name2);	
									} else {
										error("Identifier'" + name2 + "' has not a method, variable or instance");
									}
								}
								return new SelfExpr(membro1, membro2, null);
						
							} else if(lexer.token == Token.IDCOLON) {
								name2 = lexer.getStringValue();

								//Analise Semantica (verificacao de existecia do metodo unario)*
								if(symbolTable.getInLocal(name2) == null) {
									error("Method'" + name2 + "' has not declared in " + name1);
						
								} else {
						
									if(symbolTable.getInLocal(name2) instanceof MethodDec) {
										membro2 = (MethodDec)symbolTable.getInLocal(name2);	
									} else {
										error("Identifier'" + name2 + "' has not a method");
									}
								}

								next();
								exprList = expressionList();
								MethodDec metodo = (MethodDec)membro2;

								/*Verifica se o numero de argumentos é igual do metodo */
								if(metodo != null && (metodo.getNumParam() != exprList.getNumberExpr())) {
									error("The number of parameters of the method call is" + metodo.getNumParam() + ", while the number of expressions is " + exprList.getNumberExpr());
								
								/*Verifica se os tipos dos argumentos é igual da descricao do metodo */
								} else {
									ArrayList<ParamDec> parametros = metodo.getParamDec().getParamDec();
									ArrayList<Expr> expressoes = exprList.getArrayList();
									Iterator<ParamDec> param = parametros.iterator();
									Iterator<Expr> expr = expressoes.iterator();
									ParamDec param1 = null;
									Expr expr1 = null;

									while(param.hasNext() && expr.hasNext()) {
										param1 = param.next();
										expr1 = expr.next();
										if(expr1.getType() != param1.getType()) {
											error("Expression and parameter have distinct types");
											break;
										}
									}
								}
								return new SelfExpr(membro1, membro2, exprList);			
							
							} else {
								error("An identifier or identifer: was expected after '.'");		
							}
						}
						return new SelfExpr(membro1, null, null);

					} else if(lexer.token == Token.IDCOLON) {
						name = lexer.getStringValue();
						next();
						exprList = expressionList();
						return new PrimaryExpr("self", null, id, exprList);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
				} else {
					return new SelfExpr(null, null, null);
				}
				break;
			case IN:
				next();
				Expr readexpr = readExpr();
				return readexpr;
			default:
				break;
		}
		return null;
	}

	private Expr readExpr() {

		String name = "";
		check(Token.DOT, "'.' expected after the 'In'");
		next();

		if(!(lexer.getStringValue().equals("readInt")) && 
		   !(lexer.getStringValue().equals("readString"))) {
			error("'readInt' or 'readString' expected after the '.'");
		
		} else {
			name = lexer.getStringValue();
		}

		next();
		return new ReadExpr(name);
	}

	private LocalDec localDec() {
		
		Expr expressao = null;
		next();
		Type tipo = type();
		next();
		IdList idlist = idList();

		if(lexer.token == Token.ASSIGN) {
			next();
			expressao = expression();
			if(expressao == null) {
				error("expression expected after the '='");
			}
		}
		return new LocalDec(tipo, idlist, expressao);
	}

	private RepeatStat repeatStat() {
		next();
		StatementList statList = statementList();
		check(Token.UNTIL, "missing keyword 'until'");
		next();
		Expr expressao = expression();
		return new RepeatStat(statList, expressao);
	}

	private BreakStat breakStat() {
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
		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		next();
		StatementList statList = statementList();
		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");
		next();
		return new WhileStat(statList, expressao);
	}

	//Metodo para verificar a expressao no metodo WhileStat
	private boolean checkWhileExpr(Type exprType) {
		if (exprType == Type.undefinedType || exprType == Type.booleanType) {
			return true;
		} else {
			return false;
		}
	}

	//Metodo para verificar a expressao e booleana
	private boolean checkRelExpr(Type left, Type right) {
		if (left == Type.undefinedType || right == Type.undefinedType) {
			return true;
		} else {
			return left == right;
		}
	}

	private WriteStat writeStat() {
		next();
		check(Token.DOT, "'.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");
		String printName = lexer.getStringValue();
		next();
		ExpressionList exprList = expressionList();
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

	private LiteralInt literalInt() {

		LiteralInt e = null;

		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Token token) {

		return token == Token.FALSE || token == Token.TRUE
				|| token == Token.NOT || token == Token.SELF
				|| token == Token.LITERALINT || token == Token.SUPER
				|| token == Token.LEFTPAR || token == Token.NIL
				|| token == Token.ID || token == Token.LITERALSTRING;

	}

	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private ErrorSignaller	signalError;

}