/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class AssertStat extends Statement{

    //Construtor da classe WhileStat
    public AssertStat(String str, Expr expr) {
        this.str = str;
        this.expr = expr;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printlnIdent("assert " + expr + " : " + "\"" + str + "\"");
    }

    //Atributos da Classe
    private String str;
    private Expr expr;
}