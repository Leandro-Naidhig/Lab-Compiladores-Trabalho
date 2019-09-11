/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

public class SumSubExpression {

    //Construtor da classe
    public SumSubExpression(ArrayList<Term> term, ArrayList<String> lowOperator){
        this.term = term;
        this.lowOperator = lowOperator;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private ArrayList<Term> term;
    private ArrayList<String> lowOperator;

}