/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class FieldDec extends Member{

    //Construtor da classe 
    public FieldDec(Type type, IdList idList, Boolean isSemiColon, String qualifier) {
        this.type = type;
        this.idList = idList;
        this.qualifier = qualifier;
    }

    //Metodo para retornar o qualificador dos atributos
    public String getQualifier() {
        return qualifier;
    }

    //Metodo para retornar o tipo
    public Type getType() {
        return type;
    }

    //Metodo para retornar o nome
    public String getName() { 
        return null;       
    }

    //Metodo para retornar a lista de ids
    public IdList getIdList() {
        return idList;
    }
    
    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        type.getName();
        pw.print(" ");
        idList.genC(pw);
        pw.print(";");
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printIdent("private ");
        pw.print(type.getName());
        pw.print(" ");
        idList.genJava(pw);
        pw.println(";");
    }

    //Atributos da classe
    private Type type;
    private IdList idList;
    private String qualifier;
}