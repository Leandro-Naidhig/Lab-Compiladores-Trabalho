import java.util.*;
public class OK_GER07 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m() {
      System.out.println("" + 0);
   }
}
class Program {
   public void run() {
      A a;
      System.out.println("" + "0");
      a = new A();
      a.m();
   }
}