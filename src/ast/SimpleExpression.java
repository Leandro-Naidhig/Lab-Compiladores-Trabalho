/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class SimpleExpression {

    //Construtor da classe
    public SimpleExpression(ArrayList<SumSubExpression> arraySumSub) {
        this.arraySumSub = arraySumSub;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {

        //System.out.println(arraySumSub);
        Boolean flag = true;
        for(SumSubExpression s : arraySumSub) {
            if(flag) {
                s.genJava(pw);
                flag = false;
            } else {
                pw.print(" + ");
                s.genJava(pw); 
            }
        }
    }


    //Atributos da classe
    private ArrayList<SumSubExpression> arraySumSub;
}