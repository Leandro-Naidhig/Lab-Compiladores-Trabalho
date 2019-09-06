/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ReadExpr extends Factor{

    public ReadExpr(String name) {
        this.name = name;
    }

    private String name;
}