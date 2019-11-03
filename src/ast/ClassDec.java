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

    //Metodo para retornar o nome da classe em C
    public String getCname() {
        return classname;
    }

    //Metodo para retornar o nome da classe em Java
    public String getJavaname() {
        return classname;
    }

    //Metodo para retornar os membros da classe
    public MemberList getMembros(){
        return memberList;
    }

    //Metodo para setar os membros da classe
    public void setMembros(MemberList memberList) {
        this.memberList = memberList;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {

        //Estrutura da classe para declaracao de metodos e variaveis de instancia
        pw.println("typedef struct _St_" + classname + " {");
        pw.add();
        int contador = 0;
        int quantidade = 0;

        //Caso existam membros na classe (metodos ou variaveis de instancia)
        if(memberList != null) {
            pw.printlnIdent("Func *vt;");
            
            for(Member s: memberList.getArray()) {

                //Verifica todas as instancias da classe
                if(s instanceof FieldDec) {

                    pw.printIdent(s.getType().getCname() + " ");
                    contador = 0;

                    for(Variable v: ((FieldDec)s).getIdList().getArray()) {

                        pw.print("_" + classname + "_" + v.getName());
                        contador++;

                        if(((FieldDec)s).getIdList().getArray().size() != contador) {
                            pw.print(", ");
                        }
                    }
                    pw.println(";");
                }
            }
        }
        pw.sub();
        pw.println("}_class_" + classname + ";");
        pw.println("");

        //Criacao de uma nova classe
        pw.println("_class_" + classname + " *new_" + classname + "(void);");
        pw.println("");


        //Geracao de todas os metodos
        for(Member s: memberList.getArray()) {
            if(s instanceof MethodDec) {
                ((MethodDec)s).genC(pw, classname);
                quantidade++;
            }
        }

        pw.println("");

        //Criacao de uma tabela de metodos da classe
        pw.println("Func VTclass_" + classname + "[] = {");
        pw.add();
        contador = 0;

        //Geracao da tabela de metodos da classe
        for(Member s: memberList.getArray()) {
            if(s instanceof MethodDec) {
                pw.printIdent("(void(*)())_" + classname + "_" + ((MethodDec)s).getName());
                contador++;

                if(contador != quantidade) {
                    pw.println(",");
                
                } else {
                    pw.println("");
                }
            }
        }

        pw.sub();
        pw.println("};");
        pw.println("");

        //Caso a classe nao for abstrata, declara uma funcao que aloca memoria para um objeto da classe
        pw.println("_class_" + classname + " *new_" + classname + "() {");
        pw.add();
        pw.printlnIdent("_class_" + classname + " *t;");
        pw.printlnIdent("");
        pw.printlnIdent("if((t = malloc(sizeof(_class_" + classname + "))) != NULL) {");
        pw.add();
        pw.printlnIdent("t->vt = VTclass_" + classname + ";");
        pw.sub();
        pw.printlnIdent("}");
        pw.printlnIdent("");
        pw.printlnIdent("return t;");
        pw.sub();
        pw.println("}");
        pw.println("");
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {

        pw.print(classname);
        if(superclassname != null) {
            pw.print(" extends ");
            pw.print(superclassname.getJavaname());
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