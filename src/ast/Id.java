/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Id {

    //Construtor da classe
    public Id(String name) {
        if(name.indexOf(":") != -1) {
            int tamanho = name.length()-1;
            this.name = name.substring(0, tamanho);
        } else {
            this.name = name;
        }
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        pw.print("_" + name);
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.print(name);
    }

    //Metodo que retorna o nome do Id
    public String getName() {
        return name;
    }

    // Atributos da classe
    private String name;
}
