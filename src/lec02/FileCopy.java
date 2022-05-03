import java.io.*;

public class FileCopy
{
    public static void main(String[] args)
    {
        // 入力バッファを定義
        // BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        byte[] buf = new byte[10000000];
        // ファイルサイズを格納する変数を定義
        int fileSize = 0;
        // 1回で入力したバイト数を格納する変数を定義
        int readSize = 0;
        
        try
        {
            // FileInputStream クラスのインスタンス作成
            FileInputStream fis = new FileInputStream(args[0]);
            // FileOutputStream クラスのインスタンス作成
            FileOutputStream fos = new FileOutputStream(args[1]);

            // ファイルから入力し、戻り値が-1 でない場合に繰り返す
            while ((readSize = fis.read(buf)) != -1)
            {
                // 入力したデータを出力ファイルに書き込む
                // fos.write(readSize);
                // fos.write(buf, fileSize, readSize);
                fos.write(buf, 0, readSize);
                // ファイルサイズをカウントアップ
                // fileSize++;
                fileSize += readSize;
            }

            // 入力ファイル名, 出力ファイル名, ファイルサイズを画面に出力
            System.out.println("コピー元ファイル名: " + args[0]);
            System.out.println("コピー先本ファイル名: " + args[1]);
            System.out.println("コピーファイルサイズ: " + fileSize + "バイト");

            // 入力ファイル, 出力ファイルをクローズ
            fis.close();
            fos.close();
            // in.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}