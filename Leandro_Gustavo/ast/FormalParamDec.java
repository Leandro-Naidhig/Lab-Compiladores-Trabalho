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
            
            if(paramDec.size() != contador) {
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

    //Metodo para retornar o numero de parametros
    public int getNumberParam() {
        return paramDec.size();
    }

    //Metodo para retornar os parametros
    public ArrayList<ParamDec> getParamDec() {
        return paramDec;
    }

    //Atributos da classe
    private ArrayList<ParamDec> paramDec;

}