/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ExpressionFactor extends Factor{

    public ExpressionFactor(Expression expr) {
        this.expr = expr;
    }

    private Expression expr;
    
}