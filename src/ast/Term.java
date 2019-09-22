/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

public class Term {

    //Construtor da classe
    public Term(ArrayList<SignalFactor> signalFactor, ArrayList<Token> highOperator) {
        this.signalFactor = signalFactor;
        this.highOperator = highOperator;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        Iterator<SignalFactor> fator = signalFactor.iterator();
        Iterator<Token> operador = highOperator.iterator();
        SignalFactor fator1 = null;
        Token operador1 = null;

        while(fator.hasNext()) {
            fator1 = fator.next();
            fator1.genJava(pw);

            if(operador.hasNext()) {
                operador1 = operador.next();
                pw.print(operador1.toString());
                fator1 = fator.next();
                fator1.genJava(pw);
            }
        }
    }

    private ArrayList<SignalFactor> signalFactor;
    private ArrayList<Token> highOperator;
}