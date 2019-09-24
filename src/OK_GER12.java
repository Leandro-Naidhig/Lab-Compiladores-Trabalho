package comp;
import java.util.*;
public class A {
   public void m1() {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + 1 + " ");
   }
   public void m2(int n) {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + n + " ");
   }
}
public class B extends A {
   override void m2(int n) {
      Scanner variable = new Scanner(System.in);
      System.out.print("" + n + " ");
