import java.util.*;
public class OK_SIN09 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int n;
   private int k;
   private int m1() {
      return (this.n + 1);
   }
   private void m2(int pk) {
      this.k = pk;
   }
   public int m() {
      this.m2(0);
      return (this.m1();
 + this.k);
   }
   public void init() {
      this.k = 1;
      this.n = 0;
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      System.out.println("" + a.m());
   }
}
