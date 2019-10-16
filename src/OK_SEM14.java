import java.util.*;
public class OK_SEM14 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
}
class B extends A {
}
class Program {
   public void run() {
      A a, a2;
      B b;
      a = null;
      b = null;
      a2 = null;
      if((a == b)) {
         System.out.println("" + 0);
      }
      if((b == a)) {
         System.out.println("" + 1);
      }
      if((a == a2)) {
         System.out.println("" + 2);
      }
   }
}
