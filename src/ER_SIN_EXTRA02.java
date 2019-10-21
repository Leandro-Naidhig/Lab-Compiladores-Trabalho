import java.util.*;
public class ER_SIN_EXTRA02 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   private int num1;
   private int num2;
   private int result;
   public int mult() {
      return (this.num1 * this.num2);
   }
   public void div(int num3, int num4) {
      this.result = (num3 / num4);
   }
}
class B {
   private String name;
   public void setName(String name) {
      this.name = name;
   }
   public String getName() {
      return this.name;
   }
}
