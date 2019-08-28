/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

abstract public class Type {

    //Construtor da classe
    public Type( String name ) {
        this.name = name;
    }

    //Objetos estaticos relacionados a cada tipo
    public static Type booleanType = new TypeBoolean();
    public static Type intType = new TypeInt();
    public static Type stringType = new TypeString();
    public static Type undefinedType = new TypeUndefined();
    public static Type nullType = new TypeNull();

    //Retorna o nome
    public String getName() {
        return name;
    }

    //Metodo abstrato para retornar o nome em C do tipo
    abstract public String getCname();

    //Atributos da classe
    private String name;
}
