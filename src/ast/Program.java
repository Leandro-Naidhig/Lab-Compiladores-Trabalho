/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533

 */
package ast;
import java.util.*;
import comp.CompilationError;

public class Program {

	//Construtor da classe
	public Program(ArrayList<TypeCianetoClass> classList, ArrayList<MetaobjectAnnotation> metaobjectCallList, 
			       ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}

	//Metodo responsavel pela geracao do codigo em 	Java
	public void genJava(PW pw) {
		pw.print("package comp;");
		pw.print("import java.util.Scanner");	
		
		for(TypeCianetoClass s : classList) {

			if(s.getName().equals("Program")) {
				pw.print("public class Program {");
				pw.add();
				pw.print("public static void main(String []args) {");
				pw.add();
				pw.sub();
				pw.print("}");
			
			} else {
				pw.print("public class ");
				s.getName();
				pw.print("} ");


			}


			
		  }

		  s.genC(pw);

		


			
				new Comp().run(args);
			}
	}

	//Metodo responsavel pela geracao do codigo em C
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

	//Atributos da classe
	private ArrayList<TypeCianetoClass> classList;
	private ArrayList<MetaobjectAnnotation> metaobjectCallList;	
	ArrayList<CompilationError> compilationErrorList;
}