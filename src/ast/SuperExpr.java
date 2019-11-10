/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class SuperExpr extends Expr {

    //Construtor da classe
    public SuperExpr(Member id1, ExpressionList exprlist, ClassDec currentClass) {
        this.id1 = id1;
        this.exprlist = exprlist;
        this.currentClass = currentClass;
    }

    public void genC(PW pw, ArrayList<Member> membros){
        ClassDec classePai = currentClass.getSuperClass();
        pw.print("_" + classePai.getCname() + "_" + ((MethodDec)id1).getName() + "((_class_" + classePai.getCname() + " *) self");
        
        if(exprlist != null) {
            pw.print(", ");
            exprlist.genC(pw, membros);
            pw.print(")");
                    
        } else {
            pw.print(")");
        }
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }
    
    //Metodo para geracao do codigo em java
    public void genJava(PW pw) {
        pw.print("super.");
        pw.print(((MethodDec)id1).getName());
        
        if(exprlist != null) {
            pw.print("(");
            exprlist.genJava(pw);
            pw.print(")");
                    
        } else {
            pw.print("()");
        }
    } 
     
    //Metodo que retorna o tipo
    public Type getType() {
        return id1.getType();
    }

    //Atributos da classe
    private Member id1;
    private ExpressionList exprlist;
    private ClassDec currentClass;
}