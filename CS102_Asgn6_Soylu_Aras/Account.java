public class Account {
    char type;
    double amount;

    Account(char type, double amount) 
    { 
        this.type = type; 
        this.amount = amount; 
    }
    
    double getCommon() 
    {
        switch (type) 
        {
            case 'A': return amount * BankApplication.rateA;
            case 'B': return amount * BankApplication.rateB;
            case 'C': return amount * BankApplication.rateC;
            case 'D': return amount * BankApplication.rateD;
        }
        return 0;
    }
}