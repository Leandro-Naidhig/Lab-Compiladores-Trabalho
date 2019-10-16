import java.util.*;
public class OK_SEM11 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void open() {
   }
}
class Program {
   public void run() {
      A a;
      a = new A();
      a.open();
   }
}
