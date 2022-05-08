package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Madiyar Nurgazin
 */
public class User {
    private final long id;
    private final Map<String, Equities> equities;
    private int money;

    public User(long id, int money) {
        this.id = id;
        equities = new HashMap<>();
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int addition) {
        money += addition;
    }

    public Collection<Equities> getEquities() {
        return equities.values();
    }

    public void buyEquities(String companyName, int price, int count) {
        if (price * count > money) {
            throw new IllegalArgumentException("Not enough money");
        }
        Equities current = equities.getOrDefault(companyName, new Equities(companyName, 0, price));
        equities.put(companyName, current.add(count));
        money -= price * count;
    }

    public void sellEquities(String companyName, int price, int count) {
        if (!equities.containsKey(companyName) || equities.get(companyName).getCount() < count) {
            throw new IllegalArgumentException("Not enough equities");
        }
        Equities current = equities.get(companyName);
        equities.put(companyName, current.minus(count));
        money += price * count;
    }
}
