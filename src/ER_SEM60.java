import java.util.*;
public class ER_SEM60 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private void p() {
   }
   public void m() {
      this.p();
   }
}
