/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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

    //Metodo que retorna os membros do vetor em C
    public ArrayList<Member> getMembersVT() {
        return membrosVT;
    }

    //Metodo que retorna os membros do vetor em C
    public void setMembersVT(ArrayList<Member> membros) {
        this.membrosVT = membros;
    }


    //Metodo para geracao do codigo em C
    public void genC(PW pw) {

        //Estrutura da classe para declaracao de metodos e variaveis de instancia
        pw.println("typedef struct _St_" + classname + " {");
        pw.add();
        
        //Listas para montagem das funcoes da classe atual
        ArrayList<Member> membros = new ArrayList<>();
        ArrayList<String> nomeClasses = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();
        ArrayList<String> variaveis = new ArrayList<>();
        int contador = 0;
        String classe = classname;
        MemberList lista = this.getMembros();
        ClassDec superclasse = this.getSuperClass();
        int flag = 0;
        int quantidade = 0;

        pw.printlnIdent("Func *vt;");

        //Recupera todos as variaveis de instancia da superclasse e da propria classe
        do {

            for(Member s: lista.getArray()) {

                //Verifica todas as instancias da classe
                if(s instanceof FieldDec) {

                    if(s.getType() instanceof ClassDec) {
                        tipos.add("struct _St_" + s.getType().getCname() + " *");
                    } else {
                        tipos.add(s.getType().getCname() + " ");
                    }

                    contador = 0;
                    String nomes = "";

                    for(Variable v: ((FieldDec)s).getIdList().getArray()) {

                        nomes = nomes + ("_" + classe + "_" + v.getName()) ;
                        contador++;

                        if(((FieldDec)s).getIdList().getArray().size() != contador) {
                            nomes = nomes + (", ");
                        }
                    }
                    nomes = nomes + (";");
                    variaveis.add(nomes);
                }
            }

            if(flag == 1) {

                if(superclasse.getSuperClass() != null) {
                    superclasse = superclasse.getSuperClass();
                    lista = superclasse.getMembros();
                    classe = superclasse.getCname();
                } else {
                    superclasse = null;
                }
                    
            } else {
                
                if(superclasse != null) {   
                    lista = superclasse.getMembros();
                    classe = superclasse.getCname();
                    flag = 1;
                }
            }
            
        } while(superclasse != null);

        Collections.reverse(tipos);
        Collections.reverse(variaveis);


        //Transforma em iteradores
		Iterator<String>parsTipos = tipos.iterator();
		Iterator<String>parsVariaveis = variaveis.iterator();

		//imprime todas as variaveis de instancia da classe
		while(parsTipos.hasNext() && parsVariaveis.hasNext()){
            pw.printlnIdent(parsTipos.next() + parsVariaveis.next());
		}

        pw.sub();
        pw.println("}_class_" + classname + ";");
        pw.println("");

        //Criacao de uma nova classe
        pw.println("_class_" + classname + " *new_" + classname + "(void);");
        pw.println("");

        flag = 0;
        quantidade = 0;
        classe = classname;
        lista = this.getMembros();
        superclasse = this.getSuperClass();

        //Recupera todos os metodos da superclasse
        //Enquanto for encontrado uma superclasse
        do {

            boolean encontrado = false;
            int pos = 0;
            String classtemp = "";

            //Percorre todos os membros da superclasse
            for(Member s: lista.getArray()) {

                if(s instanceof MethodDec) {
                    //Caso for um metodo e ele nao for privado
                    if(!((MethodDec)s).getQualifier().equals("private")) {
                        
                        //Verifica se nao e um override da superclasse
                        for(Member v: membros) {
                            if(((MethodDec)v).getName().equals(((MethodDec)s).getName())){
                                pos = membros.lastIndexOf(v);
                                classtemp = nomeClasses.get(pos);
                                encontrado = true;
                            }   
                        }

                        if(encontrado) {
                            membros.remove(pos);
                            nomeClasses.remove(pos);
                            membros.add(s);
                            nomeClasses.add(classtemp);
                            encontrado = false;

                        } else {
                            membros.add(s);
                            nomeClasses.add(classe);
                            quantidade++;
                        }
                    }
                }       
            }

            if(flag == 1) {

                if(superclasse.getSuperClass() != null) {
                    superclasse = superclasse.getSuperClass();
                    lista = superclasse.getMembros();
                    classe = superclasse.getCname();

                } else {
                    superclasse = null;
                }
                    
            } else {
                
                if(superclasse != null) {   
                    lista = superclasse.getMembros();
                    classe = superclasse.getCname();
                    flag = 1;
                }
            }
            
        } while(superclasse != null);

        //Inverte as duas listas, ja que se trata de uma hierarquia
        Collections.reverse(membros);
        Collections.reverse(nomeClasses);
        setMembersVT(membros);
        contador = 0; 


        //Geracao de todas os metodos
        for(Member s: memberList.getArray()) {
            if(s instanceof MethodDec) {
                ((MethodDec)s).genC(pw, classname, membros);
            }
        }

        pw.println("");

        //Criacao de uma tabela de metodos da classe
        pw.println("Func VTclass_" + classname + "[] = {");
        pw.add();

        //Transforma em iteradores
		Iterator<Member>parsMetodos = membros.iterator();
		Iterator<String>parsNames = nomeClasses.iterator();

		//Percorre os dois vetores verificando se sao similares em seus tipos
		while(parsMetodos.hasNext() && parsNames.hasNext()){
            
            pw.printIdent("(void(*)())_" + parsNames.next() + "_" + parsMetodos.next().getName());
            contador++;

            if(contador != quantidade) {
                pw.println(",");
                    
            } else {
                pw.println("");
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

        if(classname.equals("Program")) {
            
            pw.println("int main() {");
            pw.add();
            pw.printlnIdent("_class_Program *program;");  
            pw.printlnIdent("program = new_Program();");
            pw.printIdent("((void (*)(_class_Program*)) program->vt[");

            contador = 0;
            
            //Verificar a posicao do metodo run
            for(Member s: membrosVT) {
                if(s instanceof MethodDec) {
                    if(((MethodDec)s).getName().equals("run")) {
                        break;
                    } else {
                        contador++;
                    }
                }
            } 

            pw.println(contador + "])(program);");
            pw.printlnIdent("return 0;");
            pw.sub();
            pw.println("}");
        }
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
    private ArrayList<Member> membrosVT;

}