/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class IfStat extends Statement {

    //Construtor da classe
    public IfStat(Expression expr, ArrayList<Statement> statIf, ArrayList<Statement> statElse) {
        this.expr = expr;
        this.statIf = statIf;
        this.statElse = statElse;
    }

    //metodo para geracao do codigo em C
    public void genC(PW pw) {
        pw.printIdent("if(");
        expr.genC(pw);
        pw.println(") {");

        if(statIf != null) {
            pw.add();
            for(Statement s : statIf) {
                s.genC(pw);
            }
            pw.sub();
        }

        if(statElse != null) {
            pw.print(" } else { ");
            pw.add();    
            for(Statement s : statElse) {
                s.genC(pw);
            }
            pw.sub();    
        }
        
        pw.println("} ");
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
            }
            pw.sub();    
        }

        if(statElse != null) {
            pw.print("} else { ");
            for(Statement s : statElse) {
                s.genJava(pw);
            }
            pw.sub();
        }
        pw.println("}");
    }

    //Atributos da classe
    private Expression expr;
    private ArrayList<Statement> statElse;
    private ArrayList<Statement> statIf;
}