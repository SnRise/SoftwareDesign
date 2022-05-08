package dao;

import model.Equities;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public interface AccountDao {

    Observable<String> addUser(long id);

    Observable<String> addMoney(long id, int count);

    Observable<Integer> getAllMoney(long id);

    Observable<String> buyEquities(long id, String companyName, int count);

    Observable<String> sellEquities(long id, String companyName, int count);

    Observable<Equities> getUserEquitiesInfo(long id);

}
