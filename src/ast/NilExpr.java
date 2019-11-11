/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class NilExpr extends Expr {

   //Metodo para geracao do codigo em C
   public void genC( PW pw, boolean putParenthesis ) {
   }

   //Metodo para geracao do codigo em C
   public void genC( PW pw, ArrayList<Member> membros) {
      pw.printIdent("null");
   }

   //Metodo para geracao do codigo em Java
   public void genJava(PW pw) {
      pw.print("null");
   }
   
   //Retorna o tipo
   public Type getType() {
      return Type.undefinedType;
   }
}