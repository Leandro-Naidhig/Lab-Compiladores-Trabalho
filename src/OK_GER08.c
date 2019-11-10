#include<malloc.h>
#include<stdlib.h>
#include<stdio.h>
#include<string.h>

typedef int boolean;

#define true 1
#define false 0

int readInt() {
   int _n;
   char __s[512];
   gets(__s);
   sscanf(__s, "%d", &_n);
   return _n;
}

char *readString() {
   char s[512];
   gets(s);
   char *ret = malloc(strlen(s) + 1);
   strcpy(ret, s);
   return ret;
}

char *intToString(int _n) {
   char *ret = malloc(512);
   sprintf(ret, "%d", _n);
   return ret;
}

char *concatStrings(char string1[], char string2[]) {
   char *ret = malloc(strlen(string1) + strlen(string2) + 1);
   strcpy(ret,string1);
   strcat(ret,string2);
   return ret;
}

typedef void (*Func)();

typedef struct _St_A {
   Func *vt;
}_class_A;

_class_A *new_A(void);

void _A_m1(_class_A *self, int _n) {
   printf("%d%s", 1, " ");
   printf("%d%s", _n, " ");
}
void _A_m2(_class_A *self, int _n) {
   printf("%d%s", 2, " ");
   printf("%d%s", _n, " ");
}
void _A_m3(_class_A *self, int _n, int _p, char * _q[], int _r, int _falseBool) {
   printf("%d%s", 3, " ");
   printf("%d%s", _n, " ");
   printf("%d%s", _p, " ");
   printf("%s%s", _q, " ");
   printf("%d%s", _r, " ");
   if(_falseBool) {
      printf("%s", (concatStrings(intToString(8), " ")));
   } else { 
      printf("%s", (concatStrings(intToString(7), " ")));
   }
}

Func VTclass_A[] = {
   (void(*)())_A_m3,
   (void(*)())_A_m2,
   (void(*)())_A_m1
};

_class_A *new_A() {
   _class_A *t;
   
   if((t = malloc(sizeof(_class_A))) != NULL) {
      t->vt = VTclass_A;
   }
   
   return t;
}

typedef struct _St_Program {
   Func *vt;
}_class_Program;

_class_Program *new_Program(void);

void _Program_run(_class_Program *self) {
   _class_A *_a;
   printf("%s\n", "1 1 2 2 3 3 4 5 6 7");
   _a = new_A();
   ((void (*)(_class_A* , int))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 3 - 2)])(_a, 1);
   ((void (*)(_class_A* , int))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 3 - 1)])(_a, 2);
   ((void (*)(_class_A* , int, int, char *, int, int))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 3 - 0)])(_a, 3, 4, "5", 6, 0);
}

Func VTclass_Program[] = {
   (void(*)())_Program_run
};

_class_Program *new_Program() {
   _class_Program *t;
   
   if((t = malloc(sizeof(_class_Program))) != NULL) {
      t->vt = VTclass_Program;
   }
   
   return t;
}

int main() {
   _class_Program *program;
   program = new_Program();
   ((void (*)(_class_Program*)) program->vt[0])(program);
   return 0;
}
