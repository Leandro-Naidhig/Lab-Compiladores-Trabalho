import java.util.*;
public class OK_SIN10 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int n;
   public int get() {
      return this.n;
   }
   public void set(int pn) {
      this.n = pn;
   }
}
class B extends A {
   private int k;
   public void m() {
      int i;
      i = Scan.imputValue.nextInt();
      this.k = Scan.imputValue.nextInt();
      super.set(0);
      System.out.println("" + super.get());
      System.out.println("" + this.get());
      System.out.println("" + this.k);
      System.out.println("" + i);
   }
   public void print() {
      System.out.println("" + this.k);
   }
}
