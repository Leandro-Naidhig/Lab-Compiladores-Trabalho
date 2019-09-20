/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.*;

public class StatementList {

    //Construtor da classe
    public StatementList(ArrayList<Statement> arrayStatement) {
		this.arrayStatement = arrayStatement;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
      for(Statement s : arrayStatement) {
        s.genC(pw);
      }
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      for(Statement s : arrayStatement) {
        System.out.println(s);
        s.genJava(pw);
      }
    }

    //Atributos da classe
    private ArrayList<Statement> arrayStatement;
}