/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class MethodDec extends Member {

    //Construtor da classe 
    public MethodDec(Id id_idColon, FormalParamDec formParDec, Type type, StatementList statementList, String qualifier) {
        this.id_idColon = id_idColon;
        this.formParDec = formParDec;
        this.type = type;
        this.statementList = statementList;
        this.qualifier = qualifier;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, String currentClass) {
        if(type != null) {
            pw.print(type.getName());
        } else {
            pw.print("void");
        }

        pw.print(" ");

        if(formParDec == null) {
            pw.print("_" + currentClass);
            id_idColon.genC(pw);
            pw.print("(_class_"  +  currentClass + " *self)");
        } else {
            pw.print("_" + currentClass);
            id_idColon.genC(pw);
            pw.print("(");
            pw.print("_class_" +  currentClass + " *self, ");
            formParDec.genC(pw);
            pw.print(")");
        }
        
        pw.println(" {");
        pw.add();
        statementList.genC(pw);
        pw.sub();
        pw.printlnIdent("}");
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(type != null) {
            pw.print(type.getName());
        } else {
            pw.print("void");
        }
        
        pw.print(" ");
        
        if(formParDec == null) {
            id_idColon.genJava(pw);
            pw.print("()");
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
        pw.printlnIdent("}");
    }

    //Retorna o numero de parametros
    public int getNumParam() {

        if(formParDec == null) {
            return 0;
        } else {
            return formParDec.getNumberParam();
        }
    }

    //Retorna a descricao do metodo
    public FormalParamDec getParamDec() {
        return formParDec;
    }

    //Metodo para retornar o qualificador do metodo
    public String getQualifier() {
        return qualifier;
    }

    //Metodo para retornar o nome do metodo
    public String getName() {
        return id_idColon.getName();
    }

    //Metodo para retornar o tipo do metodo
    public Type getType() {
        return type;
    }

    //Atributos da classe
    private Id id_idColon;
    private FormalParamDec formParDec;
    private Type type;
    private StatementList statementList;
    private String qualifier;
}