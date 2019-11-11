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

void _A_p1(_class_A *self) {
   printf("%s", "999 ");
}
void _A_set(_class_A *self, int _pn) {
   printf("%d%s", 1, " ");
   (self->_A_n) = _pn;
}
void _A_p2(_class_A *self) {
   printf("%s", "888 ");
}
int _A_get(_class_A *self) {
   return (self->_A_n);
}
void _A_print(_class_A *self) {
   printf("%s", "A ");
}

Func VTclass_A[] = {
   (void(*)())_A_print,
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

typedef struct _St_B {
   Func *vt;
   int _A_n;
}_class_B;

_class_B *new_B(void);

void _B_p2(_class_B *self) {
}
void _B_set(_class_B *self, int _pn) {
   printf("%d%s", _pn, " ");
   _A_set((_class_A *) self, _pn);
}
void _B_p1(_class_B *self) {
   printf("%d%s", 2, " ");
}
void _B_print(_class_B *self) {
   printf("%s", "B ");
}

Func VTclass_B[] = {
   (void(*)())_B_print,
   (void(*)())_B_p1,
   (void(*)())_B_set
};

_class_B *new_B() {
   _class_B *t;
   
   if((t = malloc(sizeof(_class_B))) != NULL) {
      t->vt = VTclass_B;
   }
   
   return t;
}

typedef struct _St_Program {
   Func *vt;
   struct _St_Program *_Program_program;
}_class_Program;

_class_Program *new_Program(void);

void _Program_print(_class_Program *self) {
   printf("%s", "P ");
}
_class_B* _Program_m(_class_Program *self, _class_A *_a) {
   ((void (*)(_class_A* , int))_a->vt[2])(_a, 0);
   return new_B();
}
_class_A* _Program_p(_class_Program *self, int _i) {
   if((_i > 0)) {
      return new_A();
   } else { 
      return new_B();
   }
}
void _Program_run(_class_Program *self) {
   _class_A *_a, *_a2;
   _class_B *_b;
   printf("%s\n", "0 1 0 1 0 1 2 B A 0 1 P");
   _a = new_A();
   _b = new_B();
   _a = _b;
   ((void (*)(_class_A* , int))_a->vt[2])(_a, 0);
   _a = (self->vt[2])(self, _a);
   _b = (self->vt[2])(self, _b);
   ((void (*)(_class_B* ))_b->vt[1])(_b);
   _a = (self->vt[1])(self, 0);
   ((void (*)(_class_A* ))_a->vt[0])(_a);
}

Func VTclass_Program[] = {
   (void(*)())_Program_run,
   (void(*)())_Program_p,
   (void(*)())_Program_m,
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
   ((void (*)(_class_Program*)) program->vt[3])(program);
   return 0;
}
