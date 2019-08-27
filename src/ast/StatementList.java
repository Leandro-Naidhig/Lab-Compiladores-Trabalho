/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.*;

public class StatementList {

    //Construtor da classe StatementList
    public StatementList(ArrayList<Statement> arrayStatement) {
		this.arrayStatement = arrayStatement;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
		for(Statement s : arrayStatement) {
			s.genC(pw);
		}
	}

    //Atributos da classe
    private ArrayList<Statement> arrayStatement;
}