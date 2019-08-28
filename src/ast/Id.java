/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Id {

    //Construtor da classe
    public Id(String name) {
        this.name = name;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        pw.print(name);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

    //Atributos da classe
    private String name;
}
