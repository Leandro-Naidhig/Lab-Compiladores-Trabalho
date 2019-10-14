import java.util.*;
public class OK_GER14 {
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
   public void init() {
      this.k = 1;
   }
}
class B extends A {
   private int k;
   public int get_B() {
      return this.k;
   }
   public void init() {
      super.init();
      this.k = 2;
   }
}
class C extends B {
   private int k;
   public int get_C() {
      return this.k;
   }
   public void init() {
      super.init();
      this.k = 3;
   }
}
class D extends C {
   private int k;
   public int get_D() {
      return this.k;
   }
   public void init() {
      super.init();
      this.k = 4;
   }
}
class Program {
   public void run() {
      A a;
      B b;
      C c;
      D d;
      System.out.println("" + "4 3 2 1");
      d = new D();
      d.init();
      System.out.print("" + d.get_D() + " ");
      c = d;
      System.out.print("" + c.get_C() + " ");
      b = c;
      System.out.print("" + b.get_B() + " ");
      a = b;
      System.out.print("" + a.get_A() + " ");
   }
}
