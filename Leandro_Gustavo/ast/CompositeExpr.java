/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import lexer.Token;

public class CompositeExpr extends Expr {

    //Construtor da classe
	public CompositeExpr(Expr exprLeft, Token Op, Expr exprRight ) {
	    this.exprLeft = exprLeft;
		this.Op = Op;
		this.exprRight = exprRight;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
       
        if(exprLeft != null) {
            pw.print("(");
            
            if(Op != null && Op.toString().equals("++")) {

                String expr1 = "";
                String expr2 = "";

                if(exprLeft.getType() == Type.intType) {
                    expr1 = String.valueOf(((BasicValue)exprLeft).getValue().getValue());

                } else {
                    expr1 = ((BasicValue)exprLeft).getString().getName();
                }

                if(exprRight != null) {
                    
                    if(exprRight.getType() == Type.intType) {
                        
                        //Falta verificar o valor da variavel
                        if(exprRight instanceof IdExpr) {
                            expr1 = ((IdExpr)exprRight).getMember1().getName();

                        } else if(exprRight instanceof BasicValue) {
                            expr2 = String.valueOf(((BasicValue)exprRight).getValue().getValue());
                        }
                    
                    } else {
                        expr2 = ((BasicValue)exprRight).getString().getName();
                    }
                }

                expr1 = expr1 + expr2;
                pw.print(expr1 + ")");
            
            } else {
                exprLeft.genC(pw);

                if(Op != null) {
                    pw.print(" " + Op.toString() + " ");	
                } else {
                    pw.print(")");
                }
                
                if(exprRight != null) {
                    exprRight.genC(pw);
                    pw.print(")");
                }
            }
            
		} else if(Op != null) {
            pw.print("(");
            pw.print(Op.toString());

            if(exprRight != null) {
                exprRight.genC(pw);
            }
            pw.print(")");
        }
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
		} else {
			return Type.intType;
		}
    }

    //Atributos da classe
    private Expr exprLeft;
    private Expr exprRight;
    private Token Op;
}