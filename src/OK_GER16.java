import java.util.*;
public class OK_GER16 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int k;
   public int get_A() {
      return this.k;
   }
   public void set(int k) {
      this.k = k;
   }
   public void print() {
      System.out.print("" + this.get_A() + " ");
   }
   public void init() {
      this.set(0);
   }
}
class B extends A {
   private int k;
   public int get_B() {
      return this.k;
   }
   @Override
   public void init() {
      super.init();
      this.k = 2;
   }
   @Override
   public void print() {
      System.out.print("" + this.get_B() + " ");
      System.out.print("" + this.get_A() + " ");
      super.print();
   }
}
class C extends A {
   @Override
   public int get_A() {
      return 0;
   }
}
class Program {
   public void run() {
      A a;
      B b;
      C c;
      System.out.println("" + "2 2 0 0 2 0 0 0 0 0 0");
      b = new B();
      b.init();
      c = new C();
      c.init();
      System.out.print("" + b.get_B() + " ");
      a = b;
      a.print();
      b.print();
      a.init();
      b.init();
      System.out.print("" + a.get_A() + " ");
      System.out.print("" + b.get_A() + " ");
      a = c;
      System.out.print("" + a.get_A() + " ");
      c = new C();
      System.out.print("" + c.get_A() + " ");
   }
}
