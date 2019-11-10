/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.ArrayList;

public class BasicValue extends Expr{

    //Construtor da classe
    public BasicValue(LiteralString StringValue, LiteralInt IntValue, LiteralBoolean BooleanValue) {
        this.StringValue = StringValue;
        this.IntValue = IntValue;
        this.BooleanValue = BooleanValue;
    }

    //Metodo para recuperar o valor caso for um inteiro
    public LiteralInt getValue() {
        
        if(IntValue != null) {
            return IntValue;
        } else {
            return null;
        }
    }

    //Metodo para recuperar a string caso for uma string
    public LiteralString getString() {
        
        if(StringValue != null) {
            return StringValue;
        } else {
            return null;
        }
    }

    public void genC(PW pw, ArrayList<Member> membros){
        if(StringValue != null) {
            StringValue.genC(pw);
        } else if(BooleanValue != null) {
            BooleanValue.genC(pw);
        } else {
            IntValue.genC(pw);
        }
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw, boolean op) {
        
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
        if(StringValue != null) {
            StringValue.genJava(pw);
        } else if(BooleanValue != null) {
            BooleanValue.genJava(pw);
        } else {
            IntValue.genJava(pw);
        }
    }

    //Metodo para retornar o tipo do valor
    public Type getType() {
        if(StringValue != null) {
            return Type.stringType;
        } else if(BooleanValue != null) {
            return Type.booleanType;
        } else {
            return Type.intType;
        }
    }

    //Atributos da classe
    private LiteralString StringValue;
    private LiteralInt IntValue;
    private LiteralBoolean BooleanValue;
}