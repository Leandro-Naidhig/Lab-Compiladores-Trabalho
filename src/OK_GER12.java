import java.util.*;
public class OK_GER12 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m1() {
      System.out.print("" + 1 + " ");
   }
   public void m2(int n) {
      System.out.print("" + n + " ");
   }
}
class B extends A {
   @Override
   public void m2(int n) {
      System.out.print("" + n + " ");
      super.m2((n + 1));
   }
}
class C extends B {
   @Override
   public void m1() {
      super.m1();
      System.out.print("" + 2 + " ");
   }
   public void m3() {
      this.m1();
      System.out.print("" + 1 + " ");
      System.out.print("" + 2 + " ");
   }
}
class Program {
   public void run() {
      A a;
      B b;
      C c;
      System.out.println("" + "1 2 1 2 1 2 1 2");
      b = new B();
      b.m2(1);
      c = new C();
      c.m1();
      c.m3();
   }
}
