package comp;

import java.io.PrintWriter;
import java.util.ArrayList;
import ast.LiteralInt;
import ast.MetaobjectAnnotation;
import ast.Program;
import ast.Statement;
import ast.TypeCianetoClass;
import lexer.Lexer;
import lexer.Token;

public class Compiler {

	public Compiler() { }

	// compile must receive an input with an character less than
	// p_input.lenght
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
		ArrayList<TypeCianetoClass> CianetoClassList = new ArrayList<>();
		Program program = new Program(CianetoClassList, metaobjectCallList, compilationErrorList);
		boolean thereWasAnError = false;
		while(lexer.token == Token.CLASS ||
				(lexer.token == Token.ID && lexer.getStringValue().equals("open") ) ||
				lexer.token == Token.ANNOT) {
			
			try {
				while (lexer.token == Token.ANNOT) {

					/*Chamada de funcao*/
					metaobjectAnnotation(metaobjectCallList);
				}

				/*Chamada de fun��o*/
				classDec();
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

	/**  parses a metaobject annotation as <code>{@literal @}cep(...)</code> in <br>
     * <code>
     * {@literal @}cep(5, "'class' expected") <br>
     * class Program <br>
     *     func run { } <br>
     * end <br>
     * </code>
     *

	 */
	@SuppressWarnings("incomplete-switch")
	private void metaobjectAnnotation(ArrayList<MetaobjectAnnotation> metaobjectAnnotationList) {

		//Acho que falta verificar se � realmente um ID e n�o alguma palavra chave da gramatica
		//N�o sei se foi verificado se contem arroba no come�o
		String name = lexer.getMetaobjectName();
		int lineNumber = lexer.getLineNumber();
		next();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		boolean getNextToken = false;

		/*Caso o token for um abre paranteses*/
		if(lexer.token == Token.LEFTPAR) {
			// metaobject call with parameters
			next();

			/*Verifica se trata de alguma senten�a pertencente ao AnnotParam*/
			while(lexer.token == Token.LITERALINT || lexer.token == Token.LITERALSTRING ||
					lexer.token == Token.ID) {
				switch(lexer.token) {
				
				/*Caso o token for um numero*/
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				/*Caso o token for uma string*/
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				/*Caso o token for algum ID*/
				case ID:
					metaobjectParamList.add(lexer.getStringValue());
				}

				next();

				/*Caso ainda tiver mais parametros de Int, String ou ID, continua o loop*/
				if(lexer.token == Token.COMMA) {
					next();
				
				} else {
					break;
				}
			}

			/*Caso o token n�o for um fecha parenteses*/
			if(lexer.token != Token.RIGHTPAR) {
				error("')' expected after annotation with parameters");
			
			} else { /*Caso o token for um fecha parenteses*/
				getNextToken = true;
			}
		}

		//????? Tenho que verificar melhor esses tratamentos de erros
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

	
	private void classDec() {
		
		if(lexer.token == Token.ID && lexer.getStringValue().equals("open")) {
			//class open
		}

		/*Verifica se o token � a palavra class*/
		if ( lexer.token != Token.CLASS ) {
			error("'class' expected");
		}

		next();
		
		/*Verifica se o token � um ID */
		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		}

		/*Recupera o nome da classe */
		String className = lexer.getStringValue();
		next();

		/*Verifica se o token � Extends */
		if (lexer.token == Token.EXTENDS) {
			next();
			
			/*Verifica se o token � um ID */
			if (lexer.token != Token.ID) {
				error("Identifier expected");
			}
			String superclassName = lexer.getStringValue();
			next();
		}

		/*Chamada de fun��o*/
		memberList();
		
		/*Verifica se o token � um END*/
		if(lexer.token != Token.END) {
			error("'end' expected");
		}

		next();
	}

	private void memberList() {
		while(true) {
			
			/*Chamada de funcao*/
			qualifier();
			
			if(lexer.token == Token.VAR) {
				
				/*Chamada de funcao*/
				fieldDec();
			
			} else if(lexer.token == Token.FUNC) {

				/*Chamada de funcao*/
				methodDec();
			
			} else {
				break;
			}
		}
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

	private void methodDec() {
		next();
		if(lexer.token == Token.ID) {
			// unary method
			next();

		} else if(lexer.token == Token.IDCOLON) {
			// keyword method. It has parameters

		} else {
			error("An identifier or identifer: was expected after 'func'");
		}

		if(lexer.token == Token.MINUS_GT) {
			// method declared a return type
			next();
			type();
		}
		if(lexer.token != Token.LEFTCURBRACKET) {
			error("'{' expected");
		}
		next();

		/*Chamada de funcao*/
		statementList();

		if ( lexer.token != Token.RIGHTCURBRACKET ) {
			error("'{' expected");
		}
		next();

	}

	private void statementList() {
		while(lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END) {
			statement();
		}
	}

	private void statement() {
		boolean checkSemiColon = true;
		switch(lexer.token) {
		case IF:
			/*Chamada de fun��o*/
			ifStat();
			checkSemiColon = false;
			break;
		case WHILE:
			/*Chamada de fun��o*/
			whileStat();
			checkSemiColon = false;
			break;
		case RETURN:
			/*Chamada de fun��o*/
			returnStat();
			break;
		case BREAK:
			/*Chamada de fun��o*/
			breakStat();
			break;
		case SEMICOLON:
			/*Chamada de fun��o*/
			next();
			break;
		case REPEAT:
			/*Chamada de fun��o*/
			repeatStat();
			break;
		case VAR:
			/*Chamada de fun��o*/
			localDec();
			break;
		case ASSERT:
			/*Chamada de fun��o*/
			assertStat();
			break;
		default:
			if(lexer.token == Token.ID && lexer.getStringValue().equals("Out")) {
				
				/*Chamada de fun��o*/
				writeStat();
			
			} else {

				/*Chamada de fun��o*/
				expr();
			}

		}

		if(checkSemiColon) {
			check(Token.SEMICOLON, "';' expected");
		}
	}

	private void localDec() {
		next();
		type();

		check(Token.ID, "A variable name was expected");
		
		while(lexer.token == Token.ID) {
			next();
			if(lexer.token == Token.COMMA) {
				next();
			}
			else {
				break;
			}
		}

		if(lexer.token == Token.ASSIGN) {
			next();
			// check if there is just one variable
			expr();
		}

	}

	private void repeatStat() {
		next();
		while(lexer.token != Token.UNTIL && lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END) {
			statement();
		}

		check(Token.UNTIL, "missing keyword 'until'");
	}

	private void breakStat() {
		next();
	}

	private void returnStat() {
		next();
		expr();
	}

	private void whileStat() {
		next();
		
		/*Chamada de funcao*/
		expr();
		
		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		
		next();
		while(lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END) {
			
			/*Chamada de funcao*/
			statement();
		}

		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");
	}

	private void ifStat() {
		next();
		expr();
		check(Token.LEFTCURBRACKET, "'{' expected after the 'if' expression");
		next();
		while(lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END && lexer.token != Token.ELSE) {
			statement();
		}

		check(Token.RIGHTCURBRACKET, "'}' was expected");
		
		if (lexer.token == Token.ELSE) {
			next();
			check(Token.LEFTCURBRACKET, "'{' expected after 'else'");
			next();
			while(lexer.token != Token.RIGHTCURBRACKET) {
				statement();
			}

			check(Token.RIGHTCURBRACKET, "'}' was expected");
		}
	}

	private void writeStat() {
		next();
		check(Token.DOT, "a '.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");
		String printName = lexer.getStringValue();
		expr();
	}

	private void expr() {

	}

	private void fieldDec() {
		next();
		type();
		if(lexer.token != Token.ID) {
			this.error("A field name was expected");
		
		} else {
			while(lexer.token == Token.ID ) {
				next();
				if(lexer.token == Token.COMMA) {
					next();
				
				} else {
					break;
				}
			}
		}

	}

	private void type() {
		if(lexer.token == Token.INT || lexer.token == Token.BOOLEAN || lexer.token == Token.STRING) {
			next();
		
		} else if(lexer.token == Token.ID) {
			next();
		
		} else {
			this.error("A type was expected");
		}

	}


	private void qualifier() {
		if(lexer.token == Token.PRIVATE) {
			next();
		
		} else if(lexer.token == Token.PUBLIC) {
			next();
		
		} else if(lexer.token == Token.OVERRIDE) {
			next();
			if (lexer.token == Token.PUBLIC) {
				next();
			}
		
		} else if(lexer.token == Token.FINAL) {
			next();
			if(lexer.token == Token.PUBLIC) {
				next();
			
			} else if(lexer.token == Token.OVERRIDE) {
				next();
				if(lexer.token == Token.PUBLIC) {
					next();
				}
			}
		}
	}

	public Statement assertStat() {

		next();
		int lineNumber = lexer.getLineNumber();
		
		/*Chamada de Fun��o*/
		expr();
		
		/*Verifica se � o token � : */
		if(lexer.token != Token.COMMA) {
			this.error("',' expected after the expression of the 'assert' statement");
		}

		next();
		
		/*Verifica se � o token � uma string*/
		if(lexer.token != Token.LITERALSTRING) {
			this.error("A literal string expected after the ',' of the 'assert' statement");
		}

		String message = lexer.getLiteralStringValue();
		lexer.nextToken();
		
		/*Verifica se � o token � ; */
		if(lexer.token == Token.SEMICOLON) {
			next();
		}

		return null;
	}

	/*Tranforma um inteiro em uma express�o do tipo inteiro */
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
				|| token == Token.LEFTPAR || token == Token.NULL
				|| token == Token.ID || token == Token.LITERALSTRING;

	}

	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private ErrorSignaller	signalError;

}
