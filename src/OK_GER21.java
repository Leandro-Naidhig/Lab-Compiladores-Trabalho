import java.util.*;
public class OK_GER21 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int n;
   public void set(int n) {
      this.n = n;
   }
   public int get() {
      return this.n;
   }
}
class Program {
   private A a;
   public void print() {
      System.out.print("" + this.a);
   }
   private void set(A a) {
      this.a = a;
   }
   public A get() {
      return this.a;
   }
   public void run() {
      System.out.println("" + "0");
      System.out.println("" + "0");
   }
}
