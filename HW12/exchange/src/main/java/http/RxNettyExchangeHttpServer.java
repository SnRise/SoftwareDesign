package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dao.ExchangeDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Equities;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class RxNettyExchangeHttpServer {
    private final ExchangeDao dao;

    public RxNettyExchangeHttpServer(ExchangeDao dao) {
        this.dao = dao;
    }

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);

        try {
            switch (path) {
                case "add_company":
                    return addCompany(request);
                case "get_companies":
                    return getCompanies();
                case "add_equities":
                    return addEquities(request);
                case "get_equities_price":
                    return getEquitiesPrice(request);
                case "get_equities_count":
                    return getEquitiesCount(request);
                case "buy_equities":
                    return buyEquities(request);
                case "change_equities_price":
                    return changeEquitiesPrice(request);
                default:
                    throw new RuntimeException("Unsupported request : " + path);
            }
        } catch (Exception e) {
            return Observable.just(e.getMessage());
        }
    }

    private <T> Observable<String> addCompany(HttpServerRequest<T> request) {
        checkRequestParameters(request, Arrays.asList("name", "equities_count", "equities_price"));

        String name = getQueryParam(request, "name");
        int equitiesCount = getIntParam(request, "equities_count");
        int equitiesPrice = getIntParam(request, "equities_price");
        return dao.addCompany(name, equitiesCount, equitiesPrice).onErrorReturn(Throwable::getMessage);
    }

    private Observable<String> getCompanies() {
        return dao.getCompanies().map(company -> company + "\n");
    }

    private <T> Observable<String> addEquities(HttpServerRequest<T> request) {
        checkRequestParameters(request, Arrays.asList("company_name", "count"));

        String companyName = getQueryParam(request, "company_name");
        int equitiesCount = getIntParam(request, "count");
        return dao.addEquities(companyName, equitiesCount).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> buyEquities(HttpServerRequest<T> request) {
        checkRequestParameters(request, Arrays.asList("company_name", "count"));

        String companyName = getQueryParam(request, "company_name");
        int count = getIntParam(request, "count");
        return dao.buyEquities(companyName, count).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getEquitiesPrice(HttpServerRequest<T> request) {
        checkRequestParameters(request, Collections.singletonList("company_name"));

        String companyName = getQueryParam(request, "company_name");
        return dao.getEquitiesInfo(companyName).map(Equities::getPrice).map(String::valueOf).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getEquitiesCount(HttpServerRequest<T> request) {
        checkRequestParameters(request, Collections.singletonList("company_name"));

        String companyName = getQueryParam(request, "company_name");
        return dao.getEquitiesInfo(companyName).map(Equities::getCount).map(String::valueOf).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> changeEquitiesPrice(HttpServerRequest<T> request) {
        checkRequestParameters(request, Arrays.asList("company_name", "new_equities_price"));

        String companyName = getQueryParam(request, "company_name");
        int newEquitiesPrice = getIntParam(request, "new_equities_price");
        return dao.changeEquitiesPrice(companyName, newEquitiesPrice).onErrorReturn(Throwable::getMessage);
    }

    public static <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> int getIntParam(HttpServerRequest<T> request, String param) {
        return Integer.parseInt(getQueryParam(request, param));
    }

    public static <T> void checkRequestParameters(HttpServerRequest<T> request, List<String> requiredParameters) {
        requiredParameters.stream()
                .filter(key -> !request.getQueryParameters().containsKey(key))
                .reduce((s1, s2) -> s1 + ", " + s2)
                .ifPresent(s -> {
                    throw new IllegalArgumentException(s + " parameters not found");
                });
    }
}
