import java.io.*;
import java.net.*;

public class ATM_Server {
    public static void main(String[] args) {
        try {
            int PORT = 49152; // サーバのポート番号
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("server start");
            Socket socket = server.accept();
            System.out.println("client connect");
            byte[] getbuf = new byte[1024];
            byte[] sendbuf = new byte[1024];
            int len;

            OutputStream out = socket.getOutputStream(); // 送信用ストリーム
            InputStream in = socket.getInputStream();// 受信用ストリーム

            String Success = "Success";
            String Fail = "Failure";

            while (true) {
                len = in.read(getbuf);

                String select = new String(getbuf, 0, len);

                if (select.equals("0")) {
                    sendbuf = Success.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);
                    System.out.println("新規口座開設");

                    System.out.println("銀行名");
                    len = in.read(getbuf);
                    String bank = new String(getbuf, 0, len);
                    System.out.println(bank);
                    System.out.println("---------------------");

                    System.out.println("支店名");
                    len = in.read(getbuf);
                    String branch = new String(getbuf, 0, len);
                    System.out.println(branch);
                    System.out.println("---------------------");

                    System.out.println("御名");
                    len = in.read(getbuf);
                    String name = new String(getbuf, 0, len);
                    System.out.println(name);
                    System.out.println("---------------------");

                    System.out.println("初期資産");
                    len = in.read(getbuf);
                    String initialmoney = new String(getbuf, 0, len);
                    System.out.println(initialmoney);
                    int value = Integer.parseInt(initialmoney);
                    System.out.println("---------------------");

                    System.out.println("パスワード");
                    len = in.read(getbuf);
                    String password = new String(getbuf, 0, len);
                    System.out.println(password);
                    System.out.println("---------------------");

                    PassAccount Account = new PassAccount(bank, branch, name, value, password);

                    System.out.println("口座開設完了");
                    FileOutputStream outFile = new FileOutputStream(name + "_" + Account.accountNumber + ".obj");
                    ObjectOutputStream objectOut = new ObjectOutputStream(outFile);
                    objectOut.writeObject(Account);
                    objectOut.close();

                    System.out.println("口座番号の送付");

                    String accountNumber = Integer.toString(Account.accountNumber);
                    sendbuf = accountNumber.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);
                    break;
                }

                else if (select.equals("1")) {
                    sendbuf = Success.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("御名と口座番号");
                    len = in.read(getbuf);
                    String name = new String(getbuf, 0, len);
                    len = in.read(getbuf);
                    String accountNumber = new String(getbuf, 0, len);

                    FileInputStream inFile = new FileInputStream(name + "_" + accountNumber + ".obj"); // 格納されているファイル名を特定
                    ObjectInputStream objectln = new ObjectInputStream(inFile);
                    PassAccount inAccount = (PassAccount) objectln.readObject();
                    objectln.close();

                    // System.out.println("認証方式の選択");
                    len = in.read(getbuf);

                    // select = new String(getbuf, 0, len);

                    System.out.println("パスワード認証を行います");

                    System.out.println("パスワードをチェックします");
                    len = in.read(getbuf);
                    String password = new String(getbuf, 0, len);
                    if (inAccount.IsPasswordCorrect(inAccount.password(), password)) {
                        System.out.println("パスワードが一致しました");
                        sendbuf = Success.getBytes();
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);
                    } else {
                        System.out.println("パスワードが一致しませんでした");
                        sendbuf = Fail.getBytes();
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);
                        break;
                    }

                    while (true) {
                        System.out.println("操作番号の受信");
                        len = in.read(getbuf);
                        select = new String(getbuf, 0,len);
                        if (select.equals("0")) {
                            System.out.println("貯金額を取得");
                             
                            len = in.read(getbuf);
                            String money = new String(getbuf, 0, len);
                            System.out.println(money);
                            int value = Integer.parseInt(money);
                            
                            inAccount.deposit(value);
                            System.out.println("預金残高の送信");
                            String money_send = Integer.toString(inAccount.getBalance());
                            sendbuf = money_send.getBytes();
                            len = sendbuf.length;
                            out.write(sendbuf, 0, len);


                            
                        } else if (select.equals("1")) {
                            System.out.println("引き下ろし額を取得");

                            len = in.read(getbuf);
                            String draw_money = new String(getbuf, 0, len);
                            System.out.println(draw_money);
                            int value = Integer.parseInt(draw_money);

                            int r = inAccount.draw(value);
                            r = inAccount.getBalance();
                            System.out.println("預金残高の送信");
                            sendbuf = Integer.toString(r).getBytes();
                            len = sendbuf.length;
                            out.write(sendbuf, 0, len);


                        } else if (select.equals("2")) {
                            int r = inAccount.getBalance();
                            System.out.println("預金残高の送信");
                            sendbuf = Integer.toString(r).getBytes();
                            len = sendbuf.length;
                            out.write(sendbuf, 0, len);
                        }

                        else if (select.equals("3")) {
                            System.out.println(inAccount.bankName);
                            System.out.println(inAccount.branchName);
                            System.out.println(inAccount.accountHolder);

                            FileOutputStream outFile = new FileOutputStream(name + "_" + accountNumber + ".obj");
                            ObjectOutputStream objectOut = new ObjectOutputStream(outFile);
                            objectOut.writeObject(inAccount);
                            objectOut.close();
                            break;
                        }
                        
                        else if (select.equals("4")) {
                            System.out.println("顧客情報の削除");
                            File f = new File(name + "_" + accountNumber + ".obj");
                            f.delete();
                            break;
                        }

                    }

                } else {
                    System.out.println("失敗");
                    break;
                }
                if (select.equals("3")|| select.equals("4")) {
                    break;
                }
            }

            System.out.println("取引終了");
            sendbuf = Success.getBytes();
            len = sendbuf.length;
            out.write(sendbuf, 0, len);

        
            in.close();
            out.close();
            socket.close();
            server.close();


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
