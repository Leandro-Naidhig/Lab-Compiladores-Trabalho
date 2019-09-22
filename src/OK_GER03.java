package comp;
public class A {
   public void m() {
      System.out.print("" + (6) + (" "));
      if((true&&true)) {
         System.out.print("" + (1) + (" "));
      }
      if(((false&&true))) {
         System.out.print("" + (1000) + (" "));
      }
      if((true&&false)) {
         System.out.print("" + (1000) + (" "));
      }
      if(((false&&false))) {
         System.out.print("" + (1000) + (" "));
      }
      if((true||true)) {
         System.out.print("" + (2) + (" "));
      }
      if(((true||false))) {
         System.out.print("" + (3) + (" "));
      }
      if((false||true)) {
         System.out.print("" + (4) + (" "));
      }
      if(((false||false))) {
         System.out.print("" + (1000) + (" "));
      }
      if(((false))) {
         System.out.print("" + (5) + (" "));
      }
      if((true)) {
         System.out.print("" + (1000) + (" "));
      }
      if(((true||(true&&false)))) {
         System.out.print("" + (6) + (" "));
      }
   }
}
