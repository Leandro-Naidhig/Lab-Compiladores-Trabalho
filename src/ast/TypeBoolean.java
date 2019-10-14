/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class TypeBoolean extends Type {

   //Construtor da clase
   public TypeBoolean() { super("boolean"); }

   //Retornar o nome do tipo em C
   @Override
   public String getCname() {
      return "boolean";
   }

}
