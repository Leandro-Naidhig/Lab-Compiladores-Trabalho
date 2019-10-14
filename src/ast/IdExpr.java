/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class IdExpr extends Expr{

    //Construtor da classe
    public IdExpr(Member id1,  Member id2, ExpressionList exprlist) {
        this.id1 = id1;
        this.id2 = id2;
        this.exprlist = exprlist;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        
        id1.genJava(pw);

        if(id2 != null) {
            if(exprlist != null) {
                pw.print(".");
                pw.print(id2.getName());
                pw.print("(");
                exprlist.genJava(pw);
                pw.println(");");
                    
            } else {
                pw.print(".");
                pw.print(id2.getName());
                    
                if(id2 instanceof MethodDec) {
                    pw.println("();");
                }
            }

            if(id1 instanceof MethodDec) {
                pw.println("();");
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