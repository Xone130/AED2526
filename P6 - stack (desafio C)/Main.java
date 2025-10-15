import java.util.Scanner;

public class Main{

  public static void main(String[] args) {
  Scanner in = new Scanner(System.in);
  String expr = in.nextLine();
  System.out.println(expr + " - " + isBalanced(expr) );
  in.close();
  }

  private static String isBalanced(String expr) {
    return "false";
  }

  



}