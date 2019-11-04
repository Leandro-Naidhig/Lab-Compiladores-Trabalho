/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;

import java.util.ArrayList;

/** This class represents a metaobject annotation as <code>{@literal @}ce(...)</code> in <br>
 * <code>
 * @ce(5, "'class' expected") <br>
 * clas Program <br>
 *     public void run() { } <br>
 * end <br>
 * </code>
 *
   @author Jos�

 */
public class MetaobjectAnnotation {

	//Construtor da classe
	public MetaobjectAnnotation(String name, ArrayList<Object> paramList) {
		this.name = name;
		this.paramList = paramList;
	}

	//Retorna uma lista de parametros
	public ArrayList<Object> getParamList() {
		return paramList;
	}

	//Retorna seu respectivo nome
	public String getName() {
		return name;
	}

	//Metodo para geracao do codigo em C
    public void genC(PW pw) {
    }

	//Metodo para geracao do codigo em Java
    public void genJava(PW pw) {
    }

	//Atributos da classe
	private String name;
	private ArrayList<Object> paramList;
}
