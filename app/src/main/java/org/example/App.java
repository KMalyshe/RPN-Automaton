package org.example;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class App {

    public static void main(String[] args) {

        final TextIO textIO = TextIoFactory.getTextIO();
        final TextTerminal<?> terminal = textIO.getTextTerminal();
        
        String upn = textIO.newStringInputReader().read("Enter your input sequence:");
        if (!isValidUPN(upn)) {
            terminal.println("Rejected. Input contains illegal characters.");
            awaitExit(textIO);
        }
        boolean stepMode = getExecutionMode(textIO);

        Stack stack = new Stack(upn.length());
        String numRegex = "^[0-9]+$";
        String opRegex = "^[+*]+$";

        for (int i = 0; i < upn.length(); i++) {
            String current = String.valueOf(upn.charAt(i));

            if (current.matches(numRegex)) {
                stack.push(Integer.valueOf(current));
            }

            else if (current.matches(opRegex)) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                if (operand1 == Stack.NO_ENTRY || operand2 == Stack.NO_ENTRY) {
                    terminal.println("Rejected. Insufficient numbers for operand.");
                    awaitExit(textIO);
                }
                stack.push(current.equals("*") ? operand1 * operand2 : operand1 + operand2);
            }
            printStackIfStepMode(stack, stepMode, terminal);
        }

        int result = stack.pop();
        if (result != Stack.NO_ENTRY && stack.top == Stack.NO_ENTRY) {
            terminal.println("Result: " + result);
        } else {
            terminal.println("Rejected. Stack contains more than a single number at end of word.");
        }
        awaitExit(textIO);
    }

    private static boolean isValidUPN(String expression) {
        return expression.matches("^[0-9+*]+$");
    }

    private static boolean getExecutionMode(TextIO textIO) {
        int choice = textIO.newIntInputReader().read("Enter your execution mode (1-Step(Default))(2-Run)");
        return choice != 2; // Default is step mode
    }

    private static void awaitExit(TextIO textIO) {
        textIO.newStringInputReader().read("Enter any key to exit..");
        System.exit(0);
    }

    private static void printStackIfStepMode(Stack stack, boolean stepMode, TextTerminal<?> terminal) {
        if (stepMode) {
            terminal.println(stack.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
            }
        }
    }
}
