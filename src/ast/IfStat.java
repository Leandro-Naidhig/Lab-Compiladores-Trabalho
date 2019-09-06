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

        //Caso existir instrucoes dentro do IF
        if(statIf != null) {
            pw.add();
            
            for(Statement s : statIf) {
                s.genC(pw);
            }

            pw.sub();    
        }

        pw.print("} ");

        //Caso existir o else
        if(statElse != null) {
            pw.print("else { ");
            pw.add();
            
            for(Statement s : statElse) {
                s.genC(pw);
            }

            pw.print("} ");
            pw.sub();    
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private Expression expr;
    private ArrayList<Statement> statElse;
    private ArrayList<Statement> statIf;
}