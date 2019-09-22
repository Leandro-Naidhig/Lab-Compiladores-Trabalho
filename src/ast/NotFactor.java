/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

public class NotFactor extends Factor{

    //Construtor da classe
    public NotFactor(Factor factor) {
      this.factor = factor;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
      pw.printIdent("!(");
      factor.genJava(pw);
      pw.print(")");
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      pw.printIdent("!(");
      factor.genJava(pw);
      pw.print(")");
    }

    //Atributos da classe
    private Factor factor;
}