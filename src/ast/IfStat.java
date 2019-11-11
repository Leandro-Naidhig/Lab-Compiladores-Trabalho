/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class IfStat extends Statement {

    //Construtor da classe
    public IfStat(Expr expr, ArrayList<Statement> statIf, ArrayList<Statement> statElse) {
        this.expr = expr;
        this.statIf = statIf;
        this.statElse = statElse;
    }

    //metodo para retonar o statement do if
    public ArrayList<Statement> getArrayIf() {
        return statIf;
    }

    //metodo para retonar o statement do else
    public ArrayList<Statement> getArrayElse() {
        return statElse;
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        pw.printIdent("if(");
        expr.genC(pw, membros);
        pw.println(") {");

        if(statIf != null) {
            pw.add();
            for(Statement s : statIf) {
                s.genC(pw, membros);
                pw.println(";");
            }
            pw.sub();    
        }

        if(statElse != null) {
            pw.printlnIdent("} else { ");
            pw.add();
            for(Statement s : statElse) {
                s.genC(pw, membros);
                pw.println(";");
            }
            pw.sub();   
        }
        pw.printlnIdent("}");
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printIdent("if(");
        expr.genJava(pw);
        pw.println(") {");

        if(statIf != null) {
            pw.add();
            for(Statement s : statIf) {
                s.genJava(pw);
                pw.println(";");
            }
            pw.sub();    
        }

        if(statElse != null) {
            pw.printlnIdent("} else { ");
            pw.add();
            for(Statement s : statElse) {
                s.genJava(pw);
                pw.println(";");
            }
            pw.sub();   
        }
        pw.printlnIdent("}");
    }

    //Atributos da classe
    private Expr expr;
    private ArrayList<Statement> statElse;
    private ArrayList<Statement> statIf;
}