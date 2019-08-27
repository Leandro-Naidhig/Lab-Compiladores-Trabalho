/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ReturnStat extends Statement {

    //Construtor da classe
    public ReturnStat(Expr expr) {
        this.expr = expr;
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw) {
        pw.printIdent("return ");
        expr.genC(pw);
        pw.println(";");
    }

    //Atributos da classe
    private Expr expr;
}