package comp;
import java.util.*;

public class SymbolTable {

	//Construtor da classe
    public SymbolTable() {
		globalTable = new Hashtable<>();
		localTable = new Hashtable<>();
		localTableMethod = new Hashtable<>();
	}

	//Insercao de variaveis de forma global
	public Object putInGlobal(String key, Object value) {
		return globalTable.put(key, value);
	}

	//Insercao de variaveis de forma local(classe)
	public Object putInLocal(String key, Object value) {
		return localTable.put(key, value);
	}

	//Insercao de variaveis de forma local(metodo)
	public Object putInLocalMethod(String key, Object value) {
		return localTableMethod.put(key, value);
	}

	//Recupera o valor localmente(classe)
	public Object getInLocal(Object key) {
		return localTable.get(key);
	}

	//Recupera o valor localmente(metodo)
	public Object getInLocalMethod(Object key) {
		return localTable.get(key);
	}

	//Recupera o valor globalmente
	public Object getInGlobal(Object key) {
		return globalTable.get(key);
	}

	//Recupe o valor
	public Object get(String key) {
		Object result;
		if ((result = localTable.get(key)) != null || (result = localTableMethod.get(key)) != null) {
			return result;
		}
		else {
			return globalTable.get(key);
		}
	}

	//Remove os elementos da tabela local(da classe)
	public void removeLocalIdent() {
		localTable.clear();
	}

	//Remove os elementos da tabela local(do metodo)
	public void removeLocalIdentMethod() {
		localTableMethod.clear();
	}

	//Atributos da classe
	private Hashtable globalTable, localTable, localTableMethod;

}
