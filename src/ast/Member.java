/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Member {

    //Construtor da classe
    public Member(FieldDec field, MethodDec method) {
      this.field = field;
      this.method = method;
    }

    //Metodo para geracao do codigo em C
    public void genC(PW pw) {
      if(field != null) {
        field.genC(pw);
      } else {
        method.genC(pw);
      }
    }

    //Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
      if(field != null) {
        field.genJava(pw);
      } else {
        method.genJava(pw);
      }
    }

    //Atributos da classe
    private MethodDec method;
    private FieldDec field;
}