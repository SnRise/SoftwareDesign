package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import model.Equities;
import org.bson.Document;
import rx.Observable;
import rx.functions.Func2;

/**
 * @author Madiyar Nurgazin
 */
public class MongoExchangeDao implements ExchangeDao {
    private final static String SUCCESS = "SUCCESS";

    private final MongoCollection<Document> companies;

    public MongoExchangeDao(MongoCollection<Document> companies) {
        this.companies = companies;
    }

    @Override
    public Observable<String> addCompany(String name, int equitiesCount, int equitiesPrice) {
        return companies
                .find(Filters.eq("companyName", name))
                .toObservable()
                .isEmpty()
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        return companies.insertOne(
                                new Equities(name, equitiesCount, equitiesPrice).toDocument()
                        ).map(String::valueOf);
                    } else {
                        return Observable.error(new IllegalArgumentException("Company with name '" + name + "' " +
                                "already exists"));
                    }
                });
    }

    @Override
    public Observable<Equities> getCompanies() {
        return companies.find().toObservable().map(Equities::new);
    }

    @Override
    public Observable<String> addEquities(String companyName, int equitiesCount) {
        return manageEquities(companyName, Equities::add, equitiesCount);
    }

    @Override
    public Observable<Equities> getEquitiesInfo(String companyName) {
        return companies
                .find(Filters.eq("companyName", companyName))
                .toObservable()
                .map(Equities::new)
                .switchIfEmpty(Observable.error(new IllegalArgumentException("Company with name '" + companyName + "'" +
                        " doesn't exists")));
    }

    @Override
    public Observable<String> buyEquities(String companyName, int count) {
        return manageEquities(companyName, Equities::minus, count);
    }

    @Override
    public Observable<String> changeEquitiesPrice(String companyName, int newEquitiesPrice) {
        return manageEquities(companyName, Equities::changePrice, newEquitiesPrice);
    }

    private Observable<String> manageEquities(String companyName, Func2<Equities, Integer, Equities> action,
                                              int parameter) {
        return companies
                .find(Filters.eq("companyName", companyName))
                .toObservable()
                .map(Equities::new)
                .defaultIfEmpty(null)
                .flatMap(company -> {
                    if (company == null) {
                        return Observable.error(new IllegalArgumentException("Company with name '" + companyName + "'" +
                                " doesn't exists"));
                    } else {
                        return companies.replaceOne(
                                        Filters.eq("companyName", companyName),
                                        action.call(company, parameter).toDocument())
                                .map(doc -> SUCCESS);
                    }
                });
    }
}
