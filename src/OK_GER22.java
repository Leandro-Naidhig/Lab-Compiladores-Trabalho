import java.util.*;
public class OK_GER22 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class Program {
   public void run() {
      System.out.println("" + "100");
      int i, j, n;
      i = 0;
      j = 0;
      n = 10;
      boolean b;
      b = false;
      do {
         n = (n + 1);
      } while((false));
      assert (n == 11) : "'repeat-until' statement with 'false' as expression'";
      n = 10;
      int sum;
      sum = 0;
      do {
         i = 0;
         do {
            i = (i + 1);
            sum = (sum + 1);
         } while((i < n));
         j = (j + 1);
      } while((j < n));
      System.out.println("" + sum);
      assert (sum == 100) : "Nested 'repeat-until' statement with two indexes";
   }
}
