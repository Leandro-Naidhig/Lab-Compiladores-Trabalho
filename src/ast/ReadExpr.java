/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ReadExpr extends Factor{

    public ReadExpr(String name) {
        this.name = name;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(name.equals("readInt")) {
            pw.print("variable.nextInt()");
        } else {
            pw.print("variable.readLine()");
        }
    }

    private String name;
}