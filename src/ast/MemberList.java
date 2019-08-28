/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

public class MemberList {

    //Construtor da classe
    public MemberList(String qualifier, Member member) {
      this.qualifier = qualifier;
      this.member = member;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private String qualifier;
    private Member member;
}