package ru.itmo.calculator.token;

/**
 * @author Madiyar Nurgazin
 */
public enum State {
    START {
        @Override
        public Token createNextToken(Tokenizer tokenizer) {
            char c = tokenizer.getCurrentCharacter();
            tokenizer.nextCharacter();
            return switch (c) {
                case '(' -> new Brace(TokenType.LEFT);
                case ')' -> new Brace(TokenType.RIGHT);
                case '+' -> new Operation(TokenType.PLUS);
                case '-' -> new Operation(TokenType.MINUS);
                case '*' -> new Operation(TokenType.MUL);
                case '/' -> new Operation(TokenType.DIV);
                default -> throw new IllegalStateException();
            };
        }

        @Override
        public void nextState(Tokenizer tokenizer) {
            if (!tokenizer.hasNextCharacter()) {
                tokenizer.setState(END);
            } else if (tokenizer.isOperation()) {
                tokenizer.setState(START);
            } else if (tokenizer.isDigit()) {
                tokenizer.setState(NUMBER);
            } else {
                tokenizer.setState(ERROR);
            }
        }

    },
    NUMBER {
        @Override
        public Token createNextToken(Tokenizer tokenizer) {
            StringBuilder number = new StringBuilder();

            while (tokenizer.hasNextCharacter() && Character.isDigit(tokenizer.getCurrentCharacter())) {
                number.append(tokenizer.getCurrentCharacter());
                tokenizer.nextCharacter();
            }

            return new NumberToken(Integer.parseInt(number.toString()), TokenType.NUMBER);
        }

        @Override
        public void nextState(Tokenizer tokenizer) {
            if (!tokenizer.hasNextCharacter()) {
                tokenizer.setState(END);
            } else if (tokenizer.isOperation()) {
                tokenizer.setState(START);
            } else {
                tokenizer.setState(ERROR);
            }
        }
    },
    END,
    ERROR;

    public Token createNextToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }

    public void nextState(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }
}
