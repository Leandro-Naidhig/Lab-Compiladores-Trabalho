/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class IdExpr extends Expr {

    //Construtor da classe
    public IdExpr(Member id1,  Member id2, ExpressionList exprlist) {
        this.id1 = id1;
        this.id2 = id2;
        this.exprlist = exprlist;
    }

    //Metodo para retornar o primeiro membro da expressao
    public Member getMember1() {
        return id1;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        if(id1 != null && id2 == null) {
            id1.genC(pw);
        
        } else if(id1 != null && id2 != null) {

            pw.print("((void (*)(_class_" + id1.getType().getCname() + "* ");
            int contador = 0;
            ClassDec classe = ((ClassDec)id1.getType());

            if(exprlist != null) {

                exprListArray = exprlist.getArrayList();
                pw.print(", ");

                for(Expr s: exprListArray) {
                    pw.print(s.getType().getCname());
                    contador++;
                
                    if(exprListArray.size() != contador) {
                        pw.print(", ");
                    }
                }

                pw.print("))");
                id1.genC(pw);
                contador = 0;

                //recuperar a posicao do metodo na lista
                for(Member s : classe.getMembersVT()) {
                    if(s instanceof MethodDec) { 
                        if(((MethodDec)s).getName().equals(id2.getName())) {
                            break;
                        } else {
                            contador++;
                        }
                    }
                }
                
                pw.print("->vt[" + contador + "])(");
                id1.genC(pw);
                pw.print(", ");
                exprlist.genC(pw, membros);
                pw.print(")");
            
            } else {
                
                pw.print("))");
                id1.genC(pw);
                contador = 0;

                //recuperar a posicao do metodo na lista
                for(Member s : classe.getMembersVT()) {
                    if(s instanceof MethodDec) { 
                        if(((MethodDec)s).getName().equals(id2.getName())) {
                            break;
                        } else {
                            contador++;
                        }
                    }
                }

                pw.print("->vt[" + contador + "])(");
                id1.genC(pw);
                pw.print(")");
            }
        }
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        
        id1.genJava(pw);

        if(id2 != null) {
            if(exprlist != null) {
                pw.print(".");
                pw.print(id2.getName());
                pw.print("(");
                exprlist.genJava(pw);
                pw.print(")");
                    
            } else {
                pw.print(".");
                pw.print(id2.getName());
                    
                if(id2 instanceof MethodDec) {
                    pw.print("()");
                }
            }

            if(id1 instanceof MethodDec) {
                pw.print("()");
            }
        }
    } 
     
    //Metodo que retorna o tipo
    public Type getType() {
        if(id2 != null) {
            return id2.getType();
        } else {
            return id1.getType();    
        }
    }

    //Atributos da classe
    private Member id1;
    private Member id2;
    private ExpressionList exprlist;
    private ArrayList<Expr> exprListArray;
}