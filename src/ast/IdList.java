/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class IdList {

    //Construtor da classe
    public IdList(ArrayList<Variable> identifiers) {
        this.identifiers = identifiers;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        
        int contador = 0;
        for(Variable s : identifiers) {
            s.genC(pw);
            contador++;
            
            if((identifiers.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        
        int contador = 0;
        for(Variable s : identifiers) {
            s.genJava(pw);
            contador++;

            if((identifiers.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para recuperacao do array de ids
    public ArrayList<Variable> getArray() {
        return identifiers;
    }

    //Atributos da classe
    private ArrayList<Variable> identifiers;
}
