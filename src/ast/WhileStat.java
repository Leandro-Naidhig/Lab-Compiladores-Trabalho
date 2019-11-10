/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class WhileStat extends Statement {

    //Construtor da classe WhileStat
    public WhileStat(StatementList statList, Expr expr) {
        this.statList = statList;
        this.expr = expr;
    }

    public void genC(PW pw, ArrayList<Member> membros){
        pw.printIdent("while(");
		expr.genC(pw, membros);
		pw.println(") { ");

		if (statList != null) {
            pw.add();
			statList.genC(pw, membros);
            pw.sub();
        }
        
        pw.printlnIdent("}");
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printIdent("while(");
		expr.genJava(pw);
		pw.println(") { ");

		if (statList != null) {
            pw.add();
			statList.genJava(pw);
            pw.sub();
        }
        
        pw.printlnIdent("}");
    }

    //Atributos da Classe
    private StatementList statList;
    private Expr expr;
}