/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class SelfExpr extends Expr {

    //Construtor da classe
    public SelfExpr(Member id1,  Member id2, ExpressionList exprlist, ClassDec currentClass) {
        this.id1 = id1;
        this.id2 = id2;
        this.exprlist = exprlist;
        this.currentClass = currentClass;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        
        int contador = 0;

        if(id1 != null && id2 != null) {

            if(id1 instanceof MethodDec) {
                pw.print("(self->_" + currentClass.getCname() + "_" + ((MethodDec)id1).getName());
            } else {
                pw.print("((" + ((MethodDec)id2).getType().getCname() + " (*)(_class_" + currentClass.getCname() + " *))" + "self->_" + currentClass.getCname() + "_" + ((Variable)id1).getName());
            }
            
            if(exprlist != null) {
                pw.print("->_" + currentClass.getCname() + "_");
                pw.print(((MethodDec)id2).getName());
                pw.print("(");
                exprlist.genC(pw);
                pw.print(")");
                    
            } else {
                
                ClassDec classe = ((ClassDec)id1.getType());
                pw.print("->vt[");
                contador = 0;

                //recuperar a posicao do metodo na lista
                for(Member s: classe.getMembersVT()) {
                    if(s instanceof MethodDec) { 
                        if(((MethodDec)s).getName().equals(id2.getName())) {
                            break;
                        } else {
                            contador++;
                        }
                    }
                }

                pw.print(contador + "])(self->_" + currentClass.getCname());
                id1.genC(pw);
                pw.print(")");

                }
        
        } else if(id1 != null && id2 == null) {

            if(id1 instanceof MethodDec) {

                if(((MethodDec)id1).getQualifier().equals("private")) {

                    pw.print("_" + currentClass.getCname() + "_" + ((MethodDec)id1).getName());

                    if(exprlist != null) {
                        pw.print("(self, ");
                        exprlist.genC(pw);
                        pw.print(")");
                    } else {
                        pw.print("(self)");
                    }

                } else {
                    
                    contador = 0;

                    //recuperar a posicao do metodo na lista
                    for(Member s: membros) {
                        if(s instanceof MethodDec) { 
                            if(((MethodDec)s).getName().equals(id1.getName())) {
                                break;
                            } else {
                                contador++;
                            }
                        }
                    }

                    pw.print("(self->vt[" + contador + "])");

                    if(exprlist != null) {
                        pw.print("(self, ");
                        exprlist.genC(pw, membros);
                        pw.print(")");
                    } else {
                        pw.print("(self)");
                    }
                }


            } else {

                pw.print("(self->_" + currentClass.getCname() + "_" + ((Variable)id1).getName() + ")");

            }
        }
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        pw.print("this");

        if(id1 != null) {

            if(id1 instanceof MethodDec) {
                pw.print("." + ((MethodDec)id1).getName());
            } else {
                pw.print("." + ((Variable)id1).getName());
            }

            if(id2 != null) {
                if(exprlist != null) {
                    pw.print(".");
                    pw.print(((MethodDec)id2).getName());
                    pw.print("(");
                    exprlist.genJava(pw);
                    pw.print(")");
                    
                } else {
                    pw.print(".");
                    pw.print(((MethodDec)id2).getName() + "()");
                }
            }

            if(id1 instanceof MethodDec) {
                if(exprlist != null) {
                    pw.print("(");
                    exprlist.genJava(pw);
                    pw.print(")");
                } else {
                    pw.print("()");
                }
                
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
    private ClassDec currentClass;
}