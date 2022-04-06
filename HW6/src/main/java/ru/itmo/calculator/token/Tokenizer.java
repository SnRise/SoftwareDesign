package ru.itmo.calculator.token;

import java.util.Set;

/**
 * @author Madiyar Nurgazin
 */
public class Tokenizer {
    private final Set<Character> operationSymbols;
    private final String input;

    private int index;
    private State state;

    public Tokenizer(String input) {
        this.operationSymbols = Set.of('+', '-', '*', '/', '(', ')');
        this.input = input.trim();
        this.index = 0;

        State.START.nextState(this);
    }

    public Token next() {
        Token next = state.createNextToken(this);
        while (hasNextCharacter() && isWhiteSpace()) {
            nextCharacter();
        }

        state.nextState(this);
        return next;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean hasNext() {
        return state == State.START || state == State.NUMBER;
    }

    public boolean hasNextCharacter() {
        return index < input.length();
    }

    public char getCurrentCharacter() {
        return input.charAt(index);
    }

    public void nextCharacter() {
        index++;
    }

    private boolean isWhiteSpace() {
        return Character.isWhitespace(getCurrentCharacter());
    }

    public boolean isDigit() {
        return Character.isDigit(getCurrentCharacter());
    }

    public boolean isOperation() {
        return operationSymbols.contains(getCurrentCharacter());
    }

    public boolean isErrorState() {
        return state == State.ERROR;
    }

}
