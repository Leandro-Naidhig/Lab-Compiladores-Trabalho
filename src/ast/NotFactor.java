/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

import java.util.ArrayList;

public class NotFactor extends Expr {

    //Construtor da classe
    public NotFactor(Expr factor) {
      this.factor = factor;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
      pw.print("!(");
      factor.genC(pw, membros);
      pw.print(")");
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      pw.print("!(");
      factor.genJava(pw);
      pw.print(")");
    }

    //Metodo que retorna o tipo do fator
    public Type getType() {
      return factor.getType();
    }

    //Atributos da classe
    private Expr factor;
}