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

	//Metodo responsavel pela geracao do codigo em C
	public void genC(PW pw) {

		//Bibliotecas utilizadas na geracao do cedigo em C
		pw.println("#include<malloc.h>");
		pw.println("#include<stdlib.h>");
		pw.println("#include<stdio.h>");
		pw.println("#include<string.h>");
		pw.println("");

		//Metodo que define o tipo boolean
		pw.println("typedef int boolean;"); 
		pw.println("");	
		pw.println("#define true 1");
		pw.println("#define false 0");
		pw.println("");

		//Cria funcoes readInt e readString
		//ReatInt
		pw.println("int readInt() {");
		pw.add();
		pw.printlnIdent("int _n;");
		pw.printlnIdent("char __s[512];");
		pw.printlnIdent("gets(__s);");
		pw.printlnIdent("sscanf(__s, \"%d\", &_n);");
		pw.printlnIdent("return _n;");
		pw.sub();
		pw.println("}");
		pw.println("");

		//ReadString
		pw.println("char *readString() {");
		pw.add();
		pw.printlnIdent("char s[512];");
		pw.printlnIdent("gets(s);");
		pw.printlnIdent("char *ret = malloc(strlen(s) + 1);");
		pw.printlnIdent("strcpy(ret, s);");
		pw.printlnIdent("return ret;");
		pw.sub();
		pw.println("}");
		pw.println("");

		//Converte int para String
		pw.println("char *intToString(int _n) {");
		pw.add();
		pw.printlnIdent("char *ret = malloc(512);");
		pw.printlnIdent("sprintf(ret, \"%d\", _n);");
		pw.printlnIdent("return ret;");
		pw.sub();
		pw.println("}");
		pw.println("");


		//Concatena duas strings
		pw.println("char *concatStrings(char string1[], char string2[]) {");
		pw.add();
		pw.printlnIdent("char *ret = malloc(strlen(string1) + strlen(string2) + 1);");
		pw.printlnIdent("strcpy(ret,string1);");
		pw.printlnIdent("strcat(ret,string2);");
		pw.printlnIdent("return ret;");
		pw.sub();
		pw.println("}");
		pw.println("");

		//Define um tipo Func que e um ponteiro para funcao
		pw.println("typedef void (*Func)();");
		pw.println("");

		for(ClassDec s : classList) {
			s.genC(pw);
		}
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