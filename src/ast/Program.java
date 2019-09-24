/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.*;
import comp.CompilationError;

public class Program {

	//Construtor da classe
	public Program(ArrayList<ClassDec> classList, ArrayList<MetaobjectAnnotation> metaobjectCallList, 
			       ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}

	//Metodo responsavel pela geracao do codigo em 	Java
	public void genJava(PW pw) {

		//Bibliotecas do Java
		//pw.println("package comp;");
		pw.println("import java.util.*;");

		pw.println("public class " + mainJavaClassName + " {");
		pw.add();
		pw.printlnIdent("public static void main(String []args) {");
		pw.add();
		pw.printlnIdent("new Program().run();");
		pw.sub();
		pw.printlnIdent("}");
		pw.sub();
		pw.printlnIdent("}");

		pw.println("class Scan {");
		pw.add();
		pw.printlnIdent("public static Scanner imputValue = new Scanner(System.in);");
		pw.sub();
		pw.printlnIdent("}");

		for(ClassDec s : classList) {
			pw.print("class ");	
			s.genJava(pw);
		}
	}

	//Metodo responsavel pela geracao do codigo em C
	public void genC(PW pw) {

		//Bibliotecas utilizadas na geração do código em C
		pw.println("#include<malloc.h>");
		pw.println("#include<stdlib.h>");
		pw.println("#include<stdio.h>");	
	}

	//Recupera o nome do teste que será executado o Program
	public void setMainJavaClassName(String mainJavaClassName) {
		this.mainJavaClassName = mainJavaClassName;
	}
	
	//Compõe uma lista da classe TypeCianetoClass (ClassDec na gramatica)
	public ArrayList<ClassDec> getClassList() {
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

	//Atributos da classe
	private ArrayList<ClassDec> classList;
	private ArrayList<MetaobjectAnnotation> metaobjectCallList;	
	ArrayList<CompilationError> compilationErrorList;
	private String mainJavaClassName;
}