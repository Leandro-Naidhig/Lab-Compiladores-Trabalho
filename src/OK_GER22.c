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

typedef struct _St_Program {
   Func *vt;
}_class_Program;

_class_Program *new_Program(void);

void _Program_run(_class_Program *self) {
   printf("%s\n", "100");
   int _i, _j, _n;
   _i = 0;
   _j = 0;
   _n = 10;
   boolean _b;
   _b = 0;
   do {
      _n = (_n + 1);
   } while((0));
;
   _n = 10;
   int _sum;
   _sum = 0;
   do {
      _i = 0;
      do {
         _i = (_i + 1);
         _sum = (_sum + 1);
      } while((_i < _n));
      _j = (_j + 1);
   } while((_j < _n));
   printf("%d\n", _sum);
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
