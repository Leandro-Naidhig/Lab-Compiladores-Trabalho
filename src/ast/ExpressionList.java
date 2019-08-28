/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

public class ExpressionList {

    //Construtor da classe
    public ExpressionList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    //Metodo para geracao do codigo em Java
    public void genC(PW pw) {

        int contador = 0;

        for(Expr s : exprList) {
            s.genC(pw);
            contador++;
            
            if((exprList.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private ArrayList<Expr> exprList;
}