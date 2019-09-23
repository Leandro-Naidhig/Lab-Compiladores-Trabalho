/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

public class SumSubExpression {

    //Construtor da classe
    public SumSubExpression(ArrayList<Term> term, ArrayList<Token> lowOperator){
        this.term = term;
        this.lowOperator = lowOperator;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {

        Iterator<Term> termo = term.iterator();
        Iterator<Token> operador = lowOperator.iterator();
        Term termo1 = null;
        Token operador1 = null;

        while(termo.hasNext()) {
            termo1 = termo.next();

            if(operador.hasNext()) {
                pw.print("(");
                termo1.genJava(pw);
                operador1 = operador.next();
                pw.print(" ");
                pw.print(operador1.toString());
                pw.print(" ");
                termo1 = termo.next();
                termo1.genJava(pw);
                pw.print(")");
            
            } else {
                termo1.genJava(pw);
            }
        }
    }

    //Atributos da classe
    private ArrayList<Term> term;
    private ArrayList<Token> lowOperator;

}