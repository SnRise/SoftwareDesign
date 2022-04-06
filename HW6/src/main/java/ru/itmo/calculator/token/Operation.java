package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

/**
 * @author Madiyar Nurgazin
 */
public class Operation extends CommonToken {
    public Operation(TokenType type) {
        super(type);
    }

    public int evaluate(int left, int right) {
        return getType().evaluate(left, right);
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
