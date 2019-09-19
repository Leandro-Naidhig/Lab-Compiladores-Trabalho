/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public abstract class Factor {

    //Metodo abstrato para geracao do codigo em C
    abstract public void genC(PW pw);

    //Metodo abstrato para geracao do codigo em Java
    abstract public void genJava(PW pw);
}