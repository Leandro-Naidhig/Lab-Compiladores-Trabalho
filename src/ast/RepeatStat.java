/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class RepeatStat {

    //Construtor da classe
    public RepeatStat(StatementList statementList, Expr expr) {
        this.statementList = statementList;
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private StatementList statementList;
    private Expr expr;
}