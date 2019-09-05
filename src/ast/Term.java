/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

public class Term {

    //Construtor da classe
    public Term(ArrayList<SignalFactor> signalFactor, ArrayList<String> highOperator) {
        this.signalFactor = signalFactor;
        this.highOperator = highOperator;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    private ArrayList<SignalFactor> signalFactor;
    private ArrayList<String> highOperator;
}