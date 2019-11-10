/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

abstract public class Member {

    //Metodo para geracao do codigo em C
    abstract public void genC(PW pw);

    //Metodo para geracao do codigo em Java
    abstract public void genJava(PW pw);

    //Metodo para retornar o tipo
    abstract public Type getType();

    //Metodo para retornar o nome
    abstract public String getName();
}