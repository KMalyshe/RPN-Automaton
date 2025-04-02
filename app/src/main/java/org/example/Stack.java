package org.example;

public class Stack {
    private int maxSize;
    private int[] stack;
    public int top;
    public static final int NO_ENTRY = -1;

    public Stack(int size) {
        this.maxSize = size;
        this.stack = new int[size];
        this.top = NO_ENTRY;

        for (int i = 0; i < maxSize; i++) {
            stack[i] = NO_ENTRY;
        }
    }

    private boolean isEmpty() {
        return top == NO_ENTRY;
    }

    private boolean isFull() {
        return top == maxSize - 1;
    }

    public void push(int value) {
        if (isFull()) {
            return;
        }
        stack[++top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            return NO_ENTRY;
        }
        int ret = stack[top];
        stack[top] = NO_ENTRY;
        top--;
        return ret;
    }

    public String toString() {
        String result = "[";
        for (int i = 0; i < maxSize; i++) {
            if (stack[i] == NO_ENTRY)
                break;
            result = result + stack[i] + ", ";
        }
        result = result.substring(0, result.length() - 2) + "]";
        return result;
    }
}
