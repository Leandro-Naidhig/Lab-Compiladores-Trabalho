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

    private LiteralString StringValue;
    private LiteralInt IntValue;
    private LiteralBoolean BooleanValue;
}