/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

/** This class represents a metaobject annotation as <code>{@literal @}ce(...)</code> in <br>
 * <code>
 * @ce(5, "'class' expected") <br>
 * clas Program <br>
 *     public void run() { } <br>
 * end <br>
 * </code>
 *
   @author Jos�

 */

public class ClassDec {

    //Construtor da clase
    public ClassDec(String name, MemberList memberList) {
        this.name = name;
        this.memberList = memberList;   
    }

    //Metodo para geracao do codigo em C
    public String getName() {
		  return name;
    }
    
    //Metodo para geracao do codigo em Java
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private String name;
    private MemberList memberList;

}