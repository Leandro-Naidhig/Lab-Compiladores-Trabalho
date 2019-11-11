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
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, ArrayList<Member> membros) {
        int contador = 0;

        if(tipo instanceof ClassDec) {
            pw.printIdent("_class_" + tipo.getName() + " ");
        } else {
            pw.printIdent(tipo.getName() + " ");
        }
    
        if(expr != null) {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {
                s.genC(pw);
                pw.print(" = ");
                expr.genC(pw, membros);
                contador++;
                
                if(ids.size() != contador) {
                    pw.print(", ");
                }
            }
        
        } else {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {

                if(s.getType() instanceof ClassDec) {
                    pw.print("*");
                    s.genC(pw);
                    contador++;
                } else {
                    s.genC(pw);
                    contador++;
                }
                
                if(ids.size() != contador) {
                    pw.print(", ");
                }
            }
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
        
        } else {
            ArrayList<Variable> ids = idList.getArray();

            for(Variable s : ids) {
                s.genJava(pw);
                contador++;
                
                if(ids.size() != contador) {
                    pw.print(", ");
                }
            }
        }
    }

    //Atributos da classe
    private Type tipo;
    private IdList idList;
    private Expr expr;
}