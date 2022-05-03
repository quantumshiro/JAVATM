import java.io.*;
import java.net.*;

public class FileSnd
{
    public static void main(String[] args)
    {
        byte[] sbuf = new byte[10000000];
        int len;
        int PORT = 49152;
        String IP = "127.0.0.1";
        String OK = "OK";

        try
        {
            InetAddress IPaddr = InetAddress.getByName(IP);
            Socket socket = new Socket(IPaddr, PORT);
            OutputStream out = socket.getOutputStream(); // 送信用ストリーム
            InputStream in = socket.getInputStream(); // 受信用ストリーム

            sbuf = args[0].getBytes();
            len = sbuf.length;
            out.write(sbuf, 0, len);

            len = in.read(sbuf);
            String buf = new String(sbuf, 0, len);
            System.out.println(buf);

            if (OK.equals(buf))
            {
                FileInputStream fin = new FileInputStream(args[0]);
                while ((len = fin.read(sbuf)) != -1)
                {
                    out.write(sbuf, 0, len);
                }
                fin.close();
            }
            else
            {
                System.out.println("Error");
            }
            socket.close();
            out.close();
            in.close();
        }
        catch(SocketException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}