/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class LiteralInt extends Expr {
    
    //Construtor da classe
    public LiteralInt( int value ) { 
        this.value = value;
    }
    
    //Retorna o valor
    public int getValue() {
        return value;
    }

    //Metodo para geracao do codigo em C
    public void genC( PW pw, boolean putParenthesis ) {
        pw.printIdent("" + value);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        System.out.println(value);
        pw.print("" + value);
    }
    
    //Retorna o tipo
    public Type getType() {
        return Type.intType;
    }
    
    //Atributos da classe
    private int value;
}
