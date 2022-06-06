import java.io.*;
import java.net.*;

public class FileRcv
{
    public static void main(String[] args)
    {
        byte[] sbuf = new byte[10000000];
        byte[] rbuf = new byte[10000000];

        int len;
        int PORT = 49152;
        String OK = "OK";

        try
        {
            try (ServerSocket server = new ServerSocket(PORT)) {
                Socket socket = server.accept();

                InputStream in = socket.getInputStream(); // 受信用ストリーム
                OutputStream out = socket.getOutputStream(); // 送信用ストリーム
                
                // InetAddress IP_addr = socket.getInetAddress();

                len = in.read(sbuf);
                String buf = new String(sbuf, 0, len);
                System.out.println(buf);
                sbuf = OK.getBytes();
                len = sbuf.length;
                out.write(sbuf, 0, len);

                FileOutputStream fout = new FileOutputStream(buf);
                while ((len = in.read(rbuf)) != -1)
                {
                    fout.write(rbuf, 0, len);
                }
                fout.close();
                in.close();
                out.close();
                socket.close();
            }
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