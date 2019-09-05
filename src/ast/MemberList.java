/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;
import java.util.ArrayList;

public class MemberList {

  // Construtor da classe
    public MemberList(ArrayList<Integer> qualifierPos, ArrayList<String> qualifier, ArrayList<Member> member) {
      this.qualifier = qualifier;
      this.qualifierPos = qualifierPos;
      this.member = member;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private ArrayList<String> qualifier;
    private ArrayList<Integer> qualifierPos;
    private ArrayList<Member> member;
}