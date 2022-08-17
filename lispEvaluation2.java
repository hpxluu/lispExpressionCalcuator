import java.util.Scanner;
import java.util.Stack;
import java.lang.RuntimeException;


public class lispEvaluation2 extends lispEvaluation {

    public static Double evaluate(String expr) {

        Stack<Character> operate = new Stack<>();
        Stack<String> values = new Stack<>();
        Scanner scan = new Scanner(System.in);

        if (isBalanced(expr)) {

            if (expr.equals("( )")) {
        		scan.close();
        		throw new RuntimeException();
        	}

            for (int i=0; i<expr.length(); i++) {
                switch(expr.charAt(i)) {
                    case ('('): 
                        values.push(Character.toString(expr.charAt(i))); 
                        break;
                    case (' '):
                        continue;
                    case ('+'): case ('-'): case ('*'): case ('/'): 
                        operate.push(expr.charAt(i));
                        break;
                    case (')'):
                        values.push(calculation(operate, values));
                        break;
                    default:
                        if (!(operate.isEmpty())) {
                            if(Character.isAlphabetic(expr.charAt(i))) {
                                i=misc(expr, i, values, 0, scan);
                            }

                            else if (Character.isDigit(expr.charAt(i+1))) {
                                i=misc(expr, i, values, 1, scan);
                            }

                            else {
                                values.push(Character.toString(expr.charAt(i)));
                            }
                        }
                        break;
                }
            }

            if (!(operate.isEmpty())) {
                scan.close();
                throw new RuntimeException();
            }
        }
        else {
            scan.close();
            throw new RuntimeException();
        }
        scan.close();
        return Double.parseDouble(values.peek());
    }

    protected static int misc(String expr, int i, Stack<String> values, int m, Scanner scan) {

        if(m==0) {
            if (Character.isAlphabetic(expr.charAt(i+1))) {
                Double varString;
                StringBuilder keyterm = new StringBuilder();
                int s = i;
                while (s<expr.length()) {
                    i = s-1;
                    if (expr.charAt(s) != ')' && (!(Character.isDigit(expr.charAt(s))))) {
                        keyterm.append(expr.charAt(s)); 
                    }

                    else if (Character.isDigit(expr.charAt(s))) { 
                        keyterm.append(' '); 
                        break;
                    }

                    else if (expr.charAt(s) == ')') { 
                         break;
                    }
                    s++;
                } 
                String[] word = keyterm.toString().split(" ");
                int w = 0;
                while (w<word.length) {
                    System.out.println("What is the value of "+"'"+word[w]+"'"+"?");
                    varString = scan.nextDouble(); 
                    values.push(varString.toString()); 
                    w++;
                }
            }

            else {
                Double varUser; 
                System.out.println("What is the value of "+"'"+expr.charAt(i)+"'"+"?");
                varUser = scan.nextDouble();
                values.push(varUser.toString());
            }
        } 

        else if (m==1) {
            StringBuilder digits = new StringBuilder();
            int j = i;
            while (j<expr.length()) {
                i=j-1;
                if (expr.charAt(j) != ')') {
                    digits.append(expr.charAt(j));
                }
                else if (expr.charAt(j) == ')') {
                    break;
                }
                j++;
            }

            String[] arr = digits.toString().split(" ");
            int k = 0;
            while(k<arr.length) {
                values.push(arr[k]); k++;
            }
        }
        return i;
    }
}