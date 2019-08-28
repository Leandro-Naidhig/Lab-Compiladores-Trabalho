/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class LocalDec {

    //Construtor da classe
    public LocalDec(Type tipo, IdList idList, Expr expr) {
        this.tipo = tipo;
        this.idList = idList;
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        tipo.getCname();
        idList.genC(pw);

        //Caso houver uma expressao
        if(expr != null) {
            pw.print(" = ");
            expr.genC(pw);
        }
    }

    //Atributos da classe
    private Type tipo;
    private IdList idList;
    private Expr expr;
}