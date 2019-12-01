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
   int _A_j;
   int _A_i;
}_class_A;

_class_A *new_A(void);

void _A_p(_class_A *self) {
   printf("%d%s", (self->_A_i), " ");
}
void _A_q(_class_A *self) {
   printf("%d%s", (self->_A_j), " ");
}
void _A_init_A(_class_A *self) {
   (self->_A_i) = 1;
   (self->_A_j) = 2;
}
void _A_call_p(_class_A *self) {
   _A_p(self);
}
void _A_call_q(_class_A *self) {
   _A_q(self);
}
void _A_r(_class_A *self) {
   printf("%d%s", (self->_A_i), " ");
}
void _A_s(_class_A *self) {
   printf("%d%s", (self->_A_j), " ");
}

Func VTclass_A[] = {
   (void(*)())_A_s,
   (void(*)())_A_r,
   (void(*)())_A_call_q,
   (void(*)())_A_call_p,
   (void(*)())_A_init_A
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
   int _A_j;
   int _A_i;
   int _B_j;
   int _B_i;
}_class_B;

_class_B *new_B(void);

void _B_p(_class_B *self) {
   printf("%d%s", (self->_B_i), " ");
}
void _B_q(_class_B *self) {
   printf("%d%s", (self->_B_j), " ");
}
void _B_init_B(_class_B *self) {
   (self->_B_i) = 3;
   (self->_B_j) = 4;
}
void _B_call_p(_class_B *self) {
   _B_p(self);
}
void _B_call_q(_class_B *self) {
   _B_q(self);
}
void _B_r(_class_B *self) {
   printf("%d%s", (self->_B_i), " ");
}
void _B_s(_class_B *self) {
   printf("%d%s", (self->_B_j), " ");
}

Func VTclass_B[] = {
   (void(*)())_B_s,
   (void(*)())_B_r,
   (void(*)())_B_call_q,
   (void(*)())_B_call_p,
   (void(*)())_A_init_A,
   (void(*)())_B_init_B
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
   int _A_j;
   int _A_i;
   int _C_j;
   int _C_i;
}_class_C;

_class_C *new_C(void);

void _C_p(_class_C *self) {
   printf("%d%s", (self->_C_i), " ");
}
void _C_q(_class_C *self) {
   printf("%d%s", (self->_C_j), " ");
}
void _C_init_C(_class_C *self) {
   (self->_C_i) = 5;
   (self->_C_j) = 6;
}
void _C_call_p(_class_C *self) {
   _C_p(self);
}
void _C_call_q(_class_C *self) {
   _C_q(self);
}
void _C_r(_class_C *self) {
   printf("%d%s", (self->_C_i), " ");
}
void _C_s(_class_C *self) {
   printf("%d%s", (self->_C_j), " ");
}

Func VTclass_C[] = {
   (void(*)())_C_s,
   (void(*)())_C_r,
   (void(*)())_C_call_q,
   (void(*)())_C_call_p,
   (void(*)())_A_init_A,
   (void(*)())_C_init_C
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
   printf("%s\n", "1 2 1 2 3 4 3 4 5 6 5 6");
   _a = new_A();
   ((void (*)(_class_A* ))_a->vt[4])(_a);
   ((void (*)(_class_A* ))_a->vt[3])(_a);
   ((void (*)(_class_A* ))_a->vt[2])(_a);
   ((void (*)(_class_A* ))_a->vt[1])(_a);
   ((void (*)(_class_A* ))_a->vt[0])(_a);
   _b = new_B();
   ((void (*)(_class_B* ))_b->vt[5])(_b);
   ((void (*)(_class_B* ))_b->vt[4])(_b);
   ((void (*)(_class_B* ))_b->vt[3])(_b);
   ((void (*)(_class_B* ))_b->vt[2])(_b);
   ((void (*)(_class_B* ))_b->vt[1])(_b);
   ((void (*)(_class_B* ))_b->vt[0])(_b);
   _c = new_C();
   ((void (*)(_class_C* ))_c->vt[5])(_c);
   ((void (*)(_class_C* ))_c->vt[4])(_c);
   ((void (*)(_class_C* ))_c->vt[5])(_c);
   ((void (*)(_class_C* ))_c->vt[3])(_c);
   ((void (*)(_class_C* ))_c->vt[2])(_c);
   ((void (*)(_class_C* ))_c->vt[1])(_c);
   ((void (*)(_class_C* ))_c->vt[0])(_c);
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
