package http;

import java.util.Collections;
import java.util.List;

import dao.AccountDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class RxNettyAccountHttpServer {
    private final AccountDao dao;

    public RxNettyAccountHttpServer(AccountDao dao) {
        this.dao = dao;
    }

    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);

        try {
            switch (path) {
                case "add_user":
                    return addUser(request);
                case "add_money":
                    return addMoney(request);
                case "get_all_money":
                    return getAllMoney(request);
                case "buy_equities":
                    return buyEquities(request);
                case "sell_equities":
                    return sellEquities(request);
                case "get_user_equities_info":
                    return getUserEquitiesInfo(request);
                default:
                    throw new RuntimeException("Unsupported request : " + path);
            }
        } catch (Exception e) {
            return Observable.error(e);
        }
    }

    private <T> Observable<String> addUser(HttpServerRequest<T> request) {
        checkRequestParameters(request, Collections.singletonList("id"));

        long id = getId(request);
        return dao.addUser(id).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> addMoney(HttpServerRequest<T> request) {
        checkRequestParameters(request, List.of("id", "count"));

        long id = getId(request);
        int count = getCount(request);
        return dao.addMoney(id, count).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getAllMoney(HttpServerRequest<T> request) {
        checkRequestParameters(request, Collections.singletonList("id"));

        long id = getId(request);
        return dao.getAllMoney(id).map(String::valueOf).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> buyEquities(HttpServerRequest<T> request) {
        checkRequestParameters(request, List.of("id", "company_name", "count"));

        long id = getId(request);
        String companyName = getQueryParam(request, "company_name");
        int count = getCount(request);
        return dao.buyEquities(id, companyName, count).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> sellEquities(HttpServerRequest<T> request) {
        checkRequestParameters(request, List.of("id", "company_name", "count"));

        long id = getId(request);
        String companyName = getQueryParam(request, "company_name");
        int count = getCount(request);
        return dao.sellEquities(id, companyName, count).onErrorReturn(Throwable::getMessage);
    }

    private <T> Observable<String> getUserEquitiesInfo(HttpServerRequest<T> request) {
        checkRequestParameters(request, List.of("id"));

        long id = getId(request);
        return dao.getUserEquitiesInfo(id).map(equities -> equities + "\n");
    }

    public static <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

    public static <T> int getCount(HttpServerRequest<T> request) {
        return Integer.parseInt(getQueryParam(request, "count"));
    }

    public static <T> long getId(HttpServerRequest<T> request) {
        return Long.parseLong(getQueryParam(request, "id"));
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
