/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.*;

public class CompStatement {

    //Construtor da classe
    public CompStatement(ArrayList<Statement> arrayStatement) {
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
    }

    //Atributos da classe
    private ArrayList<Statement> arrayStatement;
}