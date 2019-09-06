package comp;

import java.io.PrintWriter;
import java.util.ArrayList;
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

	private Program program(ArrayList<CompilationError> compilationErrorList) {
		
		ArrayList<MetaobjectAnnotation> metaobjectCallList = new ArrayList<>();
		ArrayList<ClassDec> CianetoClassList = new ArrayList<>();

		Program program = new Program(CianetoClassList, metaobjectCallList, compilationErrorList);
		
		//Variavel respons�vel por verificar se existe algum erro na gramatica
		//Por padrao e inicializada com false
		boolean thereWasAnError = false;

		//Se for uma classe 
		while(lexer.token == Token.CLASS ||
			 (lexer.token == Token.ID && lexer.getStringValue().equals("open") ) ||
			  lexer.token == Token.ANNOT) {
			
			try {
				while (lexer.token == Token.ANNOT) {
					metaobjectAnnotation(metaobjectCallList);
				}

				CianetoClassList.add(classDec());

				while (lexer.token == Token.ANNOT || lexer.token == Token.CLASS) {				
					if(lexer.token == Token.ANNOT) {
						metaobjectAnnotation(metaobjectCallList);
					} else {
						CianetoClassList.add(classDec());
					}
				}
			}

			catch(CompilerError e) {
				// if there was an exception, there is a compilation error
				thereWasAnError = true;
				while(lexer.token != Token.CLASS && lexer.token != Token.EOF) {
					try {
						next();
					}
					catch (RuntimeException ee) {
						e.printStackTrace();
						return program;
					}
				}
			}
			catch(RuntimeException e) {
				e.printStackTrace();
				thereWasAnError = true;
			}

		}
		if (!thereWasAnError && lexer.token != Token.EOF) {
			
			try {
				error("End of file expected");
			}
			catch(CompilerError e) {
			}
		}
		return program;
	}

	@SuppressWarnings("incomplete-switch")
	private void metaobjectAnnotation(ArrayList<MetaobjectAnnotation> metaobjectAnnotationList) {

		String name = lexer.getMetaobjectName();
		int lineNumber = lexer.getLineNumber();

		//Verifica se o ID (name) � uma palavra chave
		if(lexer.get_keywords(name) != null) {
			error(name + " is a keyword");
		}

		next();

		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		boolean getNextToken = false;

		if(lexer.token != Token.LEFTPAR) {
			error("'(' expected before ID");

		} else {
		
			next();

			//Verifica se trata de alguma senten�a pertencente ao AnnotParam
			while(lexer.token == Token.LITERALINT || lexer.token == Token.LITERALSTRING ||
				  lexer.token == Token.ID) {
				switch(lexer.token) {
				
				//Caso o token for um numero
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				//Caso o token for uma string
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				//Caso o token for algum ID
				case ID:
					metaobjectParamList.add(lexer.getStringValue());
				}

				next();

				//Caso ainda tiver mais parametros de Int, String ou ID, continua o loop
				if(lexer.token == Token.COMMA) {
					next();
				
				//Senao, para o loop
				} else {
					break;
				}
			}

			//Caso o token n�o for um fecha parenteses
			if(lexer.token != Token.RIGHTPAR) {
				error("')' expected after annotation with parameters");
			
			} else { /*Caso o token for um fecha parenteses*/
				getNextToken = true;
			}
		}

		//Tratamento de erros para metaobjetos: NCE e CEP
		switch(name) {

		//Esse metaobjeto e responsavel por informar para o compilador que nao deve haver erros
		//de compilacao no codigo-fonte, se houver, o cianeto apontara que tem uma falha
		//Obs: Nao pode haver parametros
		case "nce":
			if(metaobjectParamList.size() != 0) {
				error("Annotation 'nce' does not take parameters");
			}
			break;
		
		//Esse metaobjeto e responsavel por informar para o compilador que existe um erro do
		//compilador no codigo-fonte
		//Obs: Tem que haver no minimo 3 e no maximo 4 parametros, entre outros erros
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
		
		//Verificar sua responsabilidade
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
		
		//Caso n�o for nenhuma das denota��es
		default:
			error("Annotation '" + name + "' is illegal");
		}

		//Contem uma lista de metaobjetos
		metaobjectAnnotationList.add(new MetaobjectAnnotation(name, metaobjectParamList));
		
		if(getNextToken){
			next();
		}
	}

	//Ok
	private ClassDec classDec() {
		
		String className = "";
		String superclassName = "";
		MemberList memberlist = null;

		if(lexer.token == Token.ID && lexer.getStringValue().equals("open")) {
			
		}

		if ( lexer.token != Token.CLASS ) {
			error("'class' expected");
		}

		next();
		
		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		} else {
			className = lexer.getStringValue();
		}

		//Verifica se o nome da classe n�o � um keyword
		if(lexer.get_keywords(className) != null) {
			error(className + " is a keyword");
		}

		//Verifica se a classe j� foi declarada no c�digo
		if (symbolTable.getInGlobal(className) != null) {
			error("Class '" + className + "' has already been declared");
		}
		
		next();

		if (lexer.token == Token.EXTENDS) {
			
			next();
			
			if (lexer.token != Token.ID) {
				error("Identifier expected");
			}
			
			superclassName = lexer.getStringValue();

			//Verifica se o ID (superclassName) � uma palavra chave
			if(lexer.get_keywords(superclassName) != null) {
				error(superclassName + " is a keyword");
			}

			next();
		}

		memberlist = memberList();
		
		if(lexer.token != Token.END) {
			error("'end' expected");
		}

		next();

		return new ClassDec(className, superclassName, memberlist);
		
	}

	private MemberList memberList() {
		
		int pos = 0;
		String first = "";
		String second = "";
		String third = "";
		String qual = "";
		ArrayList<Integer> qualifierspos = new ArrayList<>();
		ArrayList<String> qualifiers = new ArrayList<>(); 
		ArrayList<Member> members = new ArrayList<>();

		while(true) {

			FieldDec field = null;
			MethodDec method = null;
			
			//Verifica se o qualificador est� correto de acordo com a gramatica do cianeto
			if(lexer.token == Token.PRIVATE || lexer.token == Token.PUBLIC || 
			   lexer.token == Token.OVERRIDE || lexer.token == Token.FINAL || 
			   lexer.token == Token.SHARED) {

				next();

				//Pega o primeiro nome do qualifier
				first = lexer.getStringValue();

				//Caso houver um segundo nome para o qualifier
				if( (first == "shared" && (!(lexer.token == Token.PRIVATE) || !(lexer.token == Token.PUBLIC))) 
				    ||  (first == "final" && (!(lexer.token == Token.PUBLIC) || !(lexer.token == Token.OVERRIDE)))
				    ||  (first == "Override" && !(lexer.token != Token.PUBLIC)) ) {

					error("Qualifier illegal");
			   	}

			   	next();

			   	//Pega o segundo nome do qualifier
			   	second = lexer.getStringValue();

			   	if(first == "" && second == "" && !(lexer.token == Token.PUBLIC)) {
					error("Qualifier illegal");
			   
			   	} else if(lexer.token == Token.PUBLIC) {

					third = lexer.getStringValue();
			   	}
				
				qual = first + second + third;
				qualifiers.add(qual);
				qualifierspos.add(pos);
			   	pos++;
			}
			
			if(lexer.token == Token.VAR) {
				next();
				field = fieldDec();

			} else if(lexer.token == Token.FUNC) {
				next();
				method = methodDec();

			} else {
				break;
			}

			Member membro = new Member(field, method);
			members.add(membro);
		}

		return new MemberList(qualifierspos, qualifiers, members);
	}

	//Ok
	private void error(String msg) {
		this.signalError.showError(msg);
	}

	//Ok
	private void next() {
		lexer.nextToken();
	}

	//Ok
	private void check(Token shouldBe, String msg) {
		if(lexer.token != shouldBe) {
			error(msg);
		}
	}

	private MethodDec methodDec() {
		
		Type tipo = null;

		if(lexer.token == Token.ID) {
			// unary method
			next();

		} else if(lexer.token == Token.IDCOLON) {
			// keyword method. It has parameters

		} else {
			error("An identifier or identifer: was expected after 'func'");
		}

		FormalParamDec formparaDec = formalParamDec();

		if(lexer.token == Token.MINUS_GT) {
			next();
			tipo = type();
		}

		if(lexer.token != Token.LEFTCURBRACKET) {
			error("'{' expected");
		}

		next();

		StatementList statlist = statementList();

		if ( lexer.token != Token.RIGHTCURBRACKET ) {
			error("'{' expected");
		}

		next();
		return new MethodDec(formparaDec, tipo, statlist);
	}

	//Ok
	private FieldDec fieldDec() {
	
		Type tipo = type();
		IdList idlist = null;

		if(lexer.token != Token.ID) {
			this.error("A field name was expected");
		
		} else {
			idlist = idList();
		}

		return new FieldDec(tipo, idlist);
	}

	//Ok
	private IdList idList() {

		ArrayList<Id> ids = new ArrayList<>();
		String name = "";

		while(lexer.token == Token.ID ) {

			//Verifica se o ID (name) � uma palavra chave
			if(lexer.get_keywords(name) != null) {
				error(name + " is a keyword");
			
			//Variavel ja foi declarada na classe
			} else  if (symbolTable.getInLocal(name) != null) {
				error("Variable '" + name + "' has already been declared in class");
			
			} else {
				name = lexer.getStringValue();
			}

			ids.add(new Id(name));
			next();

			if(lexer.token == Token.COMMA) {
				next();
			} else {
				break;
			}
		}

		return new IdList(ids);
	}

	//OK
	private FormalParamDec formalParamDec() {

		ArrayList<ParamDec> params = new ArrayList<>();
		params.add(paramDec());
		next();

		while(lexer.token != Token.COMMA) {
			params.add(paramDec());
			next();
		}

		return new FormalParamDec(params);
	}

	//OK
	private ParamDec paramDec() {

		Id id = null;
		Type tipo = type();
		next();

		if(lexer.token == Token.ID) {
			id = new Id(lexer.getStringValue());			
		} else {
			error("An identifier was expected after type");
		}

		return new ParamDec(tipo, id);
	}

	//Ok
	private StatementList statementList() {
		
		ArrayList<Statement> statements = new ArrayList<>();
		
		while(lexer.token == Token.IF || lexer.token == Token.WHILE || lexer.token == Token.RETURN 
		   || lexer.token == Token.BREAK || lexer.token == Token.SEMICOLON || lexer.token == Token.REPEAT
		   || lexer.token == Token.VAR || lexer.token == Token.ASSERT || (
		   lexer.token == Token.ID && lexer.getStringValue().equals("Out"))) {
			statement(statements);
		}

		return new StatementList(statements);
	}

	//Ok
	private Statement statement(ArrayList<Statement> statements) {

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
		}
	}

	//Ok
	private IfStat ifStat() {
		
		ArrayList<Statement> statIf = new ArrayList<>();
		ArrayList<Statement> statElse = new ArrayList<>();

		next();
		Expression expressao = expression();
		check(Token.LEFTCURBRACKET, "'{' expected after the 'if' expression");
		next();
		
		while(lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END && lexer.token != Token.ELSE) {
			statIf.add(statement(statIf));
		}

		check(Token.RIGHTCURBRACKET, "'}' was expected");
		
		if (lexer.token == Token.ELSE) {
			next();
			check(Token.LEFTCURBRACKET, "'{' expected after 'else'");
			next();
			while(lexer.token != Token.RIGHTCURBRACKET) {
				statement(statElse);
			}
			check(Token.RIGHTCURBRACKET, "'}' was expected");
		}

		return new IfStat(expressao, statIf, statElse);
	}

	//Ok
	private AssignExpr assignExpr() {

		Expression exprLeft = expression();
		Expression exprRight = null;

		if(lexer.token == Token.ASSIGN) {
			exprRight = expression();
		}

		return new AssignExpr(exprLeft, exprRight); 
	}

	//Ok
	private Expression expression() {

		String relation = "";
		SimpleExpression exprLeft = simpleExpression();
		SimpleExpression exprRight = null;

		if(lexer.token == Token.EQ || lexer.token == Token.GT || lexer.token == Token.LT || 
		lexer.token == Token.GE || lexer.token == Token.LE || lexer.token == Token.NEQ) {
			relation = lexer.getStringValue();
			next();
			exprRight = simpleExpression();
		}

		return new Expression(exprLeft, relation, exprRight); 
	}

	//Ok
	private ExpressionList expressionList() {

		ArrayList<Expression> exprList = new ArrayList<>();

		Expression expr = expression();
		exprList.add(expr);
		next();

		while(lexer.token == Token.COMMA) {
			next();
			exprList.add(expr);
		}

		return new ExpressionList(exprList);
	}

	//Ok
	private SimpleExpression simpleExpression() {

		ArrayList<SumSubExpression> arraySumSub = new ArrayList<>();

		arraySumSub.add(sumSubExpression());
		next();

		while(lexer.token == Token.PLUSPLUS) {
			next();
			arraySumSub.add(sumSubExpression());
		}

		return new SimpleExpression(arraySumSub);
	}

	//Ok
	private SumSubExpression sumSubExpression() {

		ArrayList<Term> terms = new ArrayList<>();
		ArrayList<String> operators = new ArrayList<>();

		terms.add(term());
		next();

		while(lexer.token == Token.PLUS || lexer.token == Token.MINUS || lexer.token == Token.OR) {
			operators.add(lexer.getStringValue());
			next();
			terms.add(term());
		}

		return new SumSubExpression(terms, operators);
	}

	//Ok
	private Term term() {

		ArrayList<SignalFactor> signalfactor = new ArrayList<>();
		ArrayList<String> highOperator = new ArrayList<>();

		signalfactor.add(signalFactor());

		while(lexer.token == Token.MULT || lexer.token == Token.DIV || lexer.token == Token.AND) {
			highOperator.add(lexer.getStringValue());
			next();
			signalfactor.add(signalFactor());
		}

		return new Term(signalfactor, highOperator);
	}

	//Ok
	private SignalFactor signalFactor() {

		String signal = "";

		if(lexer.token == Token.PLUS || lexer.token == Token.MINUS) {
			signal = lexer.getStringValue();
		}

		next();
		Factor fac = factor();

		return new SignalFactor(signal, fac);
	}

	private Factor factor() {

		Expression expressao = null;

		if(lexer.token == Token.LEFTPAR) {

			expressao = expression();
			if(lexer.token != Token.RIGHTPAR) {
				error("')' expected");
			}			

		} else if(lexer.token == Token.SELF || lexer.token == Token.SUPER) {
			primaryExpr();

		} else if(lexer.token == Token.NOT) {
			Factor fac = factor();

		} else if(lexer.token == Token.ID) {
			

		}
		return new Factor();
	}

	//Ok
	private Factor primaryExpr() {

		ArrayList<Id> ids = new ArrayList<>();
		String name = "";
		Id id = null;
		ExpressionList exprList = null;

		switch(lexer.token) {
			case SUPER:
				check(Token.DOT, "'.' expected after the 'super'");
				next();
				if(lexer.token != Token.ID && lexer.token != Token.IDCOLON) {
					error("An identifier or identifer: was expected after '.'");
				} else {
					if(lexer.token == Token.ID) {
						name = lexer.getStringValue();
						id = new Id(name);
						ids.add(id);
						return new PrimaryExpr("super", ids, null, null, null);
					} else if(lexer.token == Token.IDCOLON) {
						name = lexer.getStringValue();
						id = new Id(name);
						exprList = expressionList();
						return new PrimaryExpr("super", null, id, exprList, null);
					}
				}
				break;
			case ID:
				name = lexer.getStringValue();
				id = new Id(name);
				ids.add(id);
				next();
				if(lexer.token == Token.DOT) {
					next();
					if(lexer.token == Token.ID) {
						name = lexer.getStringValue();
						id = new Id(name);
						ids.add(id);
						return new PrimaryExpr(null, ids, null, null, null);	
					
					} else if(lexer.token == Token.IDCOLON) {
						name = lexer.getStringValue();
						id = new Id(name);
						exprList = expressionList();
						return new PrimaryExpr(null, ids, id, exprList, null);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
					next();
				}
				break;
			case SELF:
				next();
				if(lexer.token == Token.DOT){
					next();
					if(lexer.token == Token.ID) {
						name = lexer.getStringValue();
						id = new Id(name);
						ids.add(id);
						next();
						if(lexer.token == Token.DOT) {
							next();
							if(lexer.token == Token.ID) {
								name = lexer.getStringValue();
								id = new Id(name);
								ids.add(id);
								return new PrimaryExpr("self", ids, null, null, null);
							} else if(lexer.token == Token.IDCOLON) {
								name = lexer.getStringValue();
								id = new Id(name);
								exprList = expressionList();
								return new PrimaryExpr("self", ids, id, exprList, null);			
							} else {
								error("An identifier or identifer: was expected after '.'");		
							}
						}

					} else if(lexer.token == Token.IDCOLON) {
						name = lexer.getStringValue();
						id = new Id(name);
						next();
						exprList = expressionList();
						return new PrimaryExpr("self", null, id, exprList, null);

					} else {
						error("An identifier or identifer: was expected after '.'");
					}
				} else {
					return new PrimaryExpr("self", null, null, null, null);
				}
				break;
			case IN:
				next();
				ReadExpr readexpr = readExpr();
				return new PrimaryExpr(null, null, null, null, readexpr);
				break;
		}
	}

	//Ok
	private Factor readExpr() {

		String name = "";

		if(lexer.token != Token.DOT) {
			error("'.' expected after the 'In'");
		}

		next();

		if(!(lexer.getStringValue().equals("readInt")) && 
		   !(lexer.getStringValue().equals("readString"))) {
			error("'readInt' or 'readString' expected after the '.'");
		
		} else {
			name = lexer.getStringValue();
		}

		return new ReadExpr(name);
	}

	//Ok
	private LocalDec localDec() {
		
		Expression expressao = null;
		next();
		Type tipo = type();
		check(Token.ID, "A variable name was expected");
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

	//Ok
	private RepeatStat repeatStat() {
		next();
		StatementList statList = statementList();
		check(Token.UNTIL, "missing keyword 'until'");
		Expression expressao = expression();
		return new RepeatStat(statList, expressao);
	}

	//Ainda nao sei o que fazer aqui
	private void breakStat() {
		next();
	}

	//Ok
	private ReturnStat returnStat() {
		next();
		Expression expressao = expression();
		return new ReturnStat(expressao);
	}

	//Ok
	private WhileStat whileStat() {

		next();
		Expression expressao = expression();
		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		next();
		StatementList statList = statementList();
		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");
		return new WhileStat(statList, expressao);
	}

	//Ok
	private WriteStat writeStat() {
		
		next();
		check(Token.DOT, "a '.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");
		String printName = lexer.getStringValue();
		Expression expressao = expression();

		return new WriteStat(expressao);
	}

	private Type type() {

		Type tipo = null;
		ClassDec class = null;

		if(lexer.token == Token.INT || lexer.token == Token.BOOLEAN || lexer.token == Token.STRING) {
			if(lexer.token == Token.INT) {
				tipo = Type.intType;

			} else if(lexer.token == Token.STRING){
				tipo = Type.stringType;

			} else {
				tipo = Type.booleanType;
			}
			next();
		
		} else if(lexer.token == Token.ID) {
			
			next();
		
		} else {
			this.error("A type was expected");
		}

	}

	//Ok
	public Statement assertStat() {

		next();
		int lineNumber = lexer.getLineNumber();
		Expression expressao = expression();
		
		if(lexer.token != Token.COMMA) {
			this.error("',' expected after the expression of the 'assert' statement");
		}

		next();
		
		if(lexer.token != Token.LITERALSTRING) {
			this.error("A literal string expected after the ',' of the 'assert' statement");
		}

		String message = lexer.getLiteralStringValue();
		return new AssertStat(message, expressao);
	}

	private LiteralInt literalInt() {

		LiteralInt e = null;

		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		next();
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
