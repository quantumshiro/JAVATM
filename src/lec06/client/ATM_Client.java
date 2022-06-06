import java.io.*;
import java.net.*;
import java.security.MessageDigest;


public class ATM_Client {
    public static void main(String[] args) {
        try {
            int PORT = 49152;
            String IP = "127.0.0.1";
            byte[] getbuf = new byte[1024];
            byte[] sendbuf = new byte[1024];
            int len = 0;

            String Success = "Success";

            InetAddress IPaddr = InetAddress.getByName(IP);
            Socket socket = new Socket(IPaddr,PORT);

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            while (true) {
                System.out.println("server is waiting...");
                System.out.print("操作番号を入力してください(0: 口座新規作成, 1: 既存口座) : ");
                InputStreamReader is = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(is);
                int select = Integer.parseInt(br.readLine());

                String selectNumber = Integer.toString(select);
                sendbuf = selectNumber.getBytes();
                len = sendbuf.length;
                out.write(sendbuf, 0, len);

                getbuf = new byte[1024];
                len = in.read(getbuf);
                String check = new String(getbuf, 0, len);
                if (check.equals(Success)) {

                } else {
                    System.out.println("reaction error");
                    break;
                }

                if (select == 0) {
                    System.out.println("銀行名");
                    String bank = br.readLine();
                    sendbuf = bank.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("支店名");
                    String branch = br.readLine();
                    sendbuf = branch.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("口座名義");
                    String name = br.readLine();
                    sendbuf = name.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("初期預金額");
                    String value = br.readLine();
                    sendbuf = value.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("パスワード");
                    String pass = br.readLine();

                    sendbuf = pass.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    len = in.read(getbuf);
                    String accountNumber = new String(getbuf, 0, len);
                    // FileInputStream inFile = new FileInputStream(name + "_" + accountNumber + ".obj");
                    System.out.println("口座番号は" + accountNumber + "です。");

                    break;
                } else if (select == 1) {
                    System.out.println("認証方式を選択してください。(0: パスワード認証, 1: CHAP認証): ");
                    select = Integer.parseInt(br.readLine());
                    
                    System.out.println("お名前を入力してください");
                    String name = br.readLine();
                    sendbuf = name.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    System.out.println("口座番号を入力して下さい");
                    String accountNumber = br.readLine();
                    sendbuf = accountNumber.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    selectNumber = Integer.toString(select);
                    sendbuf = selectNumber.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    if (select == 0) {
                        System.out.print("please input password: ");
                        String pass = br.readLine();

                        System.out.println("send password");
                        sendbuf = pass.getBytes();
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);

                        System.out.println("start authentication");
                        len = in.read(getbuf);
                        check = new String(getbuf, 0, len);

                        if (check.equals(Success)) {
                            System.out.println("認証成功");
                        } else {
                            System.out.println("認証失敗");
                            break;
                        }
                    } else if (select == 1) {
                        System.out.println("please input password: ");
                        String pass = br.readLine();
                        System.out.println("get random number");

                        len = in.read(getbuf);
                        String rand = new String(getbuf, 0, len);
                        System.out.println("create digest");
                        pass = pass + rand;
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        System.out.println("digest: " + md.digest(pass.getBytes()));

                        md.update(pass.getBytes());
                        byte[] cipher = md.digest();

                        System.out.println("send message digest");

                        sendbuf = cipher;
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);

                        System.out.println("start authentication");
                        len = in.read(getbuf);
                        check = new String(getbuf, 0, len);

                        if (check.equals(Success)) {
                            System.out.println("authentication success");
                        } else {
                            System.out.println("authentication failed");
                            break;
                        }
                    }
                } else {
                    System.out.println("selection error");
                    break;
                }

                while (true) {
                    System.out.println("口座への操作を入力してください。（0: 預金, 1: 引き出し, 2: 残高確認, 3: 操作の終了 4: 顧客データの削除）: ");

                    select = Integer.parseInt(br.readLine());

                    selectNumber = Integer.toString(select);
                    sendbuf = selectNumber.getBytes();
                    len = sendbuf.length;
                    out.write(sendbuf, 0, len);

                    if (select == 0) {
                        System.out.println("入金額を入力して下さい");
                        select = Integer.parseInt(br.readLine());

                        selectNumber = Integer.toString(select);
                        sendbuf = selectNumber.getBytes();
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);

                        System.out.println("残高は以下の通りです");
                        len = in.read(getbuf);
                        String yen = new String(getbuf, 0, len);
                        System.out.println(yen + "円");

                    }
                    else if (select == 1) {
                        System.out.println("引き出したい額を入力して下さい、残高より多い金額は残高分のみ引き出されます");
                        select = Integer.parseInt(br.readLine());

                        selectNumber = Integer.toString(select);
                        sendbuf = selectNumber.getBytes();
                        len = sendbuf.length;
                        out.write(sendbuf, 0, len);

                        System.out.println("残高は以下の通りです");
                        len = in.read(getbuf);
                        String yen = new String(getbuf, 0, len);
                        System.out.println(yen + "円");

                    }
                    else if (select == 2) {
                        System.out.println("残高は以下の通りです");
                        len = in.read(getbuf);
                        String yen = new String(getbuf, 0, len);
                        System.out.println(yen + "円");

                    }
                    else if (select == 3) {
                        System.out.println("操作を終了します");
                        break;
                    }
                    else if (select == 4) {
                        System.out.println("ご愛顧いただきありがとうございました。");
                        break;
                    }
                    else {
                        System.out.println("Number error");
                    }
                }

                if (select == 3||select == 4) {
                    break;
                }
            }
            System.out.println("ご利用ありがとうございました。\n");
            in.close();
            out.close();
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}