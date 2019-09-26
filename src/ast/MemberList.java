/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;
import java.util.ArrayList;
import java.util.Iterator;

public class MemberList {

  // Construtor da classe
    public MemberList(ArrayList<String> qualifier, ArrayList<Member> member) {
      this.qualifier = qualifier;
      this.member = member;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      
      Iterator<String> q = qualifier.iterator();
      Iterator<Member> m = member.iterator();
      
      while (q.hasNext() && m.hasNext()) {

        String q1 = q.next();
        Member m1 = m.next();

        if(!(q1.equals(""))) {

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
    private ArrayList<String> qualifier;
    private ArrayList<Member> member;
}