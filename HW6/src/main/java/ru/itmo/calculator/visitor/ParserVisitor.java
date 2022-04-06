package ru.itmo.calculator.visitor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;
import ru.itmo.calculator.token.Token;
import ru.itmo.calculator.token.TokenType;

/**
 * @author Madiyar Nurgazin
 */
public class ParserVisitor implements TokenVisitor {
    private final Deque<Token> deque = new ArrayDeque<>();
    private final List<Token> tokens = new ArrayList<>();

    public List<Token> parseTokens(List<Token> tokenList) {
        for (Token token : tokenList) {
            token.accept(this);
        }

        tokens.addAll(deque);
        return tokens;
    }

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.getType() == TokenType.LEFT) {
            deque.push(token);
        } else {
            if (deque.isEmpty()) {
                throw new IllegalStateException();
            }

            Token next = deque.pop();
            while (next.getType() != TokenType.LEFT) {
                tokens.add(next);

                if (deque.isEmpty()) {
                    throw new IllegalStateException();
                }
                next = deque.pop();
            }
        }
    }

    @Override
    public void visit(Operation token) {
        while (!deque.isEmpty()) {
            Token next = deque.peek();
            if (token.getPriority() > next.getPriority()) {
                break;
            }

            tokens.add(deque.pop());
        }

        deque.push(token);
    }
}
