/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;
import java.util.ArrayList;
import java.util.Iterator;

public class MemberList {

  // Construtor da classe
    public MemberList(ArrayList<Member> member) {
      this.member = member;
    }

    //Metodo para retornar o arraylist de membros
    public ArrayList<Member> getArray() {
      return member;
    } 

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      
      Iterator<Member> m = member.iterator();
      
      while (m.hasNext()) {

        Member m1 = m.next();

        if(!(q1.equals("public"))) {

          String[] arraysplit = q1.split(" ", 2);
  
          for (String a : arraysplit) {
            if(a.equals("override")) {
              pw.printlnIdent("@Override");
            }
          }
          
          pw.printIdent(q1);
          pw.print(" "); 
       
        } else {
          pw.printIdent("public");
          pw.print(" ");
        }
        m1.genJava(pw);
      }
    }

    //Atributos da classe
    private ArrayList<Member> member;
}