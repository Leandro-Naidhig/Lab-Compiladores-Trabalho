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
        if(!(s instanceof IfStat) && !(s instanceof WhileStat)) {
          pw.println(";");
        }
      }
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      for(Statement s : arrayStatement) {
        s.genJava(pw);
        if(!(s instanceof IfStat) && !(s instanceof WhileStat)) {
          pw.println(";");
        }
      }
    }

    //Metodo para retornar o array de statement
    public ArrayList<Statement> getArray() {
      return arrayStatement;
    }

    //Atributos da classe
    private ArrayList<Statement> arrayStatement;
}