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

public class QRCodeApp extends JFrame {
    private JButton openFileButton;
    private JPanel panel1;
    private JLabel label1;
    private final JFrame thisApp;

    public QRCodeApp() {
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
                    byte[] allBytes = new byte[(int) fileSize];
                    FileInputStream fstream = null;
                    try {
                        fstream = new FileInputStream(file);
                        fstream.read(allBytes);

                        String contents = new String(allBytes, "MS932");

                        QRCodeWriter writer = new QRCodeWriter();

                        Hashtable hints = new Hashtable();
                        hints.put(EncodeHintType.CHARACTER_SET, "MS932");

                        BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 600, 600, hints);
                        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                        ImageIcon ic = new ImageIcon(image);
                        label1.setIcon(ic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        });

    }

    public static void main(final String[] args) {
        try {
            QRCodeApp window = new QRCodeApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
