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
   int _A_n;
}_class_A;

_class_A *new_A(void);

void _A_set(_class_A *self, int _n) {
      self->n = _n;
}
int _A_get(_class_A *self) {
   return    self->n;
}

Func VTclass_A[] = {
   (void(*)())_A_set,
   (void(*)())_A_get
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
   A _Program_a;
}_class_Program;

_class_Program *new_Program(void);

void _Program_print(_class_Program *self) {
   printf("%s",    self->a);
}
void _Program_set(_class_Program *self, _class_A *a) {
      self->a = _a;
}
A _Program_get(_class_Program *self) {
   return    self->a;
}
void _Program_run(_class_Program *self) {
   printf("%s\n", "0");
   printf("%s\n", "0");
}

Func VTclass_Program[] = {
   (void(*)())_Program_print,
   (void(*)())_Program_set,
   (void(*)())_Program_get,
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
