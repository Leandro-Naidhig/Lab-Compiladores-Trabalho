/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

import lexer.Token;

public class CompositeExpr extends Expr {

    //Construtor da classe
	public CompositeExpr(Expr exprLeft, Token Op, Expr exprRight ) {
	    this.exprLeft = exprLeft;
		this.Op = Op;
		this.exprRight = exprRight;
    }

    public void genC(PW pw, ArrayList<Member> membros){
        if(exprLeft != null) {
            pw.print("(");
            
            if(Op != null && Op.toString().equals("++")) {

                if(exprLeft.getType() == Type.intType) {

                    pw.print("concatStrings(intToString(");
                    exprLeft.genC(pw, membros);
                    pw.print(")");

                } else {
                    pw.print("concatStrings(");
                    exprLeft.genC(pw, membros);
                }

                if(exprRight != null) {
                    
                    if(exprRight.getType() == Type.intType) {
                        pw.print(", intToString(");
                        exprRight.genC(pw, membros);
                        pw.print(")");          
                    } else {
                        pw.print(", ");
                        exprRight.genC(pw, membros);
                    }
                }

                pw.print("))");
            
            } else {
                exprLeft.genC(pw, membros);

                if(Op != null) {
                    pw.print(" " + Op.toString() + " ");	
                } else {
                    pw.print(")");
                }
                
                if(exprRight != null) {
                    exprRight.genC(pw, membros);
                    pw.print(")");
                }
            }
            
		} else if(Op != null) {
            pw.print("(");
            pw.print(Op.toString());

            if(exprRight != null) {
                exprRight.genC(pw, membros);
            }
            pw.print(")");
        }
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        
        if(exprLeft != null) {

            pw.print("(");
			exprLeft.genJava(pw);

			if(Op != null) {

                if(Op.toString().equals("++")) {
                    pw.print(" " + "+" + " ");
                } else {
                    pw.print(" "+ Op.toString() + " ");
                }
				
			} else {
				pw.print(")");
            }
            
			if(exprRight != null) {
				exprRight.genJava(pw);
				pw.print(")");
            }
            
		} else if(Op != null) {
            pw.print("(");
            pw.print(Op.toString());

            if(exprRight != null) {
                exprRight.genJava(pw);
            }
            pw.print(")");
        }
    }

    //Metodo para retornar o tipo da composicao da expressao
    public Type getType() {
        if(Op == Token.EQ || Op == Token.NEQ || Op == Token.LE || Op == Token.LT ||
		   Op == Token.GE || Op == Token.GT || Op == Token.AND || Op == Token.OR ) {
            return Type.booleanType;
        
        } else if(Op == Token.PLUSPLUS) {
            return Type.stringType;
           
		} else {
			return Type.intType;
		}
    }

    //Atributos da classe
    private Expr exprLeft;
    private Expr exprRight;
    private Token Op;
}