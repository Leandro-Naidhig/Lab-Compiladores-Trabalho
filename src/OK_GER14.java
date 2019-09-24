package comp;
import java.util.*;
public class A {
   public private  k, ;   public  get_A() {
      Scanner variable = new Scanner(System.in);
   }
   public void init() {
      Scanner variable = new Scanner(System.in);
k = 1;
   }
}
public class B extends A {
   public private  k, ;   public  get_B() {
      Scanner variable = new Scanner(System.in);
   }
   override void init() {
      Scanner variable = new Scanner(System.in);
initk = 2;
   }
}
public class C extends B {
   public private  k, ;   public  get_C() {
      Scanner variable = new Scanner(System.in);
   }
   override void init() {
      Scanner variable = new Scanner(System.in);
initk = 3;
   }
}
public class D extends C {
   public private  k, ;   public  get_D() {
      Scanner variable = new Scanner(System.in);
   }
   override void init() {
      Scanner variable = new Scanner(System.in);
initk = 4;
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      B b;
      C c;
      D d;
      System.out.println("" + "4 3 2 1");
d = new D();
      System.out.print("" +  + " ");
c = d;
      System.out.print("" +  + " ");
b = c;
      System.out.print("" +  + " ");
a = b;
      System.out.print("" +  + " ");
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
