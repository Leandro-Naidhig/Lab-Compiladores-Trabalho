/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import lexer.Token;

public class Expression {

    //Construtor da classe
    public Expression(SimpleExpression exprLeft, Token relation, SimpleExpression exprRight) {
        this.exprLeft = exprLeft;
        this.relation = relation;
        this.exprRight = exprRight;
    }

    //Metodo para geracao do codigo em Java
    public void genC(PW pw) {
        
        exprLeft.genC(pw);
        if(exprRight != null) {
            pw.print(" ");
            pw.print(relation.toString());
            exprRight.genC(pw);
        }
    }
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        
        if(exprRight == null) {
            exprLeft.genJava(pw);
        } else {
            pw.print("(");
            exprLeft.genJava(pw);
            pw.print("");
            pw.print(relation.toString());
            pw.print("");
            exprRight.genJava(pw);
            pw.print(")");
        }
    }

    //Atributos da classe
    private SimpleExpression exprLeft;
    private SimpleExpression exprRight;
    private Token relation;
}