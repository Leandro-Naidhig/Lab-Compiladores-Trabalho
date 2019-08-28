/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class AssignExpr {

    //Construtor da classe WhileStat
    public AssignExpr(Expr exprLeft, Expr exprRight) {
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
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da Classe
    private Expr exprLeft;
    private Expr exprRight;
}