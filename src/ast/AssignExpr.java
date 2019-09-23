/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class AssignExpr extends Statement{

    //Construtor da classe WhileStat
    public AssignExpr(Expression exprLeft, Expression exprRight) {
        this.exprLeft = exprLeft;
        this.exprRight = exprRight;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    
        exprLeft.genC(pw);
		if (exprRight != null) {
            pw.print(" = ");
            exprRight.genC(pw);
            pw.print(";");
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {

        exprLeft.genJava(pw);
		if (exprRight != null) {
            pw.print(" = ");
            exprRight.genJava(pw);
            pw.println(";");
        }
    }

    //Atributos da Classe
    private Expression exprLeft;
    private Expression exprRight;
}