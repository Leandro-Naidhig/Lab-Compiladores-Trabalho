import java.util.*;
public class OK_GER05 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m() {
      int a, b, c, d, e, f;
      a = Scan.imputValue.nextInt();
      b = Scan.imputValue.nextInt();
      c = Scan.imputValue.nextInt();
      d = Scan.imputValue.nextInt();
      e = Scan.imputValue.nextInt();
      f = Scan.imputValue.nextInt();
      System.out.print("" + a);
      System.out.print("" + b);
      System.out.print("" + c);
      System.out.print("" + d);
      System.out.print("" + e);
      System.out.print("" + f);
   }
}
class Program {
   public void run() {
      A a;
      System.out.println("" + "");
      System.out.println("" + "Ok-ger05");
      System.out.println("" + "The output should be what you give as input.");
      System.out.println("" + "Type in six numbers");
      a = new A();
      a.m();
   }
}
