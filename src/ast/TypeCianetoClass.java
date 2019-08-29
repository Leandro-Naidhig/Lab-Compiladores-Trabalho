/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */

package ast;

public class TypeCianetoClass extends Type {

   public TypeCianetoClass(String name) {
      super(name);
   }

   @Override
   public String getCname() {
      return getName();
   }

   

   private String name;
   private TypeCianetoClass superclass;
   // private FieldList fieldList;
   // private MethodList publicMethodList, privateMethodList;
   // m�todos p�blicos get e set para obter e iniciar as vari�veis acima,
   // entre outros m�todos
}
