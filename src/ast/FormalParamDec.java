/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class FormalParamDec {

    //Construtor da classe
    public FormalParamDec(ArrayList<ParamDec> paramDec) {
        this.paramDec = paramDec;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        int contador = 0;

        for(ParamDec s : paramDec) {
            s.genC(pw);
            contador++;
            
            if((paramDec.size()-1) != contador) {
                pw.print(", ");
            }
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        int contador = 0;

        for(ParamDec s : paramDec) {
            s.genJava(pw);
            contador++;
            
            if(paramDec.size() != contador) {
                pw.print(", ");
            }
        }
    }

    //Atributos da classe
    private ArrayList<ParamDec> paramDec;

}