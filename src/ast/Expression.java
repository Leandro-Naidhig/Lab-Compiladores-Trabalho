/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Expression {

    //Construtor da classe
    public Expression(SimpleExpression exprLeft, String relation, SimpleExpression exprRight) {
        this.exprLeft = exprLeft;
        this.relation = relation;
        this.exprRight = exprRight;
    }

    //Metodo para geracao do codigo em Java
    public void genC(PW pw) {
        
        exprLeft.genC(pw);
        if(exprRight != null) {
            pw.print(relation);
            exprRight.genC(pw);
        }
    }
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        
        exprLeft.genC(pw);
        if(exprRight != null) {
            pw.print(relation);
            exprRight.genC(pw);
        }
    }

    //Atributos da classe
    private SimpleExpression exprLeft;
    private SimpleExpression exprRight;
    private String relation;
}