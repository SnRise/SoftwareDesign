package dao;

import model.Equities;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public interface ExchangeDao {
    Observable<String> addCompany(String name, int equitiesCount, int equitiesPrice);

    Observable<Equities> getCompanies();

    Observable<String> addEquities(String companyName, int equitiesCount);

    Observable<Equities> getEquitiesInfo(String companyName);

    Observable<String> buyEquities(String companyName, int count);

    Observable<String> changeEquitiesPrice(String companyName, int newEquitiesPrice);
}
