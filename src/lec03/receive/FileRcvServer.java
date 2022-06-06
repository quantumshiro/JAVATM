import java.io.*;
import java.net.*;

class FileRcvThread extends Thread {
    private Socket socket;

    public FileRcvThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        byte[] sbuf = new byte[10000000];
        byte[] rbuf = new byte[10000000];

        int len;
        String OK = "OK";

        try {
            InputStream in = socket.getInputStream(); // 受信用ストリーム
            OutputStream out = socket.getOutputStream(); // 送信用ストリーム

            len = in.read(sbuf);
            String buf = new String(sbuf, 0, len);
            System.out.println(buf);
            sbuf = OK.getBytes();
            len = sbuf.length;
            out.write(sbuf, 0, len);

            FileOutputStream fout = new FileOutputStream("output.txt");
            while ((len = in.read(rbuf)) != -1) {
                fout.write(rbuf, 0, len);
            }
            fout.close();
            in.close();
            out.close();
            socket.close();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

public class FileRcvServer {
    public static void main(String[] args) {
        int PORT = 49152;
        try {
            try (ServerSocket server = new ServerSocket(PORT)) {
                while (true) {
                    Socket socket = server.accept();
                    FileRcvThread thread = new FileRcvThread(socket);
                    thread.start();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
