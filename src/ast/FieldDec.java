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
        this.isSemiColon = isSemiColon;
        this.qualifier = qualifier;
    }

    //Metodo para retornar o qualificador dos atributos
    public String getQualifier() {
        return qualifier;
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
        pw.print("private ");
        type.getName();
        pw.print(" ");
        idList.genJava(pw);
        pw.print(";");
    }

    //Metodo para retornar o tipo
    public Type getType() {
        return type;
    }

    //Metodo para retornar o nome
    public String getName() { 
        return null;       
    }

    //Atributos da classe
    private Boolean isSemiColon;
    private Type type;
    private IdList idList;
    private String qualifier;
}