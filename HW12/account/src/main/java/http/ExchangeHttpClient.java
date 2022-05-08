package http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @author Madiyar Nurgazin
 */
public class ExchangeHttpClient implements ExchangeClient {
    private static final String SUCCESS = "SUCCESS";

    @Override
    public void buyEquities(String companyName, int count) {
        String response = sendRequest("buy_equities", Map.ofEntries(
                Map.entry("company_name", companyName),
                Map.entry("count", count)
        ));
        if (!response.equals(SUCCESS)) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public void sellEquities(String companyName, int count) {
        String response = sendRequest("add_equities", Map.ofEntries(
                Map.entry("company_name", companyName),
                Map.entry("count", count)
        ));
        if (!response.equals(SUCCESS)) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public int getEquitiesPrice(String companyName) {
        String response = sendRequest("get_equities_price", Map.ofEntries(Map.entry("company_name", companyName)));
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(response);
        }
    }

    @Override
    public int getEquitiesCount(String companyName) {
        String response = sendRequest("get_equities_count", Map.ofEntries(Map.entry("company_name", companyName)));
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(response);
        }
    }

    private String sendRequest(String path, Map<String, Object> parameters) {
        StringBuilder requestParameters = new StringBuilder();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            requestParameters.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        requestParameters.deleteCharAt(requestParameters.length() - 1);

        String requestString = "http://localhost:8080/" + path + "?" + requestParameters;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(requestString))
                    .GET()
                    .build();
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body().trim();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
