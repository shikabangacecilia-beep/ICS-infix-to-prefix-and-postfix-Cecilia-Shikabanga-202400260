import java.util.Stack;

public class InfixConverter {

    static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '^') return 3;
        return 0;
    }

    // Infix → Postfix
    public static String toPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (char ch : infix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    output.append(stack.pop());
                stack.pop(); // remove '('
            } else { // operator
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch))
                    output.append(stack.pop());
                stack.push(ch);
            }
        }

        while (!stack.isEmpty())
            output.append(stack.pop());

        return output.toString();
    }

    // Infix → Prefix
    public static String toPrefix(String infix) {
        // Step 1: Reverse and flip brackets
        StringBuilder reversed = new StringBuilder();
        for (int i = infix.length() - 1; i >= 0; i--) {
            char ch = infix.charAt(i);
            if (ch == '(') reversed.append(')');
            else if (ch == ')') reversed.append('(');
            else reversed.append(ch);
        }

        // Step 2: Get postfix of reversed
        String postfix = toPostfix(reversed.toString());

        // Step 3: Reverse postfix
        return new StringBuilder(postfix).reverse().toString();
    }

    public static void main(String[] args) {
        String infix = "A+(B*C-(D/E^F)*G)*H";
        System.out.println("Infix:   " + infix);
        System.out.println("Postfix: " + toPostfix(infix));
        System.out.println("Prefix:  " + toPrefix(infix));
    }
}