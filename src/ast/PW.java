/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.io.PrintWriter;

public class PW {

	//Construtor da classe
	public PW(PrintWriter out) {
		this.out = out;
		currentIndent = 0;
	}

	public void add() {
		currentIndent += step;
	}

	public void sub() {
		if ( currentIndent < step ) {
			System.out.println("Internal compiler error: step (" + step + ") is greater then currentIndent (" + currentIndent + ") in method sub of class PW");
		}
		currentIndent -= step;
	}

	public void set(int indent) {
		currentIndent = indent;
	}

	public void printIdent(String s) {
		out.print( space.substring(0, currentIndent) );
		out.print(s);
	}

	public void printNotIdent(String s) {
		out.print(space.substring(0, 0));
		out.print(s);
	}


	public void printlnIdent(String s) {
		out.print( space.substring(0, currentIndent) );
		out.println(s);
	}

	public void print(String s) {
		out.print(s);
	}

	public void println(String s) {
		out.println(s);
	}

	public void println() {
		out.println("");
	}

	//Atributos da classe
	int currentIndent = 0;
	public int step = 3;
	private PrintWriter out;
	static final private String space = "                                                                                                        ";

}


