/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ParamDec {

    //Construtor da classe
    public ParamDec(Variable name) {
        this.name = name;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        name.genJava(pw);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        name.genJava(pw);
    }

    //Metodo para retornar o tipo
    public Type getType() {
        return name.getType();
    }

    //Atributos da classe
    private Variable name;
}