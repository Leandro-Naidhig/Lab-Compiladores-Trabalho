/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

public class ObjectCreation extends Expr{

    //Construtor da classe
    public ObjectCreation(ClassDec classe) {
      this.classe = classe;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      pw.print("new ");
      pw.print(classe.getCname() + "()");
    }

    //Metodo que retorna o tipo da classe
    public Type getType() {
      return classe;
    }

    //Atributos da classe
    private ClassDec classe;
}