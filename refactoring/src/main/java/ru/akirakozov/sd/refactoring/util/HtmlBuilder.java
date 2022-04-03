package ru.akirakozov.sd.refactoring.util;

/**
 * @author Madiyar Nurgazin
 */
public class HtmlBuilder {
    private final static String HTML_BODY_OPEN_TAG = "<html><body>";
    private final static String HTML_BODY_CLOSE_TAG = "</body></html>";

    private final StringBuilder html;

    public HtmlBuilder() {
        html = new StringBuilder().append(HTML_BODY_OPEN_TAG);
    }

    public HtmlBuilder addLine(String line) {
        html.append(line).append('\n');
        return this;
    }

    public HtmlBuilder addHeader(String line, int level) {
        html.append((String.format("<h%d>%s</h%s>", level, line, level))).append('\n');
        return this;
    }

    public String build() {
        return html.append(HTML_BODY_CLOSE_TAG).toString();
    }
}
