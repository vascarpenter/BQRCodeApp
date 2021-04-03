package com.hatenablog.gikoha.qrc;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class BQRCodeApp extends JFrame {
    private JButton openFileButton;
    private JPanel panel1;
    private JLabel label1;
    private final JFrame thisApp;


/* バイト配列をBASE64エンコードします。
            * @param bytes エンコード対象のバイト配列
 * @return エンコード後の文字列
 */
    private static String base64Encode( byte[] bytes ) {

        // バイト配列をビットパターンに変換します。
        StringBuffer bitPattern = new StringBuffer();
        for ( int i = 0; i < bytes.length; ++i ) {
            int b = bytes[i];
            if ( b < 0 ) {
                b += 256;
            }
            String tmp = Integer.toBinaryString( b );
            while ( tmp.length() < 8 ) {
                tmp = "0" + tmp;
            }
            bitPattern.append( tmp );
        }

        // ビットパターンのビット数が6の倍数にするため、末尾に0を追加します。
        while ( bitPattern.length() % 6 != 0 ) {
            bitPattern.append( "0" );
        }

        // 変換表
        final String[] table = {
                "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "0", "1", "2", "3",
                "4", "5", "6", "7", "8", "9", "+", "/"
        };

        // 変換表を利用して、ビットパターンを4ビットずつ文字に変換します。
        StringBuffer encoded = new StringBuffer();
        for ( int i = 0; i < bitPattern.length(); i += 6 ) {
            String tmp = bitPattern.substring( i, i + 6 );
            int index = Integer.parseInt( tmp, 2 );
            encoded.append( table[index] );
        }

        // 変換後の文字数を4の倍数にするため、末尾に=を追加します。
        while ( encoded.length() % 4 != 0 ) {
            encoded.append( "=" );
        }

        return encoded.toString();
    }
    public BQRCodeApp() {
        thisApp = this;

        setTitle("QRCode Generator");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel1);
        pack();
        setBounds(50, 50, 700, 700);
        setVisible(true);


        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(thisApp);
                if(returnVal ==   JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    long fileSize = file.length();

                    if(fileSize>2000) {
                        label1.setText("Too large file size");
                    } else {
                        byte[] allBytes = new byte[(int) fileSize];
                        FileInputStream fstream = null;
                        try {
                            fstream = new FileInputStream(file);
                            fstream.read(allBytes);

                            String contents = base64Encode(allBytes);

                            QRCodeWriter writer = new QRCodeWriter();

                            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 600, 600);
                            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                            ImageIcon ic = new ImageIcon(image);
                            label1.setIcon(ic);
                            label1.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

    }

    public static void main(final String[] args) {
        try {
            BQRCodeApp window = new BQRCodeApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
