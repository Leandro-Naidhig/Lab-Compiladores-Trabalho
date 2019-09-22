/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import lexer.Token;

public class SignalFactor {

    //Construtor da classe
    public SignalFactor(Token signal, Factor factor) {
        this.signal = signal;
        this.factor = factor;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(signal != null) {
            pw.print(signal.toString());
            pw.print(" ");
        }
        factor.genJava(pw);
    }

    //Atributos da classe
    private Token signal;
    private Factor factor;
}