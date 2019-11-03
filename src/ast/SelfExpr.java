/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class SelfExpr extends Expr{

    //Construtor da classe
    public SelfExpr(Member id1,  Member id2, ExpressionList exprlist) {
        this.id1 = id1;
        this.id2 = id2;
        this.exprlist = exprlist;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
        pw.printIdent("self");

        if(id1 != null) {

            if(id1 instanceof MethodDec) {
                pw.print("->" + ((MethodDec)id1).getName());
            } else {
                pw.print("->" + ((Variable)id1).getName());
            }

            if(id2 != null) {
                if(exprlist != null) {
                    pw.print("->");
                    pw.print(((MethodDec)id2).getName());
                    pw.print("(");
                    exprlist.genC(pw);
                    pw.print(")");
                    
                } else {
                    pw.print("->");
                    pw.print(((MethodDec)id2).getName() + "()");
                }
            }

            if(id1 instanceof MethodDec) {
                if(exprlist != null) {
                    pw.print("(");
                    exprlist.genC(pw);
                    pw.print(")");
                } else {
                    pw.print("()");
                }
                
            }
        }
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        pw.print("this");

        if(id1 != null) {

            if(id1 instanceof MethodDec) {
                pw.print("." + ((MethodDec)id1).getName());
            } else {
                pw.print("." + ((Variable)id1).getName());
            }

            if(id2 != null) {
                if(exprlist != null) {
                    pw.print(".");
                    pw.print(((MethodDec)id2).getName());
                    pw.print("(");
                    exprlist.genJava(pw);
                    pw.print(")");
                    
                } else {
                    pw.print(".");
                    pw.print(((MethodDec)id2).getName() + "()");
                }
            }

            if(id1 instanceof MethodDec) {
                if(exprlist != null) {
                    pw.print("(");
                    exprlist.genJava(pw);
                    pw.print(")");
                } else {
                    pw.print("()");
                }
                
            }
        }
    } 
     
    //Metodo que retorna o tipo
    public Type getType() {
        if(id2 != null) {
            return id2.getType();
        } else {
            return id1.getType();    
        }
    }

    //Atributos da classe
    private Member id1;
    private Member id2;
    private ExpressionList exprlist;
}