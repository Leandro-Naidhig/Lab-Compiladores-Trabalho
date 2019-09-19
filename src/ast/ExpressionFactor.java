/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ExpressionFactor extends Factor{

    public ExpressionFactor(Expression expr) {
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        expr.genJava(pw);
    }

    private Expression expr;
    
}