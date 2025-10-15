import java.util.Scanner;

import dataStructures.StackWithListInArray;

public class Main{

  private static final String FALSE = "false";
  private static final String TRUE = "true";

  public static void main(String[] args) {
  Scanner in = new Scanner(System.in);
  String expr = in.nextLine();
  System.out.println(expr + " - " + isBalanced(expr) );
  in.close();
  }

  private static String isBalanced(String expr) {
    StackWithListInArray<Character> stack = new StackWithListInArray<>();

    for(int i = 0; i < expr.length(); i++){
      char ch = expr.charAt(i);

      if(ch == '(' || ch == '[' || ch == '{') stack.push(ch);
      else if(ch == ')' || ch == ']' || ch == '}'){
        if(stack.isEmpty()) return FALSE;

        char top = stack.pop();
        switch(ch){
          case ')': if (top != '(') return FALSE;
          break;
          case ']': if (top != '[') return FALSE;
          break;
          case '}': if (top != '{') return FALSE;
          break;
        }
        
      }
    }

    if(stack.isEmpty()) return TRUE;
    else return FALSE;
  }

}