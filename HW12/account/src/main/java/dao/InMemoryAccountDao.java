package dao;

import java.util.HashMap;
import java.util.Map;

import http.ExchangeClient;
import model.Equities;
import model.User;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class InMemoryAccountDao implements AccountDao {
    private final ExchangeClient exchangeClient;
    private final Map<Long, User> users = new HashMap<>();
    private final String SUCCESS = "SUCCESS";

    public InMemoryAccountDao(ExchangeClient exchangeClient) {
        this.exchangeClient = exchangeClient;
    }

    @Override
    public Observable<String> addUser(long id) {
        if (users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " already exists"));
        }
        users.put(id, new User(id, 0));
        return Observable.just(SUCCESS);
    }

    @Override
    public Observable<String> addMoney(long id, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        users.get(id).addMoney(count);
        return Observable.just(SUCCESS);
    }

    @Override
    public Observable<Integer> getAllMoney(long id) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        User user = users.get(id);
        return Observable.from(user.getEquities())
                .map(this::updateEquitiesPrice)
                .map(equities -> equities.getCount() * equities.getPrice())
                .defaultIfEmpty(0)
                .reduce(Integer::sum)
                .map(x -> x + user.getMoney());
    }

    @Override
    public Observable<String> buyEquities(long id, String companyName, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }

        try {
            int price = exchangeClient.getEquitiesPrice(companyName);
            int availableCount = exchangeClient.getEquitiesCount(companyName);
            if (availableCount < count) {
                return Observable.error(new IllegalArgumentException("Not enough equities in exchange"));
            }
            users.get(id).buyEquities(companyName, price, count);
            exchangeClient.buyEquities(companyName, count);
            return Observable.just(SUCCESS);
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }

    @Override
    public Observable<String> sellEquities(long id, String companyName, int count) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }

        try {
            int price = exchangeClient.getEquitiesPrice(companyName);
            users.get(id).sellEquities(companyName, price, count);
            exchangeClient.sellEquities(companyName, count);
            return Observable.just(SUCCESS);
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }

    @Override
    public Observable<Equities> getUserEquitiesInfo(long id) {
        if (!users.containsKey(id)) {
            return Observable.error(new IllegalArgumentException("User with id = " + id + " doesn't exist"));
        }
        return Observable.from(users.get(id).getEquities()).map(this::updateEquitiesPrice);
    }

    private Equities updateEquitiesPrice(Equities equities) {
        return equities.changePrice(exchangeClient.getEquitiesPrice(equities.getCompanyName()));
    }
}
