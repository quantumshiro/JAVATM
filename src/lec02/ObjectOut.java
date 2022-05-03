import java.io.*;

public class ObjectOut
{
    public static void main(String[] args)
    {
        int goal;
        int depositOfYear;
        int years = 0;
        int total_balance = 0;

        try
        {
            // Account のインスタンス myAccount 作成
            Account myAccount = new Account(args[0], args[1], args[2], Integer.parseInt(args[3]));
            // Account myAccount2 = new Account();
            FileOutputStream fos = new FileOutputStream(args[6]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // 引数で指定された目標金額を goal に代入
            goal = Integer.parseInt(args[4]);
            // 引数で指定された毎年預金額を depositOfYear に代入したのち、標準出力に出力する
            depositOfYear = Integer.parseInt(args[5]);
            // System.out.println("Goal: " + goal);

            //預金残額が目標金額に達しない場合に繰り返す条件
            while(total_balance < goal)
            {
                //預金残額が目標金額に達しない場合に毎年預金額を追加していく
                myAccount.deposit(depositOfYear);
                total_balance = myAccount.getBalance();
                //預金残額を毎年追加していくため、年数を1年増やす
                years++;
            }
            System.out.println("years: " + years);
            // 変数 total_balance に預金残額を代入
            total_balance = myAccount.getBalance();

            oos.writeObject(myAccount);
            oos.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }   
}
