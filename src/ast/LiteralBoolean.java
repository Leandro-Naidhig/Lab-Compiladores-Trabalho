/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class LiteralBoolean extends Expr {

    //Construtor da classe
    public LiteralBoolean( boolean value ) {
        this.value = value;
    }

    //Metodo para geracao do codigo em C
    @Override
	public void genC( PW pw, boolean putParenthesis ) {
       pw.print( value ? "1" : "0" );
    }

    //Metodo para retornar o tipo
    @Override
	public Type getType() {
        return Type.booleanType;
    }

    //Atributos da classe
    public static LiteralBoolean True  = new LiteralBoolean(true);
    public static LiteralBoolean False = new LiteralBoolean(false);
    private boolean value;
}
