/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class ReturnStat extends Statement {

    //Construtor da classe
    public ReturnStat(Expr expr) {
        this.expr = expr;
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        pw.printIdent("return ");
        expr.genC(pw, membros);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printIdent("return ");
        expr.genJava(pw);
    }

    //Atributos da classe
    private Expr expr;
}