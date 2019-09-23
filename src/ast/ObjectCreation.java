/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

public class ObjectCreation extends Factor{

    //Construtor da classe
    public ObjectCreation(ClassDec classe) {
      this.classe = classe;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      pw.print("new ");
      pw.print(classe.getCname());
    }

    //Atributos da classe
    private ClassDec classe;
}