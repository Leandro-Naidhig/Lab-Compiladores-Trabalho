/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class RepeatStat extends Statement{

    //Construtor da classe
    public RepeatStat(StatementList statementList, Expr expr) {
        this.statementList = statementList;
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printlnIdent("do {");
        pw.add();
        statementList.genJava(pw);
        pw.sub();
        pw.printIdent("} while(");
        expr.genJava(pw);
        pw.println(");");
    }

    //Atributos da classe
    private StatementList statementList;
    private Expr expr;
}