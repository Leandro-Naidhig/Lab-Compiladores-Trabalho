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

void _A_m1(_class_A *self) {
   printf("%d%s", 1, " ");
}
void _A_m2(_class_A *self, int _n) {
   printf("%d%s", _n, " ");
}

Func VTclass_A[] = {
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

typedef struct _St_B {
   Func *vt;
}_class_B;

_class_B *new_B(void);

void _B_m2(_class_B *self, int _n) {
   printf("%d%s", _n, " ");
   _A_m2((_class_A *) self, (_n + 1));
}

Func VTclass_B[] = {
   (void(*)())_A_m1,
   (void(*)())_B_m2
};

_class_B *new_B() {
   _class_B *t;
   
   if((t = malloc(sizeof(_class_B))) != NULL) {
      t->vt = VTclass_B;
   }
   
   return t;
}

typedef struct _St_C {
   Func *vt;
}_class_C;

_class_C *new_C(void);

void _C_m1(_class_C *self) {
   _A_m1((_class_A *) self);
   printf("%d%s", 2, " ");
}
void _C_m3(_class_C *self) {
   (self->vt[2])(self);
   printf("%d%s", 1, " ");
   printf("%d%s", 2, " ");
}

Func VTclass_C[] = {
   (void(*)())_B_m2,
   (void(*)())_C_m3,
   (void(*)())_C_m1
};

_class_C *new_C() {
   _class_C *t;
   
   if((t = malloc(sizeof(_class_C))) != NULL) {
      t->vt = VTclass_C;
   }
   
   return t;
}

typedef struct _St_Program {
   Func *vt;
}_class_Program;

_class_Program *new_Program(void);

void _Program_run(_class_Program *self) {
   _class_A *_a;
   _class_B *_b;
   _class_C *_c;
   printf("%s\n", "1 2 1 2 1 2 1 2");
   _b = new_B();
   ((void (*)(_class_B* , int))_b->vt[1])(_b, 1);
   _c = new_C();
   ((void (*)(_class_C* ))_c->vt[2])(_c);
   ((void (*)(_class_C* ))_c->vt[1])(_c);
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
