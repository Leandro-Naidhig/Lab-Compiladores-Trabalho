/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class ExpressionFactor extends Expr {

    //Construtor da classe
    public ExpressionFactor(Expr expr) {
        this.expr = expr;
    }

    public void genC(PW pw, ArrayList<Member> membros){
        pw.print("(");
        expr.genC(pw, membros);
        pw.print(")");
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.print("(");
        expr.genJava(pw);
        pw.print(")");
    }

    //Metodo que retorna o tipo da expressao
    public Type getType() {
        return expr.getType();
    }

    //Atributos da classe
    private Expr expr;
    
}