package comp;
import java.util.*;
public class A {
   public void m() {
      Scanner variable = new Scanner(System.in);
      int i;
      boolean b;
      System.out.print("" + 6);
i = 1;
      while((i<=5)) { 
         System.out.print("" + i + " ");
i = (i + 1);
      }
b = false;
      while((b!=true)) { 
         System.out.print("" + 6 + " ");
b = !(b);
      }
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      System.out.println("" + "6 1 2 3 4 5 6");
a = new A();
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
