import java.util.*;
public class ER_SEM39 {
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
   public B m() {
      return new A();
   }
   public void run() {
   }
}
