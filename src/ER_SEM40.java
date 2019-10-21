import java.util.*;
public class ER_SEM40 {
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
class B extends A {
   @Override
   public void set(int pn) {
      System.out.println("" + pn);
      super.set(pn);
   }
}
