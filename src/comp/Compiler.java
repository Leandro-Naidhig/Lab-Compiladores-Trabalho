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

	//Program = {Annot} ClassDec {{Annot} ClassDec}
	private Program program(ArrayList<CompilationError> compilationErrorList) {
		
		//Lista de Annots e ClassDecs
		ArrayList<MetaobjectAnnotation> metaobjectCallList = new ArrayList<>();
		ArrayList<TypeCianetoClass> CianetoClassList = new ArrayList<>();

		//Criação do objeto Program
		Program program = new Program(CianetoClassList, metaobjectCallList, compilationErrorList);
		
		//Variavel responsável por verificar se existe algum erro na gramatica
		//Por padrao e inicializada com false
		boolean thereWasAnError = false;

		//Se for uma classe 
		while(lexer.token == Token.CLASS ||
			 (lexer.token == Token.ID && lexer.getStringValue().equals("open") ) ||
			  lexer.token == Token.ANNOT) {
			
			try {
				while (lexer.token == Token.ANNOT) {
					//Chamada de funcao para "Annot"
					metaobjectAnnotation(metaobjectCallList);
				}

				/*Chamada de função*/
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

	//Annot = "@" Id [ "(" { AnnotParam} ")"]
	@SuppressWarnings("incomplete-switch")
	private void metaobjectAnnotation(ArrayList<MetaobjectAnnotation> metaobjectAnnotationList) {

		String name = lexer.getMetaobjectName();
		int lineNumber = lexer.getLineNumber();

		//Verifica se o ID (name) é uma palavra chave
		if(lexer.get_keywords(name) != null) {
			error(name + " is a keyword");
		}

		next();

		//Inicia uma lista de AnnotParam
		ArrayList<Object> metaobjectParamList = new ArrayList<>();


		boolean getNextToken = false;

		//Caso o token for um abre paranteses
		if(lexer.token != Token.LEFTPAR) {
			error("'(' expected before ID");

		} else {
			
			next();

			//Verifica se trata de alguma sentença pertencente ao AnnotParam
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

			//Caso o token não for um fecha parenteses
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

		//Contem uma lista de metaobjetos
		metaobjectAnnotationList.add(new MetaobjectAnnotation(name, metaobjectParamList));
		
		if(getNextToken){
			next();
		}
	}

	//ClassDec =  ["open"] "class" ID ["extends" ID] MemberList "end"
	private void classDec() {
		
		//Professor falta arrumar isso
		if(lexer.token == Token.ID && lexer.getStringValue().equals("open")) {
			//class open
		}

		//Verifica se o token é a palavra class
		if ( lexer.token != Token.CLASS ) {
			error("'class' expected");
		}

		next();
		
		//Verifica se o token é um ID
		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		}

		//Recupera o nome da classe
		String className = lexer.getStringValue();
		next();

		//Verifica se o ID (className) é uma palavra chave
		if(lexer.get_keywords(className) != null) {
			error(className + " is a keyword");
		}

		//Verifica se o token é Extends
		if (lexer.token == Token.EXTENDS) {
			next();
			
			//Verifica se o token é um ID
			if (lexer.token != Token.ID) {
				error("Identifier expected");
			}
			String superclassName = lexer.getStringValue();

			//Verifica se o ID (superclassName) é uma palavra chave
			if(lexer.get_keywords(superclassName) != null) {
				error(superclassName + " is a keyword");
			}

			next();
		}

		/*Chamada de função*/
		memberList();
		
		/*Verifica se o token é um END*/
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
			/*Chamada de função*/
			ifStat();
			checkSemiColon = false;
			break;
		case WHILE:
			/*Chamada de função*/
			whileStat();
			checkSemiColon = false;
			break;
		case RETURN:
			/*Chamada de função*/
			returnStat();
			break;
		case BREAK:
			/*Chamada de função*/
			breakStat();
			break;
		case SEMICOLON:
			/*Chamada de função*/
			next();
			break;
		case REPEAT:
			/*Chamada de função*/
			repeatStat();
			break;
		case VAR:
			/*Chamada de função*/
			localDec();
			break;
		case ASSERT:
			/*Chamada de função*/
			assertStat();
			break;
		default:
			if(lexer.token == Token.ID && lexer.getStringValue().equals("Out")) {
				
				/*Chamada de função*/
				writeStat();
			
			} else {

				/*Chamada de função*/
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
		
		/*Chamada de Função*/
		expr();
		
		/*Verifica se é o token é : */
		if(lexer.token != Token.COMMA) {
			this.error("',' expected after the expression of the 'assert' statement");
		}

		next();
		
		/*Verifica se é o token é uma string*/
		if(lexer.token != Token.LITERALSTRING) {
			this.error("A literal string expected after the ',' of the 'assert' statement");
		}

		String message = lexer.getLiteralStringValue();
		lexer.nextToken();
		
		/*Verifica se é o token é ; */
		if(lexer.token == Token.SEMICOLON) {
			next();
		}

		return null;
	}

	/*Tranforma um inteiro em uma expressão do tipo inteiro */
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
