package ru.itmo.calculator.visitor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;
import ru.itmo.calculator.token.Token;

/**
 * @author Madiyar Nurgazin
 */
public class CalcVisitor implements TokenVisitor {
    private final Deque<Integer> deque = new ArrayDeque<>();

    public int calculate(List<Token> tokens) {
        if (tokens.isEmpty()) {
            return 0;
        }

        tokens.forEach(token -> token.accept(this));

        if (deque.size() != 1) {
            throw new IllegalArgumentException();
        }

        return deque.pop();
    }

    @Override
    public void visit(NumberToken token) {
        deque.push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(Operation token) {
        if (deque.size() < 2) {
            throw new IllegalStateException();
        }

        int right = deque.pop();
        int left = deque.pop();
        deque.push(token.evaluate(left, right));
    }
}
