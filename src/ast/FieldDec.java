/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class FieldDec {

    //Construtor da classe 
    public FieldDec(Type type, IdList idList) {
        this.type = type;
        this.idList = idList;
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
    }

    //Atributos da classe
    private Type type;
    private IdList idList;
}