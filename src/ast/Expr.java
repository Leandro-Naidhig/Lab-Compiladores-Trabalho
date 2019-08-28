/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

abstract public class Expr extends Statement {
  
    //Metodo abstrato para geracao do codigo em C
    abstract public void genC( PW pw, boolean putParenthesis );
    
    //Meotodo para geracao do codigo em C
    @Override
	public void genC(PW pw) {
		this.genC(pw, false);
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Metodo abstrato para retornar o tipo da expressao
    abstract public Type getType();
}