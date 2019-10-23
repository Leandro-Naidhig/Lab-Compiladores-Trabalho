import java.util.*;
public class OK_GER09 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m1(int n) {
      System.out.print("" + ("1 " + n) + " ");
   }
}
class B extends A {
   public void m2(int n) {
      super.m1(1);
      System.out.print("" + (" 2 " + n) + " ");
   }
}
class C extends B {
   public void m3(int n) {
      super.m2(2);
      System.out.print("" + (" 3 " + n) + " ");
   }
   public void m4(int n) {
      this.m3(3);
      System.out.println("" + (" 4 " + n) + " ");
   }
}
class Program {
   public void run() {
      C c;
      System.out.println("" + "1 1 2 2 3 3 4 4");
      c = new C();
      c.m4(4);
   }
}
