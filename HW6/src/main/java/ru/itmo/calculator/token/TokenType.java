package ru.itmo.calculator.token;

import java.util.function.BiFunction;

/**
 * @author Madiyar Nurgazin
 */
public enum TokenType {
    PLUS((a, b) -> a + b, 1),
    MINUS((a, b) -> a - b, 1),
    MUL((a, b) -> a * b, 2),
    DIV((a, b) -> a / b, 2),
    NUMBER(3) {
        @Override
        public int evaluate(int left, int right) {
            throw new UnsupportedOperationException();
        }
    },
    LEFT(0) {
        @Override
        public int evaluate(int left, int right) {
            throw new UnsupportedOperationException();
        }
    },
    RIGHT(0) {
        @Override
        public int evaluate(int left, int right) {
            throw new UnsupportedOperationException();
        }
    };

    private final BiFunction<Integer, Integer, Integer> operation;
    private final int priority;

    TokenType(int priority) {
        this.operation = null;
        this.priority = priority;
    }

    TokenType(BiFunction<Integer, Integer, Integer> operation, int priority) {
        this.operation = operation;
        this.priority = priority;
    }

    public int evaluate(int left, int right) {
        return operation.apply(left, right);
    }

    public int getPriority() {
        return priority;
    }
}
