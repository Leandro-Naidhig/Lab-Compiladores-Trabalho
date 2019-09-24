import java.util.*;
public class OK_GER09 {
   public static void main(String []args) {
      new Program().run();
   }
}
class Scan {
   public static Scanner imputValue = new Scanner(System.in);
}
class A {
   public void m1(int n) {
      System.out.print("" + ("1 " + n) + " ");
   }
}
class B extends A {
   public void m2(int n) {
      