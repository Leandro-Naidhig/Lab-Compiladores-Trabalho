package ast;
import java.util.*;
import comp.CompilationError;

public class Program {

	public Program(ArrayList<TypeCianetoClass> classList, ArrayList<MetaobjectAnnotation> metaobjectCallList, 
			       ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}


	public void genJava(PW pw) {
	}

	public void genC(PW pw) {

		//Bibliotecas utilizadas na geração do código em C
		pw.println("#include<malloc.h>");
		pw.println("#include<stdlib.h>");
		pw.println("#include<stdio.h>");
		
	}
	
	//Compõe uma lista da classe TypeCianetoClass (ClassDec na gramatica)
	public ArrayList<TypeCianetoClass> getClassList() {
		return classList;
	}

	//Compõe uma lista da classe MetaobjectAnnotation (Annot na gramatica) 
	public ArrayList<MetaobjectAnnotation> getMetaobjectCallList() {
		return metaobjectCallList;
	}
	
	//É responsável por retornar verdadeiro caso houver algum erro na lista de erros
	public boolean hasCompilationErrors() {
		return compilationErrorList != null && compilationErrorList.size() > 0 ;
	}

	//Compõe uma lista de erros, em que é retornada apenas se conter algum erro
	public ArrayList<CompilationError> getCompilationErrorList() {
		return compilationErrorList;
	}

	//Atributos
	private ArrayList<TypeCianetoClass> classList;
	private ArrayList<MetaobjectAnnotation> metaobjectCallList;	
	ArrayList<CompilationError> compilationErrorList;
}