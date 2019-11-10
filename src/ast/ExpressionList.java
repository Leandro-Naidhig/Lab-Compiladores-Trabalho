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

    //Metodo para retornar uma lista de expressoes
    public ArrayList<Expr> getArrayList() {
        return exprList;
    }

    //Metodo para retornar o numero de expressoes do array
    public int getNumberExpr() {
        return exprList.size();
    }

    public void genC(PW pw, ArrayList<Member> membros) {
        int contador = 0;

        for(Expr s : exprList) {
            s.genC(pw, membros);
            contador++;

            if(exprList.size() != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genC(PW pw) {
        int contador = 0;

        for(Expr s : exprList) {
            s.genC(pw);
            contador++;

            if(exprList.size() != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        int contador = 0;

        for(Expr s : exprList) {
            s.genJava(pw);
            contador++;
            
            if(exprList.size() != contador) {
                pw.print(", ");
            }
        }
    }

    //Atributos da classe
    private ArrayList<Expr> exprList;
}