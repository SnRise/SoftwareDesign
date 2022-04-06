package ru.itmo.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.itmo.calculator.token.Token;
import ru.itmo.calculator.token.Tokenizer;
import ru.itmo.calculator.visitor.CalcVisitor;
import ru.itmo.calculator.visitor.ParserVisitor;
import ru.itmo.calculator.visitor.PrintVisitor;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        PrintVisitor printVisitor = new PrintVisitor();
        CalcVisitor calcVisitor = new CalcVisitor();

        while (input != null && !input.isBlank()) {
            Tokenizer tokenizer = new Tokenizer(input);
            List<Token> tokens = new ArrayList<>();

            while (tokenizer.hasNext()) {
                tokens.add(tokenizer.next());
            }
            if (tokenizer.isErrorState()) {
                throw new IllegalStateException();
            }

            ParserVisitor parserVisitor = new ParserVisitor();

            System.out.println(printVisitor.printTokens(tokens));

            tokens = parserVisitor.parseTokens(tokens);

            System.out.println(printVisitor.printTokens(tokens));
            System.out.println(calcVisitor.calculate(tokens));

            input = reader.readLine();
        }
    }

}
