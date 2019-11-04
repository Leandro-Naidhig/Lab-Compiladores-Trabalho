/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

public class ObjectCreation extends Expr{

    //Construtor da classe
    public ObjectCreation(Variable var) {
      this.var = var;
    }
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
      pw.print("new_" + var.getName() + "()");
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      pw.print("new " + var.getName() + "()");
    }

    //Metodo que retorna o tipo da classe
    public Type getType() {
      return var.getType();
    }

    //Atributos da classe
    private Variable var;
}