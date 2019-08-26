package ast;

public class NilExpr extends Expr {
    
   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NIL");
   }
   
   public Type getType() {
      //# corrija
      return null;
   }
}