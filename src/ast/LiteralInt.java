/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

public class LiteralInt extends Expr {
    
    //Construtor da classe
    public LiteralInt(int value) { 
        this.value = value;
    }
    
    //Retorna o valor
    public int getValue() {
        return value;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
    }

    //Metodo para geracao do codigo em C
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("" + value);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.print("" + value);
    }
    
    //Retorna o tipo
    public Type getType() {
        return Type.intType;
    }
    
    //Atributos da classe
    private int value;
}
