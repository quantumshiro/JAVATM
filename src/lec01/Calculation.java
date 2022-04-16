public class Calculation {
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
            // 引数で指定された目標金額を goal に代入
            goal = Integer.parseInt(args[4]);
            // 引数で指定された毎年預金額を depositOfYear に代入したのち、標準出力に出力する
            depositOfYear = Integer.parseInt(args[5]);
            // System.out.println("Goal: " + goal);

            //預金残額が目標金額に達しない場合に繰り返す条件
            while(total_balance < goal)
            {
                //預金残額が目標金額に達しない場合に毎年預金額を追加していく
                total_balance += depositOfYear;
                //預金残額を毎年追加していくため、年数を1年増やす
                years++;
            }
            // 変数 total_balance に預金残額を代入
            total_balance = myAccount.getBalance();
            // 銀行名、支店名、口座所有者、開設金額、目標金額、預金残額、所要年数をそれぞれ 改行して標準出力
            System.out.println("銀行名 : " + myAccount.bankName);
            System.out.println("支店名 : " + myAccount.branchName);
            System.out.println("口座所有者 : " + myAccount.accountHolder + " 様");
            System.out.println("口座開設金額 : " + myAccount.getBalance() + " 円");
            System.out.println("目標金額 : " + goal + " 円");
            System.out.println("年預金額 : " + depositOfYear + " 円");
            System.out.println("所要年数 : " + years + " 年");
            System.out.println("");
            
            // 預金残額 +1 万円を引き出す処理を行い、引出し要求金額、実際の引出し金額、引出し後の預金残額をそれぞれ改行して標準出力
            int past_six_years = Integer.parseInt(args[3]) + 6 * depositOfYear;
            
            for (int i = 0; i < years; i++) {
                myAccount.deposit(depositOfYear);
            }
            int ans = myAccount.draw(past_six_years + 10000);
            System.out.println("引出し要求金額 : " + (past_six_years + 10000) + " 円");
            System.out.println("実際の引出し金額 : " + ans + " 円");
            System.out.println("引出し後の預金残額 : " + myAccount.getBalance() + " 円");


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }   
}
