/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class BasicValue extends Expr{

    public BasicValue(LiteralString StringValue, LiteralInt IntValue, LiteralBoolean BooleanValue) {
        this.StringValue = StringValue;
        this.IntValue = IntValue;
        this.BooleanValue = BooleanValue;
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

    //Metodo para retornar o tipo
    public Type getType() {
        if(StringValue != null) {
            return Type.stringType;
        } else if(BooleanValue != null) {
            return Type.booleanType;
        } else {
            return Type.booleanType;
        }
    }

    private LiteralString StringValue;
    private LiteralInt IntValue;
    private LiteralBoolean BooleanValue;
}