package ru.itmo.calculator.token;

import ru.itmo.calculator.visitor.TokenVisitor;

/**
 * @author Madiyar Nurgazin
 */
public interface Token {
    void accept(TokenVisitor visitor);

    TokenType getType();

    int getPriority();
}
