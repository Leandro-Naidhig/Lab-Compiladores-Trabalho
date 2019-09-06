/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class LocalDec extends Statement{

    //Construtor da classe
    public LocalDec(Type tipo, IdList idList, Expression expr) {
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

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private Type tipo;
    private IdList idList;
    private Expression expr;
}