import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        
        System.out.println("Enter an expression");
        Scanner scan = new Scanner(System.in);
        String expr = scan.nextLine();

        for (int i=0; i<expr.length();) {
            if (Character.isAlphabetic(expr.charAt(i+1))) {
                Double result = lispEvaluation.evaluate(expr);
                System.out.print(result);
                break;
            }
            else {
                Double result2 = lispEvaluation2.evaluate(expr);
                System.out.print(result2);
                break;
            }
        }
        scan.close();
    }
}