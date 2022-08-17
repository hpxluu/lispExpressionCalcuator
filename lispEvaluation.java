import java.util.Stack;
import java.util.Scanner;
import java.lang.RuntimeException;

public class lispEvaluation {

    public static boolean isBalanced(String expr) {

        Stack<Character> isExpbal = new Stack<>();    
        boolean result = false;    
        
        int i=0;     
        while (i<expr.length()) {

            if (expr.charAt(0)==')' && expr.charAt(expr.length()-1)=='(') { 
                return result;
            }

            if ((expr.charAt(i)=='(') || (expr.charAt(i)==')')) {
                isExpbal.push(expr.charAt(i));
            }
            i++;
        }

        int count=0, count2=0;
        while (isExpbal.isEmpty()==false) {
            if (isExpbal.peek()==')') {
                isExpbal.pop();
                count++;
            }
            else {
                isExpbal.pop();
                count2++;
            }
        }

        if (count == count2) {
            result = true;
        }
        return result;
    }
    
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
                                values.push(Character.toString(expr.charAt(i))); // every other default cases 
                            }
                        }
                        break;
                }
            }

            if (!(operate.isEmpty())) { // after all completing all the calculation and the operate stack is not empty, then throw exception
                throw new RuntimeException(); 
            }

        }
        else {
            scan.close();
            throw new RuntimeException();
        }
        
        scan.close(); // close scan after usage
        return Double.parseDouble(values.peek());
    }

    protected static int misc(String expr, int i, Stack<String> values, int m, Scanner scan) {

        if(m==0) {
            Double varUser; 
            System.out.println("What is the value of "+"'"+expr.charAt(i)+"'"+"?");
            varUser = scan.nextDouble();
            values.push(varUser.toString());
        } 

        else if (m==1) {
            StringBuilder digits = new StringBuilder();
            int j = i; 
            while (j<expr.length()) {
                i=j-1; 
                if (Character.isDigit(expr.charAt(j))) { 
                    digits.append(expr.charAt(j));
                }
                else if (expr.charAt(j) == ')' || expr.charAt(j) == ' ' || expr.charAt(j) == '(') { 
                    break;
                }
                j++; 
            }
            
            String[] arr = digits.toString().split(" "); // the string builder will be split into a string array
            int k = 0;
            while(k<arr.length) {
                values.push(arr[k]); // pushes the strings of the string array into the value stack
                k++;       		
            }
        }
        return i;
    }
    
    protected static String calculation(Stack<Character> operate, Stack<String> values) {
        Double result=0.0, id1=0.0, id2=1.0; 

        if (values.isEmpty()) { 
            switch(operate.pop()) {
                case '+':
                    result = id1; 
                    break;
                case '*':
                    result = id2; 
                    break;
                default:
                    break;
            } 
        }
        
        if (!(values.isEmpty())) { 
            switch(operate.pop()) { 
                case ('+'):
                    while (!(values.peek().equals("("))) { 
                        id1 += Double.parseDouble(values.pop()); 
                    }
                    result = id1;
                    break;
                case ('-'): 
                    Stack<String> tempstack = new Stack<>();
                    while (!(values.isEmpty()) && !(values.peek().equals("("))) {
                        tempstack.push(values.pop());
                    }
                    if (tempstack.size()==1) { 
                        result = id1 - Double.parseDouble(tempstack.pop()); 
                    } 
                    else {
                        id1 = Double.parseDouble(tempstack.pop());
                        while (!(tempstack.isEmpty())) {
                            id1 -= Double.parseDouble(tempstack.pop());
                        }
                        result = id1;
                    }
                    break;
                case ('*'):
                    while (!(values.peek().equals("("))) {
                        id2 *= Double.parseDouble(values.pop()); 
                    }
                    result = id2;
                    break;
                case ('/'):
                    Stack<String> tempstack2 = new Stack<>();
                    while (!(values.isEmpty()) && !(values.peek().equals("("))) {
                        tempstack2.push(values.pop());
                    }
                    if (tempstack2.size()==1) { 
                        result = id2 / Double.parseDouble(tempstack2.pop()); 
                    } 
                    else {
                        id2 = Double.parseDouble(tempstack2.pop()); 
                        while (!(tempstack2.isEmpty())) { 
                            id2 /= Double.parseDouble(tempstack2.pop()); 
                        }
                        result = id2;
                    }
                    break;  
                default:
                    break;
            }
        }
    
        if (values.peek().equals("(")) { 
            values.pop();
        } 
        return result.toString(); 
    }
}
