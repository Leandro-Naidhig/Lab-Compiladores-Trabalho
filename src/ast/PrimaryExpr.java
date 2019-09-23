/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class PrimaryExpr extends Factor{

    //Construtor da classe
    public PrimaryExpr(String qualifier, ArrayList<Id> ids, Id idc, ExpressionList exprlist, Factor readExpr) {
        this.qualifier = qualifier;
        this.ids = ids;
        this.idc = idc;
        this.exprlist = exprlist;
        this.readExpr = readExpr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(readExpr != null) {
            readExpr.genJava(pw);
        } else if(ids.size() == 1 && idc == null) {
            for(Id s: ids) {
                s.genJava(pw);
            }
        } else if(exprlist != null && qualifier == null) {
            for(Id s: ids) {
                s.genJava(pw);
            }
            pw.print(".");
            idc.genJava(pw);
            pw.print("(");
            exprlist.genJava(pw);
            pw.println(");");
        }
    }

    private String qualifier;
    private ArrayList<Id> ids;
    private Id idc;
    private ExpressionList exprlist; 
    private Factor readExpr;
}