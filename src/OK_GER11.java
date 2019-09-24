package comp;
import java.util.*;
public class A {
   public void m1() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + " 2 ");
   }
   public void m2(int n) {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + n + " ");
m1   }
}
public class B extends A {
   override void m1() {
      Scanner variable = new Scanner(System.in);
      System.out.println("" + " 4 ");
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      B b;
      System.out.println("" + "4 1 2 3 4");
      System.out.print("" + "4 ");
a = new A();
a.m2(1);
a = new B();
a.m2(3);
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
