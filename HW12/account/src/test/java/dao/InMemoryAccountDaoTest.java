package dao;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import http.ExchangeHttpClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

/**
 * @author Madiyar Nurgazin
 */
public class InMemoryAccountDaoTest {
    @ClassRule
    public static GenericContainer exchangeContainer = new FixedHostPortGenericContainer("exchange:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    private AccountDao dao;
    private String test_company_name;

    private final static String BASE_URI = "http://localhost:8080";
    private final static String SUCCESS = "SUCCESS";
    private final static int TEST_COMPANY_EQUITIES_PRICE = 30;
    private final static long TEST_USER_ID = 1;
    private final static int TEST_USER_INITIAL_MONEY = 1000;


    @Before
    public void setUp() throws Exception {
        exchangeContainer.start();
        test_company_name = "Hooli" + UUID.randomUUID();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URI + "/add_company?name=" + test_company_name + "&equities_count=1000" +
                        "&equities_price=" + TEST_COMPANY_EQUITIES_PRICE))
                .GET()
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        dao = new InMemoryAccountDao(new ExchangeHttpClient());
        dao.addUser(TEST_USER_ID);
        dao.addMoney(TEST_USER_ID, TEST_USER_INITIAL_MONEY);
    }

    @After
    public void tearDown() {
        exchangeContainer.stop();
    }

    @Test
    public void buyEquities() {
        Assert.assertEquals(SUCCESS, dao.buyEquities(TEST_USER_ID, test_company_name, 10).toBlocking().single());
    }

    @Test
    public void sellEquities() throws Exception {
        Assert.assertEquals(SUCCESS, dao.buyEquities(TEST_USER_ID, test_company_name, 10).toBlocking().single());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URI + "/change_equities_price?company_name=" + test_company_name +
                        "&new_equities_price=" + TEST_COMPANY_EQUITIES_PRICE * 3))
                .GET()
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Assert.assertEquals(1600, dao.getAllMoney(TEST_USER_ID).toBlocking().single().intValue());
        Assert.assertEquals(SUCCESS, dao.sellEquities(TEST_USER_ID, test_company_name, 10).toBlocking().single());

    }

    @Test
    public void companyDoesNotExists() {
        Throwable exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> dao.buyEquities(TEST_USER_ID, "aaa", 10).toBlocking().single()
        );
        Assert.assertEquals("Company with name 'aaa' doesn't exists", exception.getMessage());
    }

    @Test
    public void notEnoughEquities() {
        Throwable exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> dao.sellEquities(TEST_USER_ID, test_company_name, 100).toBlocking().single()
        );
        Assert.assertEquals("Not enough equities", exception.getMessage());
    }

    @Test
    public void notEnoughEquitiesInExchange() {
        Throwable exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> dao.buyEquities(TEST_USER_ID, test_company_name, 2000).toBlocking().single()
        );
        Assert.assertEquals("Not enough equities in exchange", exception.getMessage());
    }

    @Test
    public void notEnoughMoney() {
        Throwable exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> dao.buyEquities(TEST_USER_ID, test_company_name, 150).toBlocking().single()
        );
        Assert.assertEquals("Not enough money", exception.getMessage());
    }
}
