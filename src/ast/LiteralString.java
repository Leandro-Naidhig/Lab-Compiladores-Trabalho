/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class LiteralString extends Expr {
    
    //Construtor da classe
    public LiteralString( String literalString ) { 
        this.literalString = literalString;
    }
    
    //Metodo para geracao do codigo em C
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print(literalString);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.print(literalString);
    }
    
    //Retorna o tipo 
    public Type getType() {
        return Type.stringType;
    }

    //Atributos da classe
    private String literalString;
}
