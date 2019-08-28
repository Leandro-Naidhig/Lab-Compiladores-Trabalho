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
        for(Id s : ids) {
			s.genC(pw);
		}
    }

    //Atributos da classe
    private ArrayList<Id> ids;
}
