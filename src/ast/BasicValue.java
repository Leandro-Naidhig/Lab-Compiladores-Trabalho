/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class BasicValue extends Factor{

    public BasicValue(LiteralString StringValue, LiteralInt IntValue, LiteralBoolean BooleanValue) {
        this.StringValue = StringValue;
        this.IntValue = IntValue;
        this.BooleanValue = BooleanValue;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
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

    private LiteralString StringValue;
    private LiteralInt IntValue;
    private LiteralBoolean BooleanValue;
}