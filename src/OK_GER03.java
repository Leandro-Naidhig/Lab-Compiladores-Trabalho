package comp;
import java.util.*;
public class A {
   public void m() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + 6 + " ");
      if((true && true)) {
         System.out.print("" + 1 + " ");
      }
      if((false && true)) {
         System.out.print("" + 1000 + " ");
      }
      if((true && false)) {
         System.out.print("" + 1000 + " ");
      }
      if((false && false)) {
         System.out.print("" + 1000 + " ");
      }
      if((true || true)) {
         System.out.print("" + 2 + " ");
      }
      if((true || false)) {
         System.out.print("" + 3 + " ");
      }
      if((false || true)) {
         System.out.print("" + 4 + " ");
      }
      if((false || false)) {
         System.out.print("" + 1000 + " ");
      }
      if(!(false)) {
         System.out.print("" + 5 + " ");
      }
      if(!(true)) {
         System.out.print("" + 1000 + " ");
      }
      if((true || (true && false))) {
         System.out.print("" + 6 + " ");
      }
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      System.out.println("" + "6 1 2 3 4 5 6");
a = new A();
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
