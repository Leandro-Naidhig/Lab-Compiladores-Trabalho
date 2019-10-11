/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class SuperExpr extends Expr{

    //Construtor da classe
    public SuperExpr(Member id1, ExpressionList exprlist) {
        this.id1 = id1;
        this.exprlist = exprlist;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        pw.printIdent("super.");
        id1.genJava(pw);
        
        if(exprlist != null) {
            pw.print("(");
            exprlist.genJava(pw);
            pw.println(");");
                    
        } else {
            pw.println("();");
        }

        if(id1 instanceof MethodDec) {
            pw.println("();");
        }
    } 
     
    //Metodo que retorna o tipo
    public Type getType() {
        return id1.getType();
    }

    //Atributos da classe
    private Member id1;
    private ExpressionList exprlist;
}