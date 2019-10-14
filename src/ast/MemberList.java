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
      MethodDec metodo = null;
      FieldDec atributos = null;
      
      //Recupera todos os membros do array
      while (m.hasNext()) {

        Member m1 = m.next();

        //Verifica qual o tipo do membro
        if(m1 instanceof MethodDec) {
          metodo = (MethodDec)m1;
        } else {
          atributos = (FieldDec)m1;
        }

        //Caso o membro for um FieldDec
        if(atributos != null) {
          atributos.genJava(pw);
        
        //Caso o membro for um MethodDec
        } else {
          
          if(!(metodo.getQualifier().equals("public"))) {

            String[] arraysplit = metodo.getQualifier().split(" ", 2);
    
            for (String a : arraysplit) {
              if(a.equals("override")) {
                pw.printlnIdent("@Override");
              }
            }
            
            pw.printIdent(metodo.getQualifier() + " ");
         
          } else {
            pw.printIdent("public ");
          }
          m1.genJava(pw);
        }
      }
    }

    //Atributos da classe
    private ArrayList<Member> member;
}