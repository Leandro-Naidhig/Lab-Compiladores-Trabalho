/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class MethodDec {

    //Construtor da classe 
    public MethodDec(Id id_idColon, FormalParamDec formParDec, Type type, StatementList statementList) {
        this.id_idColon = id_idColon;
        this.formParDec = formParDec;
        this.type = type;
        this.statementList = statementList;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(type != null) {
            type.getName();
        } else {
            pw.print("void");
        }
        pw.print(" ");
        if(formParDec == null) {
            id_idColon.genJava(pw);
        } else {
            id_idColon.genJava(pw);
            pw.print("(");
            formParDec.genJava(pw);
            pw.print(")");
        }
        pw.println(" {");
        pw.add();
        statementList.genJava(pw);
        pw.sub();
        pw.println("}");
    }

    //Atributos da classe
    private Id id_idColon;
    private FormalParamDec formParDec;
    private Type type;
    private StatementList statementList;
}