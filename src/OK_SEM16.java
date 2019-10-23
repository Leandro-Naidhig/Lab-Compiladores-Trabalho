import java.util.*;
public class OK_SEM16 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class Program {
   public void run() {
      int n = 10;
      do {
         if((n < 5)) {
            break
;
         }
         n = (n - 1);
      } while((n == 0));
   }
}
