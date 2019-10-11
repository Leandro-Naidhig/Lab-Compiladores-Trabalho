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
    public ClassDec(String classname, ClassDec superclassname, MemberList memberList, Boolean isOpen) {
        super(classname);
        this.classname = classname;
        this.superclassname = superclassname;
        this.memberList = memberList;
        this.isOpen = isOpen;  
    }

    //Metodo para retornar a classe pai da classe atual
    public ClassDec getSuperClass() {
        return superclassname;
    }

    //Metodo que retorna se a classe pode ser herdada na hierarquia
    public boolean getOpen() {
        return isOpen;
    }

    //Metodo para retornar o nome da classe
    public String getCname() {
        return classname;
    }

    //Metodo para retornar os membros da class
    public MemberList getMembros(){
        return memberList;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {

        pw.print(classname);
        if(superclassname != null) {
            pw.print(" extends ");
            pw.print(superclassname.getCname());
        }
        pw.println(" {");
        pw.add();
        memberList.genJava(pw);
        pw.sub();
        pw.printlnIdent("}");
    }

    //Atributos da classe
    private String classname;
    private ClassDec superclassname;
    private MemberList memberList;
    private Boolean isOpen;

}