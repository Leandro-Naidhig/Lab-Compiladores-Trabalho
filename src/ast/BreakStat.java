/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class BreakStat extends Statement {

    //Construtor da Classe
    public BreakStat() {
        this.name = "break";
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
        pw.printlnIdent(name);
    }
    
    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        pw.printlnIdent(name);
    }

    //Atributos da Classe
    private String name;

}