package comp;
import java.util.*;
public class A {
   public private  i, ;   public private  j, ;   private void p() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   private void q() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
   public void init_A() {
      Scanner variable = new Scanner(System.in);
i = 1;
j = 2;
   }
   public void call_p() {
      Scanner variable = new Scanner(System.in);
p   }
   public void call_q() {
      Scanner variable = new Scanner(System.in);
q   }
   public void r() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   public void s() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
}
public class B extends A {
   public private  i, ;   public private  j, ;   private void p() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   private void q() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
   public void init_B() {
      Scanner variable = new Scanner(System.in);
i = 3;
j = 4;
   }
   override void call_p() {
      Scanner variable = new Scanner(System.in);
p   }
   override void call_q() {
      Scanner variable = new Scanner(System.in);
q   }
   override void r() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   override void s() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
}
public class C extends A {
   public private  i, ;   public private  j, ;   private void p() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   private void q() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
   public void init_C() {
      Scanner variable = new Scanner(System.in);
i = 5;
j = 6;
   }
   override void call_p() {
      Scanner variable = new Scanner(System.in);
p   }
   override void call_q() {
      Scanner variable = new Scanner(System.in);
q   }
   override void r() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + i + " ");
   }
   override void s() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + j + " ");
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      B b;
      C c;
      System.out.println("" + "1 2 1 2 3 4 3 4 5 6 5 6");
a = new A();
b = new B();
c = new C();
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
