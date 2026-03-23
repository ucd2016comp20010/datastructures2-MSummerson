package project20280.stacksqueues;

import project20280.interfaces.Stack;

class BracketChecker {
    private String input;

    public BracketChecker(String in) {
        input = in;
    }

    public boolean check() {
        // TODO
        Stack<Character> stack = new LinkedStack<>();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                char lastOpen = stack.pop();
                if (c == '}' && lastOpen != '{') {
                    return false;
                }
                if (c == ']' && lastOpen != '[') {
                    return false;
                }
                if (c == ')' && lastOpen != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
            System.out.println("outcome: " + checker.check());
        }
    }
}