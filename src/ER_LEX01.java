import java.util.*;
public class ER_LEX01 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class Teste {
   private int n;
   public int get() {
      return this.n;
   }
   public void set(int n) {
      this.n = n;
   }
}
