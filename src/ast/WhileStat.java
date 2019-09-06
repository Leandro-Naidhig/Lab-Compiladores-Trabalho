/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class WhileStat extends Statement{

    //Construtor da classe WhileStat
    public WhileStat(StatementList statList, Expression expr) {
        this.statList = statList;
        this.expr = expr;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
		pw.printIdent("while(");
		expr.genC(pw);
		pw.println(") { ");

		if (statList != null) {
            pw.add();
			statList.genC(pw);
            pw.sub();
        }
        
        pw.printlnIdent("}");
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da Classe
    private StatementList statList;
    private Expression expr;
}