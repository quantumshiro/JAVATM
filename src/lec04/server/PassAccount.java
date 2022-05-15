import java.io.Serializable;

public class PassAccount implements Serializable
{
    public String bankName;
    public String branchName;
    public String accountHolder;
    private String password;
    public int accountNumber;
    private int amount;

    public PassAccount(String bankName, String branchName, String accountHolder, int initialValue, String password) {
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountHolder = accountHolder;
        amount = initialValue;
        this.password = password;
        this.accountNumber = (int) (Math.random() * 1000000);
    }

    public boolean IsPasswordCorrect(String password, String pass) {
        if (password.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public PassAccount()
    {
        this.bankName = "name bank";
        this.branchName = "branch branch";
        this.accountHolder = "John";
        this.amount = 0;
        this.password = "1234";
        this.accountNumber = 1234567890;
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

    public String password()
    {
        return password;
    }
}
