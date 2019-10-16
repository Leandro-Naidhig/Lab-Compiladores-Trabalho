import java.util.*;
public class OK_SEM06 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int n;
   public void set(int pn) {
      int n;
      this.n = pn;
   }
   public int put(int n, String set) {
      boolean put;
      this.n = n;
      return n;
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      a.set(0);
   }
}
