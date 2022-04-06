package ru.itmo.calculator.visitor;

import java.util.List;

import ru.itmo.calculator.token.Brace;
import ru.itmo.calculator.token.NumberToken;
import ru.itmo.calculator.token.Operation;
import ru.itmo.calculator.token.Token;

/**
 * @author Madiyar Nurgazin
 */
public class PrintVisitor implements TokenVisitor {
    private StringBuilder expression;

    public String printTokens(List<Token> tokens) {
        expression = new StringBuilder();
        for (Token token : tokens) {
            token.accept(this);
        }

        return expression.toString();
    }

    @Override
    public void visit(NumberToken token) {
        append(token);
    }

    @Override
    public void visit(Brace token) {
        append(token);
    }

    @Override
    public void visit(Operation token) {
        append(token);
    }

    private void append(Token token) {
        expression.append(token).append(' ');
    }
}
