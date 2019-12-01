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

int _A_get_A(_class_A *self);
void _A_init(_class_A *self); 

int _A_get_A(_class_A *self) {
   // printf("%d\n", self->_A_k);
   return (int)(self->_A_k);
}
void _A_init(_class_A *self) {
   (self->_A_k) = 1;
}

Func VTclass_A[] = {
   (void(*)())_A_init,
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
   int _A_k;
   int _B_k;
}_class_B;

_class_B *new_B(void);

int _B_get_B(_class_B *self);
void _B_init(_class_B *self);

int _B_get_B(_class_B *self) {
    printf("%d\n", self->_B_k);
    return (int)(self->_B_k);
}
void _B_init(_class_B *self) {
   _A_init((_class_A *) self);
   (self->_B_k) = 2;
}

Func VTclass_B[] = {
   (void(*)(_class_A ))_A_get_A,
   (void(*)(_class_B ))_B_init,
   (void(*)(_class_B ))_B_get_B
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
   int _A_k;
   int _B_k;
   int _C_k;
}_class_C;

_class_C *new_C(void);
int _C_get_C(_class_C *self);
void _C_init(_class_C *self);

int _C_get_C(_class_C *self) {
    //printf("%d\n", self->_C_k);
   return (int)(self->_C_k);
}
void _C_init(_class_C *self) {
   _B_init((_class_B *) self);
   (self->_C_k) = 3;
}

Func VTclass_C[] = {
   (void(*)(_class_A ))_A_get_A,
   (void(*)(_class_B ))_B_get_B,
   (void(*)(_class_C ))_C_init,
   (void(*)(_class_C ))_C_get_C
};

_class_C *new_C() {
   _class_C *t;
   
   if((t = malloc(sizeof(_class_C))) != NULL) {
      t->vt = VTclass_C;
   }
   
   return t;
}

typedef struct _St_D {
   Func *vt;
   int _A_k;
   int _B_k;
   int _C_k;
   int _D_k;
}_class_D;

_class_D *new_D(void);

int _D_get_D(_class_D *self) {
    //printf("%d\n", self->_D_k);
   return (int)(self->_D_k);
}
void _D_init(_class_D *self) {
   _C_init((_class_C *) self);
   (self->_D_k) = 4;
}

Func VTclass_D[] = {
   (void(*)(_class_A ))_A_get_A,
   (void(*)(_class_B ))_B_get_B,
   (void(*)(_class_C ))_C_get_C,
   (void(*)(_class_D ))_D_init,
   (void(*)(_class_D ))_D_get_D
};

_class_D *new_D() {
   _class_D *t;
   
   if((t = malloc(sizeof(_class_D))) != NULL) {
      t->vt = VTclass_D;
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
   _class_D *_d;
   printf("%s\n", "4 3 2 1");
   _d = new_D();
   ((void (*)(_class_D* ))_d->vt[3])(_d);
   printf("%d%s", ((int (*)(_class_D* ))_d->vt[4])(_d), " ");
   _c =  (_class_C* ) _d;
   printf("%d%s", ((int (*)(_class_C* ))_c->vt[3])(_c), " ");
   _b =  (_class_B* ) _c;
   printf("%d%s", ((int (*)(_class_B* ))_b->vt[2])(_b), " ");
   _a = (_class_A* ) _b;
   printf("%d%s", ((int (*)(_class_A* ))_a->vt[0])(_a), " ");
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
