import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        
        System.out.println("Enter an expression");
        Scanner scan = new Scanner(System.in);
        String expr = scan.nextLine();

        for (int i=0; i<expr.length();) {
            if (Character.isAlphabetic(expr.charAt(i+1))) {
                System.out.printf("You result is: %s\n", lispEvaluation.evaluate(expr).toString());
                break;
            }
            else {
                System.out.printf("You result is: %s\n", lispEvaluation2.evaluate(expr).toString());
                break;
            }
        }
        scan.close();
        System.out.println("Program concluded, rerun to use again.");
    }
}
