import java.util.*;
public class OK_LEX02 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m() {
      int i;
      i = 1;
      System.out.print("" + i);
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      a.m();
   }
}
