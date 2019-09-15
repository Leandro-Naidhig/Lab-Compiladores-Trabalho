/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class FieldDec {

    //Construtor da classe 
    public FieldDec(Type type, IdList idList, Boolean isSemiColon) {
        this.type = type;
        this.idList = idList;
        this.isSemiColon = isSemiColon;
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

    //Atributos da classe
    private Boolean isSemiColon;
    private Type type;
    private IdList idList;
}