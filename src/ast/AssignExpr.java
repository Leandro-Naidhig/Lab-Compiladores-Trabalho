/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

public class AssignExpr extends Statement {

    //Construtor da classe WhileStat
    public AssignExpr(Expr exprLeft, Expr exprRight) {
        this.exprLeft = exprLeft;
        this.exprRight = exprRight;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        pw.printIdent("");
        exprLeft.genC(pw, membros);
		if (exprRight != null) {
            pw.print(" = ");
            exprRight.genC(pw, membros);
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printIdent("");
        exprLeft.genJava(pw);
		if (exprRight != null) {
            pw.print(" = ");
            exprRight.genJava(pw);
        }
        pw.printNotIdent("");
    }

    //Atributos da Classe
    private Expr exprLeft;
    private Expr exprRight;
}