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
}_class_A;

_class_A *new_A(void);

void _A__m(_class_A *self) {
   printf("%d, %s", 7, " ");
   if(((1 > 0))) {
      printf("%d, %s", 0, " ");
   }
   if(((1 >= 0))) {
      printf("%d, %s", 1, " ");
   }
   if(((1 != 0))) {
      printf("%d, %s", 2, " ");
   }
   if(((0 < 1))) {
      printf("%d, %s", 3, " ");
   }
   if(((0 <= 1))) {
      printf("%d, %s", 4, " ");
   }
   if(((0 == 0))) {
      printf("%d, %s", 5, " ");
   }
   if(((0 >= 0))) {
      printf("%d, %s", 6, " ");
   }
   if(((0 <= 0))) {
      printf("%d, %s", 7, " ");
   }
   if(((1 == 0))) {
      printf("%d, %s", 18, " ");
   }
   if(((0 > 1))) {
      printf("%d, %s", 10, " ");
   }
   if(((0 >= 1))) {
      printf("%d, %s", 11, " ");
   }
   if(((0 != 0))) {
      printf("%d, %s", 12, " ");
   }
   if(((1 < 0))) {
      printf("%d, %s", 13, " ");
   }
   if(((1 <= 0))) {
      printf("%d, %s", 14, " ");
   }
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

typedef struct  _St_Program {
   Func *vt;
}_class_Program;

_class_Program *new_Program(void);

void _Program__run(_class_Program *self) {
   _class_A *_a;
   printf("%s\n", "7 0 1 2 3 4 5 6 7");
   _a = new_ A();
   ((void (*)(_class_A* ))__a->vt[1]  )(__a);
;
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
