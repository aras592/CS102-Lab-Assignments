import java.util.*;

public class User {
    String id, name, surname;
    List<Account> accounts = new ArrayList<>();
    User(String id, String name, String surname) 
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
    void addAccount(Account a) 
    { 
        accounts.add(a);
    }
    double getTotalAmountInCommonCurrency() 
    {
        double sum = 0;
        for (Account a : accounts) 
        {
            sum += a.getCommon();
        }
        return sum;
    }
}