package http;

/**
 * @author Madiyar Nurgazin
 */
public interface ExchangeClient {

    void buyEquities(String companyName, int count);

    void sellEquities(String companyName, int count);

    int getEquitiesPrice(String companyName);

    int getEquitiesCount(String companyName);

}
