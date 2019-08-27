/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Variable {

    //Construtor da classe
	public Variable(String name, Type type) {
		this.name = name;
		this.type = type;
	}

    //Construtor da classe
	public Variable(String name) {
		this.name = name;
	}

    //Seta o tipo da variavel
	public void setType(Type type) {
		this.type = type;
	}

    //Retorna o nome da variavel
	public String getName() {
		return name;
	}

    //Retorna o tipo da variavel
	public Type getType() {
		return type;
	}

    //Atributos da classe
	private String name;
	private Type type;
}