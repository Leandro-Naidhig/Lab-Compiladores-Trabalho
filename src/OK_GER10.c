#include<malloc.h>
#include<stdlib.h>
#include<stdio.h>
#include<string.h>

typedef int boolean;

#define true 1
#define false 0

int readInt() {
   int n;
   char __s[512];
   gets(__s);
   sscanf(__s, %d, &_n);
   return n;
}

char *readString() {
   char s[512];
   gets(s);
   char *ret = malloc(strlen(s) + 1);
   strcpy(ret, s);
   return ret;
}

typedef void (*Func)();

typedef struct  _St_A {
   Func *vt;
   int _A_k;
} _class_A;

_class_A *new_A(void);

void _A_m1(_class_A *self, int n) {
   self->k = ;
   printf("%d",(((   self->k + ) + ) + ));
}

Func VTclass_A[] = {
   ( void(*)() ) _A_m1
};

_class_A *new_A() {
   _class_A *t;
   
   if((t == malloc(sizeof(_class_A))) != null) {
      t->vt = VTclass_A;
   }
   
   return t;
}

typedef struct  _St_B {
   Func *vt;
   int _B_k;
} _class_B;

_class_B *new_B(void);

void _B_m2(_class_B *self, int n) {
   self->k = ;
;
   printf("%d",(((   self->k + ) + ) + ));
}

Func VTclass_B[] = {
   ( void(*)() ) _B_m2
};

_class_B *new_B() {
   _class_B *t;
   
   if((t == malloc(sizeof(_class_B))) != null) {
      t->vt = VTclass_B;
   }
   
   return t;
}

typedef struct  _St_C {
   Func *vt;
} _class_C;

_class_C *new_C(void);

void _C_m3(_class_C *self, int n) {
;
   printf("%d",(( + ) + ));
}
void _C_m4(_class_C *self, int n) {
   self->m3(, );
   printf("%d, %s",( + ));
}

Func VTclass_C[] = {
   ( void(*)() ) _C_m3,
   ( void(*)() ) _C_m4
};

_class_C *new_C() {
   _class_C *t;
   
   if((t == malloc(sizeof(_class_C))) != null) {
      t->vt = VTclass_C;
   }
   
   return t;
}

typedef struct  _St_Program {
   Func *vt;
} _class_Program;

_class_Program *new_Program(void);

void _Program__run(_class_Program *self) {
   _class_C *_c;
   printf("" + "1 1 2 2 3 3 4 4");
 = ;
;
}

Func VTclass_Program[] = {
   ( void(*)() ) _Program_run
};

_class_Program *new_Program() {
   _class_Program *t;
   
   if((t == malloc(sizeof(_class_Program))) != null) {
      t->vt = VTclass_Program;
   }
   
   return t;
}


int main() {
   _class_Program *program;
   program = new_Program();
   ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
   return 0;
}
