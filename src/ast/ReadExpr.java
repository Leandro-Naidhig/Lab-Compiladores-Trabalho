/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class ReadExpr extends Expr{

    //Construtor da classe
    public ReadExpr(String name) {
        this.name = name;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean value) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(name.equals("readInt")) {
            pw.print("Scan.imputValue.nextInt()");
        } else {
            pw.print("Scan.imputValue.readLine()");
        }
    }

    //Metodo que retorna o tipo
    public Type getType() {
        if(name.equals("readInt")) {
            return Type.intType;
        } else {
            return Type.stringType;
        }
    }

    //Atributos da classe
    private String name;
}