/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class PrimaryExpr extends Expr{

    //Construtor da classe
    public PrimaryExpr(String qualifier, ArrayList<Id> ids, Id idc, ExpressionList exprlist) {
        this.qualifier = qualifier;
        this.ids = ids;
        this.idc = idc;
        this.exprlist = exprlist;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(ids.size() == 2) {
            int contador = 0;
            for(Id s: ids) {
                s.genJava(pw);
                contador++;
                if(contador == 1) {
                    pw.print(".");
                }
            }
            pw.println("();");

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
        
        } else if(qualifier.equals("self")) {
            pw.printIdent("this");

            if(ids.size() == 1) {
                pw.print(".");
                for(Id s: ids) {
                    s.genJava(pw);
                }
                pw.println("();");

            } else if(ids.size() == 1 && idc != null) {
                pw.print(".");
                for(Id s: ids) {
                    s.genJava(pw);
                }
                pw.print(".");
                idc.genJava(pw);
                pw.print("(");
                exprlist.genJava(pw);
                pw.println(");");

            } else if(ids.size() == 2) {
                pw.print(".");
                int contador = 0;
                for(Id s: ids) {
                    s.genJava(pw);
                    contador++;
                    if(contador == 1) {
                        pw.print(".");
                    }
                }
                pw.println("();");

            } else if(idc != null) {
                pw.print(".");
                idc.genJava(pw);
                pw.print("(");
                exprlist.genJava(pw);
                pw.println(");");
            
            }
        
        } else if(qualifier.equals("super")) {
            if(idc != null) {

            } else {

            }
        }
    }

    //Metodo que retorna o tipo
    public Type getType() {
        return Type.undefinedType;
    }

    //Atributos da classe
    private String qualifier;
    private ArrayList<Id> ids;
    private Id idc;
    private ExpressionList exprlist;
}