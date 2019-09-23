package comp;
import java.util.*;
public class A {
   public void m() {
      Scanner variable = new Scanner(System.in);
      int a, b, c, d, e, f;
a = variable.nextInt();
b = variable.nextInt();
c = variable.nextInt();
d = variable.nextInt();
e = variable.nextInt();
f = variable.nextInt();
      System.out.print("" + a);
      System.out.print("" + b);
      System.out.print("" + c);
      System.out.print("" + d);
      System.out.print("" + e);
      System.out.print("" + f);
   }
}
public class Program {
   public void run() {
      Scanner variable = new Scanner(System.in);
      A a;
      System.out.println("" + "");
      System.out.println("" + "Ok-ger05");
      System.out.println("" + "The output should be what you give as input.");
      System.out.println("" + "Type in six numbers");
a = new A();
   }
}
public class OK_GER01 {
   public static void main(String []args) {
      new Program().run();
   }
}
