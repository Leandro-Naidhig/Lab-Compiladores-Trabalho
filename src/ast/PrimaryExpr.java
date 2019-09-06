/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class PrimaryExpr extends Factor{

    //Construtor da classe
    public PrimaryExpr(String qualifier, ArrayList<Id> ids, Id idc, ExpressionList exprlist) {
        this.qualifier = qualifier;
        this.ids = ids;
        this.idc = idc;
        this.exprlist = exprlist;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    private String qualifier;
    private ArrayList<Id> ids;
    private Id idc;
    private ExpressionList exprlist; 
}