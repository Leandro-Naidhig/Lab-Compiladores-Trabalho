/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class WriteStat extends Statement{

    //Construtor da classe
    public WriteStat(ExpressionList exprList) {
        this.exprList = exprList;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private ExpressionList exprList;

}