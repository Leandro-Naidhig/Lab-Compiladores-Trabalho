import java.util.*;
public class OK_GER15 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int i;
   private int j;
   public void p() {
      System.out.print("" + this.i + " ");
   }
   public void q() {
      System.out.print("" + this.j + " ");
   }
   public void init_A() {
      this.i = 1;
      this.j = 2;
   }
   public void call_p() {
      this.p();
   }
   public void call_q() {
      this.q();
   }
   public void r() {
      System.out.print("" + this.i + " ");
   }
   public void s() {
      System.out.print("" + this.j + " ");
   }
}
class B extends A {
   private int i;
   private int j;
   public void p() {
      System.out.print("" + this.i + " ");
   }
   public void q() {
      System.out.print("" + this.j + " ");
   }
   public void init_B() {
      this.i = 3;
      this.j = 4;
   }
   public void call_p() {
      this.p();
   }
   public void call_q() {
      this.q();
   }
   public void r() {
      System.out.print("" + this.i + " ");
   }
   public void s() {
      System.out.print("" + this.j + " ");
   }
}
class C extends A {
   private int i;
   private int j;
   public void p() {
      System.out.print("" + this.i + " ");
   }
   public void q() {
      System.out.print("" + this.j + " ");
   }
   public void init_C() {
      this.i = 5;
      this.j = 6;
   }
   public void call_p() {
      this.p();
   }
   public void call_q() {
      this.q();
   }
   public void r() {
      System.out.print("" + this.i + " ");
   }
   public void s() {
      System.out.print("" + this.j + " ");
   }
}
class Program {
   public void run() {
      A a;
      B b;
      C c;
      System.out.println("" + "1 2 1 2 3 4 3 4 5 6 5 6");
      a = new A();
      a.init_A();
      a.call_p();
      a.call_q();
      a.r();
      a.s();
      b = new B();
      b.init_B();
      b.init_A();
      b.call_p();
      b.call_q();
      b.r();
      b.s();
      c = new C();
      c.init_C();
      c.init_A();
      c.init_C();
      c.call_p();
      c.call_q();
      c.r();
      c.s();
   }
}
