package ru.itmo.calculator.token;

/**
 * @author Madiyar Nurgazin
 */
public abstract class CommonToken implements Token {
    protected final TokenType type;

    protected CommonToken(TokenType type) {
        this.type = type;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getPriority() {
        return getType().getPriority();
    }

    @Override
    public String toString() {
        return getType().toString();
    }
}
