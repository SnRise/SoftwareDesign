package ru.akirakozov.sd.refactoring.servlet.query;

/**
 * @author Madiyar Nurgazin
 */
public class UnknownCommandQuery extends Query {
    private final String command;

    public UnknownCommandQuery(String command) {
        super(null);
        this.command = command;
    }

    @Override
    protected void executeQuery() {
        htmlBuilder.addLine("Unknown command: " + command);
    }
}
