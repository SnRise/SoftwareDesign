package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

/**
 * @author Madiyar Nurgazin
 */
public class NumberToken extends CommonToken {
    private final int value;

    public NumberToken(int value, TokenType type) {
        super(type);
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", type, value);
    }
}
