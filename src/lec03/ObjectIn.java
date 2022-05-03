import java.io.*;

public class ObjectIn
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream fis = new FileInputStream(args[0]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Account inAccount = (Account) ois.readObject();
            System.out.println("銀行名: " + inAccount.bankName);
            System.out.println("支店名: " + inAccount.branchName);
            System.out.println("口座所有者: " + inAccount.accountHolder);
            System.out.println("預金残額: " + inAccount.getBalance());
            ois.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
