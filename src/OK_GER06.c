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

typedef void (*Func)();

typedef struct _St_A {
   Func *vt;
}_class_A;

_class_A *new_A(void);

void _A_m(_class_A *self) {
   int _i, _j, _k;
   printf("%d%s", 7, " ");
   _i = 1;
   _j = (_i + 1);
   _k = (_j + 1);
   printf("%d%s", _i, " ");
   printf("%d%s", _j, " ");
   printf("%d%s", _k, " ");
   _i = (((((((((3 + 1)) * 3)) / 2)) / 2)) + 1);
   printf("%d%s", _i, " ");
   _i = (((((100 - 95)) * 2)) - 5);
   printf("%d%s", _i, " ");
   _i = (((100 - ((45 * 2)))) - 4);
   printf("%d%s", _i, " ");
   printf("%d%s", 7, " ");
}

Func VTclass_A[] = {
   (void(*)())_A_m
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
   printf("%s\n", "7 1 2 3 4 5 6 7");
   _a = new_A();
   ((void (*)(_class_A* ))_a->vt[0])(_a);
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