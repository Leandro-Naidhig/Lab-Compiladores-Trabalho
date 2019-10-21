import java.util.*;
public class ER_SEM51 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int i;
   public void put(int x, int y, boolean ok) {
   }
}
class B extends A {
   public void put(int x, int y, int ok) {
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      a.put(0, 1, true);
   }
}
