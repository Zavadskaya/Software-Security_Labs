package com.example.nakkicalvin.labaaessecond;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import android.os.Bundle;
import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    static final String TAG = "SymmetricAlgorithmAES";
    static final  String files = "KeyAES.txt";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Original text
        /*EditText text1 = (EditText) findViewById(R.id.editText1);
        String theTestText = text1.getText().toString();
        TextView tvorig = (TextView) findViewById(R.id.textView);
        tvorig.setText("\n[ORIGINAL]:\n" + theTestText + "\n");*/

        // Set up secret key spec for 128-bit AES encryption and decryption
    }
       public void saves(View view)
        {
            EditText text1 = (EditText) findViewById(R.id.editText1);
            String theTestText = text1.getText().toString();
            TextView tvorig = (TextView) findViewById(R.id.textView);
            tvorig.setText("\n[ORIGINAL]:\n" + theTestText + "\n");


        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");

            File file = new File(/*Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),*/files);
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(sks.getEncoded());
            fo.close();

        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
        }
        TextView tvencoded = (TextView)findViewById(R.id.textView1);
        tvencoded.setText("[ENCODED]:\n" +
                Base64.encodeToString(encodedBytes, Base64.DEFAULT) + "\n");



        // Encode the original data with AES
        File file = new File(/*Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),*/files);
        try {
            FileInputStream reader = new FileInputStream(file);
            byte[] buf = new byte[reader.available()];
            reader.read(buf);
            //String s = new String(buf);
            sks = new SecretKeySpec(buf, "AES");
            reader.close();
        }
        catch (Exception ex){
        }



        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
        }
        TextView tvdecoded = (TextView)findViewById(R.id.textView2);
        tvdecoded.setText("[DECODED]:\n" + new String(decodedBytes) + "\n");

    }
}
