package comp;

import java.io.PrintWriter;
import java.util.ArrayList;
import ast.*;
import lexer.*;

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
		ArrayList<ClassDec> CianetoClassList = new ArrayList<>();

		//Cria��o do objeto Program
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
					//Chamada de funcao para "Annot"
					metaobjectAnnotation(metaobjectCallList);
				}

				//Chamada de funcao para "ClassDec"
				classDec(CianetoClassList);

				while (lexer.token == Token.ANNOT || lexer.token == Token.CLASS) {
					
					if(lexer.token == Token.ANNOT) {
						
						//Chamada de funcao para "Annot"
						metaobjectAnnotation(metaobjectCallList);
					} else {

						//Chamada de funcao para "ClassDec"
						classDec(CianetoClassList);
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

		//Verifica se o ID (name) � uma palavra chave
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

	//ClassDec =  ["open"] "class" ID ["extends" ID] MemberList "end"
	private void classDec(ArrayList<ClassDec> CianetoClassList) {
		
		//Professor falta arrumar isso
		if(lexer.token == Token.ID && lexer.getStringValue().equals("open")) {
			//class open
		}

		//Verifica se o token � a palavra class
		if ( lexer.token != Token.CLASS ) {
			error("'class' expected");
		}

		next();
		
		//Verifica se o token � um ID
		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		}

		//Recupera o nome da classe
		String className = lexer.getStringValue();
		next();

		//Verifica se o ID (className) � uma palavra chave
		if(lexer.get_keywords(className) != null) {
			error(className + " is a keyword");
		}

		//Verifica se o token � Extends
		if (lexer.token == Token.EXTENDS) {
			next();
			
			//Verifica se o token � um ID
			if (lexer.token != Token.ID) {
				error("Identifier expected");
			}
			String superclassName = lexer.getStringValue();

			//Verifica se o ID (superclassName) � uma palavra chave
			if(lexer.get_keywords(superclassName) != null) {
				error(superclassName + " is a keyword");
			}

			next();
		}

		//Chamada de fun��o para MemberList
		MemberList memberlist = memberList();
		
		/*Verifica se o token � um END*/
		if(lexer.token != Token.END) {
			error("'end' expected");
		}

		next();
		
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
				
				String qual = first + second + third;
				qualifiers.add(qual);
				qualifierspos.add(pos);
			   	pos++;
			}
			
			if(lexer.token == Token.VAR) {
				
				//Chamada de funcao
				field = fieldDec();
			
			} else if(lexer.token == Token.FUNC) {

				/*Chamada de funcao*/
				method = methodDec();
			
			} else {
				break;
			}

			Member membro = new Member(field, method);

			members.add(membro);
		}

		return new MemberList(qualifierspos, qualifiers, members);
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

	private MethodDec methodDec() {
		
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
			next();
			Type Tipo = type();


			if(lexer.token != Token.BOOLEAN || lexer.token != Token.INT || 
			   lexer.token != Token.STRING || lexer.token != Token.ID) {
				error("Expected type: Boolean, Int, String or ID");
			}
		}

		if(lexer.token != Token.LEFTCURBRACKET) {
			error("'{' expected");
		}

		next();

		// Chamada de funcao para "StatmentList"
		statementList();

		if ( lexer.token != Token.RIGHTCURBRACKET ) {
			error("'{' expected");
		}
		
		next();

		return new MethodDec();

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
				|| token == Token.LEFTPAR || token == Token.NIL
				|| token == Token.ID || token == Token.LITERALSTRING;

	}

	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private ErrorSignaller	signalError;

}
