package receive;

import java.io.Serializable;

public class Account implements Serializable
{
    public final String bankName;
    public final String branchName;
    public final String accountHolder;
    private int amount;

    public Account(String bankName, String branchName, String accountHolder, int initialValue)
    {
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountHolder = accountHolder;
        amount = initialValue;
    }

    public Account()
    {
        this.bankName = "hoge bank";
        this.branchName = "hoge branch";
        this.accountHolder = "hogehoge";
        this.amount = 0;
    }

    public void deposit(int depositValue)
    {
        amount += depositValue;
    }

    public int draw(int drawValue)
    {
        // money -> 引き出したお金-> return 
        int money = 0;
        if (amount < drawValue)
        {
            money = amount;
            amount = 0;
        }
        else
        {
            money = drawValue;
            amount = amount - money;
        }
        return money;
    }

    public int getBalance()
    {
        return amount;
    }

}

