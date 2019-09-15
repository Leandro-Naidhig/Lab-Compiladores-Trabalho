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

public class ClassDec extends Type{

    //Construtor da clase
    public ClassDec(String name1, ClassDec name2, MemberList memberList, Boolean isOpen) {
        super(name1);
        this.name1 = name1;
        this.name2 = name2;
        this.memberList = memberList;
        this.isOpen = isOpen;  
    }

    public String getCname() {
        return name1;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.print("public class ");
        pw.print(name1);
        pw.print(" {");

    }

    //Atributos da classe
    private String name1;
    private ClassDec name2;
    private MemberList memberList;
    private Boolean isOpen;

}