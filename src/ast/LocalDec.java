/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class LocalDec extends Statement {

    //Construtor da classe
    public LocalDec(Type tipo, IdList idList, Expr expr) {
        this.tipo = tipo;
        this.idList = idList;
        this.expr = expr;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {

        int contador = 0;
        tipo.getCname();

        //Caso houver uma expressao
        if(expr != null) {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {
                s.genC(pw);
                pw.print(" = ");
                expr.genC(pw);
                contador++;
                
                if((ids.size()-1) != contador) {
                    pw.print(", ");
                }
            }
            pw.print(";");
        
        } else {
            idList.genJava(pw);
            pw.print(";");
        }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        int contador = 0;
        pw.printIdent(tipo.getName() + " ");
    
        if(expr != null) {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {
                s.genJava(pw);
                pw.print(" = ");
                expr.genJava(pw);
                contador++;
                
                if(ids.size() != contador) {
                    pw.print(", ");
                }
            }
            pw.println(";");
        
        } else {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {
                s.genJava(pw);
                contador++;
                
                if(ids.size() != contador) {
                    pw.print(", ");
                }
            }
            pw.println(";");
        }
    }

    //Atributos da classe
    private Type tipo;
    private IdList idList;
    private Expr expr;
}