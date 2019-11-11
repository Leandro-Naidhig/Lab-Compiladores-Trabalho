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
   int _A_n;
}_class_A;

_class_A *new_A(void);

void _A_set(_class_A *self, int _n) {
   (self->_A_n) = _n;
}
int _A_get(_class_A *self) {
   return (self->_A_n);
}

Func VTclass_A[] = {
   (void(*)())_A_get,
   (void(*)())_A_set
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
   struct _St_A *_Program_a;
}_class_Program;

_class_Program *new_Program(void);

void _Program_print(_class_Program *self) {
   printf("%d", ((int (*)(_class_Program *))self->_Program_a->vt[0])(self->_Program_a));
}
void _Program_set(_class_Program *self, _class_A *_a) {
   (self->_Program_a) = _a;
}
_class_A* _Program_get(_class_Program *self) {
   return (self->_Program_a);
}
void _Program_run(_class_Program *self) {
   printf("%s\n", "0");
   printf("%s\n", "0");
}

Func VTclass_Program[] = {
   (void(*)())_Program_run,
   (void(*)())_Program_get,
   (void(*)())_Program_print
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
