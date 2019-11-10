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
   int _A_k;
}_class_A;

_class_A *new_A(void);

int _A_get_A(_class_A *self) {
   return (self->_A_k;
}
void _A_set(_class_A *self, int _k) {
   (self->_A_k = _k;
}
void _A_print(_class_A *self) {
   printf("%d%s", (self->vt[3])(self), " ");
}
void _A_init(_class_A *self) {
   (self->vt[2])(self, 0);
}

Func VTclass_A[] = {
   (void(*)())_A_init,
   (void(*)())_A_print,
   (void(*)())_A_set,
   (void(*)())_A_get_A
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
   int _B_k;
}_class_B;

_class_B *new_B(void);

int _B_get_B(_class_B *self) {
   return (self->_B_k;
}
void _B_init(_class_B *self) {
   _A_init((_class_A *) self);
   (self->_B_k = 2;
}
void _B_print(_class_B *self) {
   printf("%d%s", (self->vt[4])(self), " ");
   printf("%d%s", (self->vt[1])(self), " ");
   _A_print((_class_A *) self);
}

Func VTclass_B[] = {
   (void(*)())_A_set,
   (void(*)())_A_get_A,
   (void(*)())_B_print,
   (void(*)())_B_init,
   (void(*)())_B_get_B
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

int _C_get_A(_class_C *self) {
   return 0;
}

Func VTclass_C[] = {
   (void(*)())_C_get_A
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
   printf("%s\n", "2 2 0 0 2 0 0 0 0 0 0");
   _b = new_B();
   ((void (*)(_class_B* ))_b->vt[abs((sizeof(VTclass_B)/sizeof(VTclass_B[0])) - 4 - 1)])(_b);
   _c = new_C();
   ((void (*)(_class_C* ))_c->vt[abs((sizeof(VTclass_C)/sizeof(VTclass_C[0])) - 1 - 1)])(_c);
   printf("%d%s", ((void (*)(_class_B* ))_b->vt[abs((sizeof(VTclass_B)/sizeof(VTclass_B[0])) - 4 - 0)])(_b), " ");
   _a = _b;
   ((void (*)(_class_A* ))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 5 - 1)])(_a);
   ((void (*)(_class_B* ))_b->vt[abs((sizeof(VTclass_B)/sizeof(VTclass_B[0])) - 4 - 0)])(_b);
   ((void (*)(_class_A* ))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 5 - 3)])(_a);
   ((void (*)(_class_B* ))_b->vt[abs((sizeof(VTclass_B)/sizeof(VTclass_B[0])) - 4 - 1)])(_b);
   printf("%d%s", ((void (*)(_class_A* ))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 5 - 3)])(_a), " ");
   printf("%d%s", ((void (*)(_class_B* ))_b->vt[abs((sizeof(VTclass_B)/sizeof(VTclass_B[0])) - 4 - 3)])(_b), " ");
   _a = _c;
   printf("%d%s", ((void (*)(_class_A* ))_a->vt[abs((sizeof(VTclass_A)/sizeof(VTclass_A[0])) - 5 - 0)])(_a), " ");
   _c = new_C();
   printf("%d%s", ((void (*)(_class_C* ))_c->vt[abs((sizeof(VTclass_C)/sizeof(VTclass_C[0])) - 1 - 0)])(_c), " ");
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
