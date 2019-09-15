## Lab-Compiladores-Trabalho

Bacharelado em Ciência da Computação

Universidade Federal de São Carlos

Campus Sorocaba

Trabalho Prático da disciplina de Laboratório de Compiladores

----

## Descrição do Trabalho

#### Objetivos

Realização da Análise Léxica, Sintática, Semântica e Geração do Código na Linguagem C a partir da gramática do trabalho, em que se o compilador aceitar a entrada de uma sentença, é gerado o código em C, caso não, é gerado uma lista de erros.

#### Gramática

Annot ::= “@” Id [ “(” { AnnotParam } “)” ]\
AnnotParam ::= IntValue | StringValue | Id\
AssertStat ::= “assert” Expression “,” StringValue\
AssignExpr ::= Expression [ “=” Expression ]\
BasicType ::= “Int” | “Boolean” | “String”\
BasicValue ::= IntValue | BooleanValue | StringValue\
BooleanValue ::= “true” | “false”\
ClassDec ::= [ “open” ] “class” Id [ “extends” Id ] MemberList “end”\
CompStatement ::= “{” { Statement } “}”\
Digit ::= “0” | ... | “9”\
Expression ::= SimpleExpression [ Relation SimpleExpression ]\
ExpressionList ::= Expression { “,” Expression }\
Factor ::= BasicValue | “(” Expression “)” | “!” Factor | “nil” | ObjectCreation | PrimaryExpr\
FieldDec ::= “var” Type IdList [ “;” ]\
FormalParamDec ::= ParamDec { “,” ParamDec }\
HighOperator ::= “∗” | “/” | “&&”\
IdList ::= Id { “,” Id }\
IfStat ::= if” Expression “{” Statement “}” [ “else” “{” Statement “}” ]\
IntValue ::= Digit { Digit }\
LocalDec ::= “var” Type IdList [ “=” Expression ]\
LowOperator ::= +” | “−” | “||”\
MemberList ::= { [ Qualifier ] Member }\
Member ::= FieldDec | MethodDec\
MethodDec ::= “func” IdColon FormalParamDec [ “->” Type ] “{” StatementList “}” | “func” Id [ “->” Type ] “{” StatementList “}”\
ObjectCreation ::= Id “.” “new”\
ParamDec ::= Type Id\
Program ::= { Annot } ClassDec { { Annot } ClassDec }\
Qualifier ::= “private” “public” “override” “override” “public” “final” “final” “public” “final” “override” “final” “override” “public” “shared” “private” “shared” “public”\
ReadExpr ::= “In” “.” ( “readInt” | “readString” )\
RepeatStat ::= “repeat” StatementList “until” Expression\
PrimaryExpr ::= “super” “.” IdColon ExpressionList | “super” “.” Id |Id | Id “.” Id | Id “.” IdColon ExpressionList | “self” | “self” “.” Id | “self” ”.” IdColon ExpressionList | “self” ”.” Id “.” IdColon ExpressionList | “self” ”.” Id “.” Id | ReadExpr\
Relation ::= “==” | “<” | “>” | “<=” | “>=” | “! =”\
ReturnStat ::= “return” Expression\
Signal ::= “+” | “−”\
SignalFactor ::= [ Signal ] Factor\
SimpleExpression ::= SumSubExpression { “++” SumSubExpression }\
SumSubExpression ::= Term { LowOperator Term }\
Statement ::= AssignExpr “;” | IfStat | WhileStat | ReturnStat “;” | WriteStat “;” | “break” “;” | “;” | RepeatStat “;” | LocalDec “;” | AssertStat “;”\
StatementList ::= { Statement }\
Term ::= SignalFactor { HighOperator SignalFactor }\
Type ::= BasicType | Id\
WriteStat ::= “Out” “.” [ “print:” | “println:” ] Expression\
WhileStat ::= “while” Expression “{” StatementList “}”\

#### Execução (Comandos via terminal)

No diretorio [src], execute o seguinte comando no terminal:

- javac */ ** -encoding Cp1252 (Compilação do código fonte)

No diretorio [Lab-Compiladores-Trabalho], execute o seguinte comando no terminal:

- java -cp src/ comp.Comp code-generation-tests/OK_GERXX.ci (execução do código em cima de um determinado teste)

#### Verificação dos Testes

- OK_GER01.ci -> OK, sem GenJava
- OK_GER02.ci -> OK, sem GenJava
- OK_GER03.ci -> Ok, sem GenJava
- OK_GER04.ci -> Ok, sem GenJava
- OK_GER05.ci -> Ok, sem GenJava
- OK_GER06.ci -> Ok, sem GenJava
- OK_GER07.ci -> Ok, sem GenJava
- OK_GER08.ci -> Ok, sem GenJava
- OK_GER09.ci -> Ok, sem GenJava
- OK_GER10.ci -> Ok, sem GenJava
- OK_GER11.ci -> Ok, sem GenJava
- OK_GER12.ci -> Ok, sem GenJava
- OK_GER13.ci -> Ok, sem GenJava
- OK_GER14.ci -> Ok, sem GenJava
- OK_GER15.ci -> Ok, sem GenJava
- OK_GER16.ci -> Ok, sem GenJava
- OK_GER21.ci -> Ok, sem GenJava
- OK_GER22.ci -> Ok, sem GenJava

----

## Integrantes

- Nome: [Gustavo Buoro Branco de Souza](https://github.com/Gustavobbs/) RA: 726533

- Nome: [Leandro Naidhig](https://github.com/Leandro-Naidhig/) RA: 726555
