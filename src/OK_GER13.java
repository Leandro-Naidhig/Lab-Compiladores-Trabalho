import java.util.*;
public class OK_GER13 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int n;
   public void p1() {
      System.out.print("" + "999 ");
   }
   public void set(int pn) {
      System.out.print("" + 1 + " ");
      this.n = pn;
   }
   public void p2() {
      System.out.print("" + "888 ");
   }
   public int get() {
      return this.n;
   }
   public void print() {
      System.out.print("" + "A ");
   }
}
class B extends A {
   public void p2() {
   }
   public void set(int pn) {
      System.out.print("" + pn + " ");
      super.set(pn);
   }
   public void p1() {
      System.out.print("" + 2 + " ");
   }
   public void print() {
      System.out.print("" + "B ");
   }
}
class Program {
   private Program program;
   public void print() {
      System.out.print("" + "P ");
   }
   public B m(A a) {
      a.set(0);
      return new B();
   }
   public A p(int i) {
      if((i > 0)) {
         return new A();
      } else { 
         return new B();
      }
   }
   public void run() {
      A a, a2;
      B b;
      System.out.println("" + "0 1 0 1 0 1 2 B A 0 1 P");
      a = new A();
      b = new B();
      a = b;
      a.set(0);
      a = this.m(a);
;
      b = this.m(b);
;
   }
}
