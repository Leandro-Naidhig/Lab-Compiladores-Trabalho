/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class LocalDec {

    //Construtor da classe
    public LocalDec(Variable var, IdList idList, Expr expr) {
        this.var = var;
        this.idList = idList;
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {

    }

    //Atributos da classe
    private Variable var;
    private IdList idList;
    private Expr expr;
}