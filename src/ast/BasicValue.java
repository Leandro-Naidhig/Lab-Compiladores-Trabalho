/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class BasicValue extends Factor{

    public BasicValue(String StringValue, Integer IntValue, Boolean BooleanValue) {
        this.StringValue = StringValue;
        this.IntValue = IntValue;
        this.BooleanValue = BooleanValue;
    }

    private String StringValue;
    private Integer IntValue;
    private Boolean BooleanValue;
}