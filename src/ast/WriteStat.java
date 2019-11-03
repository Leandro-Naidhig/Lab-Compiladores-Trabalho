/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class WriteStat extends Statement {

    // Construtor da classe
    public WriteStat(ExpressionList exprList, String mode) {
        this.exprList = exprList;
        this.mode = mode;
    }

    // Metodo para geracao do codigo em C
    public void genC(PW pw) {
        if (mode.equals("print:")) {
            pw.printIdent("printf(\"");
            exprListArray = exprList.getArrayList();
            int contador = 0;
            
            //Percorre o vetor verificando o tipo das expressoes
            for(Expr s : exprListArray) {    
                
                if(s.getType().getCname().equals("int")) {
                    pw.print("%d");
                    contador++;
                    
                } else {
                    pw.print("%s");
                    contador++;
                }
            }

            pw.print("\", ");

            contador = 0;
            
            //Percorre o vetor verificando o nome das expressoes
            for(Expr s : exprListArray) {    
                s.genC(pw);
                contador++;
            
                if(exprListArray.size() != contador) {
                    pw.print(", ");
                }
            }
            pw.print(")");

        } else {
            pw.printIdent("printf(\"");
            exprListArray = exprList.getArrayList();
            int contador = 0;

            //Percorre o vetor verificando o tipo das expressoes
            for(Expr s : exprListArray) {    
                
                if(s.getType().getCname().equals("int")) {
                    pw.print("%d");
                    contador++;
                    
                } else {
                    pw.print("%s");
                    contador++;
                }
            }

            pw.print("\\n\", ");

            contador = 0;
            
            //Percorre o vetor verificando o nome das expressoes
            for(Expr s : exprListArray) {    
                s.genC(pw);
                contador++;
            
                if(exprListArray.size() != contador) {
                    pw.print(", ");
                }
            }
            pw.print(")");
        }
    }

    // Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if (mode.equals("print:")) {
            pw.printIdent("System.out.print(\"\" + ");
            exprListArray = exprList.getArrayList();
            int contador = 0;

            for(Expr s : exprListArray) {
                if((exprListArray.size()-1) != contador) {
                    s.genJava(pw);
                    pw.print(" + ");
                    contador++;

                } else {
                    s.genJava(pw);
                }
            }
            pw.print(")");
        
        } else {
            pw.printIdent("System.out.println(\"\" + ");
            exprListArray = exprList.getArrayList();
            int contador = 0;
        
            for(Expr s : exprListArray) {
                if((exprListArray.size()-1) != contador) {             
                    s.genJava(pw);
                    pw.print(" + ");
                    contador++;

                } else {
                    s.genJava(pw);
                }
            }
            pw.print(")");
        }
    }

    // Atributos da classe
    private ExpressionList exprList;
    private ArrayList<Expr> exprListArray;
    private String mode;

}