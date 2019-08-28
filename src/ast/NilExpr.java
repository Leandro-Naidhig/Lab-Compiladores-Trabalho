/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class NilExpr extends Expr {
   
   //Metodo para geracao do codigo em C
   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NIL");
   }

   //Metodo para geracao do codigo em Java
   public void genJava(PW pw) {
   }
   
   //Retorna o tipo
   public Type getType() {
      //# corrija
      return null;
   }
}