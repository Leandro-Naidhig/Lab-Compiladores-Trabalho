import java.util.*;
public class OK_GER03 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m() {
      System.out.print("" + 6 + " ");
      if((true && true)) {
         System.out.print("" + 1 + " ");
      }
      if(((false && true))) {
         System.out.print("" + 1000 + " ");
      }
      if((true && false)) {
         System.out.print("" + 1000 + " ");
      }
      if(((false && false))) {
         System.out.print("" + 1000 + " ");
      }
      if((true || true)) {
         System.out.print("" + 2 + " ");
      }
      if(((true || false))) {
         System.out.print("" + 3 + " ");
      }
      if((false || true)) {
         System.out.print("" + 4 + " ");
      }
      if(((false || false))) {
         System.out.print("" + 1000 + " ");
      }
      if((!(false))) {
         System.out.print("" + 5 + " ");
      }
      if(!(true)) {
         System.out.print("" + 1000 + " ");
      }
      if(((true || ((true && false))))) {
         System.out.print("" + 6 + " ");
      }
   }
}
class Program {
   public void run() {
      A a;
      System.out.println("" + "6 1 2 3 4 5 6");
      a = new A();
      a.m();
   }
}
