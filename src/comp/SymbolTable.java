package comp;
import java.util.*;

public class SymbolTable {

	//Construtor da classe
    public SymbolTable() {
		globalTable = new Hashtable<>();
		localTableMethodFieldClass = new Hashtable<>();
		localTableMethodVariablesClass = new Hashtable<>();
		localTableMethodParents = new Hashtable<>();
	}

	/*METODOS PUT*/

	//Insercao de variaveis de forma global
	public Object putInGlobal(String key, Object value) {
		return globalTable.put(key, value);
	}

	//Insercao de variaveis de forma local(metodo)
	public Object putInLocalMethodFieldClass(String key, Object value) {
		return localTableMethodFieldClass.put(key, value);
	}

	//Insercao de variaveis de forma local(classe)
	public Object putInLocalMethodVariablesClass(String key, Object value) {
		return localTableMethodVariablesClass.put(key, value);
	}

	//Insercao de variaveis de forma local(metodo)
	public Object putInLocalMethodParents(String key, Object value) {
		return localTableMethodParents.put(key, value);
	}

	/*METODOS GET*/

	//Recupera o valor globalmente
	public Object getInGlobal(Object key) {
		return globalTable.get(key);
	}

	//Recupera o valor localmente(metodo ou instancias da classe)
	public Object getInLocalMethodFieldClass(Object key) {
		return localTableMethodVariablesClass.get(key);
	}

	//Recupera o valor localmente(metodo)
	public Object getInLocalMethodVariablesClass(Object key) {
		return localTableMethodFieldClass.get(key);
	}

	//Recupera o valor localmente(parentes)
	public Object getInLocalMethodParents(Object key) {
		return localTableMethodParents.get(key);
	}

	//Recupe o valor
	public Object get(String key) {
		Object result;
		if ((result = localTableMethodFieldClass.get(key)) != null 
		 || (result = localTableMethodVariablesClass.get(key)) != null
		 || (result = localTableMethodParents.get(key)) != null) {
			return result;
		}
		else {
			return globalTable.get(key);
		}
	}

	/*METODOS DE REMOCAO*/

	//Remove os elementos da tabela local(da classe)
	public void removeLocalIdentMethodFieldClass() {
		localTableMethodFieldClass.clear();
	}

	//Remove os elementos da tabela local(do metodo)
	public void removeLocalIdentMethodVariablesClass() {
		localTableMethodVariablesClass.clear();
	}

	//Remove os elementos da tabela local(do metodo)
	public void removeLocalIdentMethodParents() {
		localTableMethodParents.clear();
	}

	//Atributos da classe
	private Hashtable globalTable, localTableMethodFieldClass, localTableMethodVariablesClass, localTableMethodParents;

}
