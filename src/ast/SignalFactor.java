/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class SignalFactor {

    //Construtor da classe
    public SignalFactor(String signal, Factor factor) {
        this.signal = signal;
        this.factor = factor;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(signal != null) {
            pw.print(signal);
            pw.print(" ");
        }
        factor.genJava(pw);
    }

    //Atributos da classe
    private String signal;
    private Factor factor;
}