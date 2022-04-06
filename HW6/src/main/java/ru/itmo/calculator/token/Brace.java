package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

/**
 * @author Madiyar Nurgazin
 */
public class Brace extends CommonToken {

    public Brace(TokenType type) {
        super(type);
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
