
//ISTO TA ERRADO AAAAAAAAAA
public class Main{

  public static final int VECTOR_SIZE = 1280;
  public static final int STARTING_NUM = 10;

  public static void main(String args[]){
    int[] arr = new int[VECTOR_SIZE];
    int a = STARTING_NUM;

    for(int i = 1; i < 9; i++){
      allDiff(arr, a);
      System.out.println(a + " = "+ System.nanoTime());
      a = a * 2;
    }

  }

/**
*
* @param numbers : vetor preenchido até n-1
* @return true iff all the numbers are different
*/
public static boolean allDiff(int[] numbers,int n) {
  int i=0;
  while (i<n && diff(numbers[i],numbers,i,n)){
    i++;
  } 

  return i==n;
}
private static boolean diff(int number, int[] numbers, int pos, int
n) {
 int i=pos+1;
 while (i<n && number!=numbers[i])
 i++;
 return i==n;
}
}
