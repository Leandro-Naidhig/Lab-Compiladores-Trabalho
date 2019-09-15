/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class IdList {

    //Construtor da classe
    public IdList(ArrayList<Id> ids) {
        this.ids = ids;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        
        int contador = 0;
        for(Id s : ids) {
            s.genC(pw);
            contador++;
            
            if((ids.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        
        int contador = 0;
        for(Id s : ids) {
            s.genJava(pw);
            contador++;

            if((ids.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Atributos da classe
    private ArrayList<Id> ids;
}
