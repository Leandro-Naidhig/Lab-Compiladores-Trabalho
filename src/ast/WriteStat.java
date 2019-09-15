/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class WriteStat extends Statement{

    //Construtor da classe
    public WriteStat(ExpressionList exprList, String mode) {
        this.exprList = exprList;
        this.mode = mode;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(mode.equals("print:")) {
            pw.print("System.out.println(");
            exprList.genJava(pw);
            pw.print(");");
        } else {
            pw.print("System.out.print(");
            exprList.genJava(pw);
            pw.print(");");
        }
    }

    //Atributos da classe
    private ExpressionList exprList;
    private String mode;

}