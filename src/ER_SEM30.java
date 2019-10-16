import java.util.*;
public class ER_SEM30 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void put() {
   }
}
class B extends A {
   @Override
   public int put() {
      return 0;
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      a.put();
   }
}
