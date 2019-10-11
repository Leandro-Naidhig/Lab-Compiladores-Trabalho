/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

public class Variable extends Member{

    //Construtor da classe
	public Variable(String name, Type type) {
		if(name.indexOf(":") != -1) {
            int tamanho = name.length()-1;
            this.name = name.substring(0, tamanho);
        } else {
            this.name = name;
        }
		this.type = type;
	}

    //Metodo que seta o tipo da variavel
	public void setType(Type type) {
		this.type = type;
	}

	//Metodo que seta o nome da variavel
	public void setName(String name) {
		this.name = name;
	}

    //Metodo que retorna o nome da variavel
	public String getName() {
		return name;
	}

    //Metodo que retorna o tipo da variavel
	public Type getType() {
		return type;
	}

	//Metodo para geracao do codigo em Java
	public void genC(PW pw) {
		pw.print(name);
	}

	//Metodo para geracao do codigo em Java
	public void genJava(PW pw) {
		pw.print(name);
	}

    //Atributos da classe
	private String name;
	private Type type;
}