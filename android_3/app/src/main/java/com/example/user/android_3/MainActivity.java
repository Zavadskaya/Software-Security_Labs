package com.example.user.android_3;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private final static String NOTES="notes.txt";
    public EditText editor;
    public Button btsave;
    public Button btread;
    public Button exit;
    public Button saves;
    public EditText trans;
    public TextView text;
    private File filePublic;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);


        filePublic  = new File(super.getExternalFilesDir(Environment.DIRECTORY_DCIM), "Filed.json");
        text=(TextView)findViewById(R.id.textView);

        saves=(Button) findViewById(R.id.save);

        trans=(EditText)findViewById(R.id.transword);
        editor = (EditText) findViewById(R.id.editor);
        editor.setBackgroundColor(240);
        btread = (Button) findViewById(R.id.bread);

        btread.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                try {

                    InputStream in = openFileInput(NOTES);

                    if (in != null) {
                        InputStreamReader tmp = new InputStreamReader(in);
                        BufferedReader reader = new BufferedReader(tmp);
                        String str = "";
                        StringBuilder buf = new StringBuilder();

                        while ((str = reader.readLine()) != null) {
                            buf.append(str + "\n");
                        }

                        in.close();
                        editor.setText(buf.toString());
                    }
                } catch (Exception e) {
                    try {
                        FileOutputStream mOutput = openFileOutput(NOTES, Activity.MODE_PRIVATE);
                        String data = "THIS DATA WRITTEN TO A FILE FIRST TIME!!!";
                        mOutput.write(data.getBytes());
                        mOutput.close();
                    } catch (FileNotFoundException ea) {
                        ea.printStackTrace();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }

                }

            }
        });

        btsave = (Button) findViewById(R.id.bsave);

        btsave.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                try {
                    FileOutputStream mOutput = openFileOutput(NOTES, Activity.MODE_PRIVATE);
                    String data = editor.getText().toString();
                    //String data_2 = trans.getText().toString();
                    //Vocabulary vocabulary = new Vocabulary(data,data_2);
                    mOutput.write(data.getBytes());
                    Toast.makeText(getApplicationContext(),"Successful write",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Warning!!")
                            .setMessage("Successful write!")
                            .setIcon(R.drawable.cat)
                            .setCancelable(false)
                            .setNegativeButton("ОК",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                editor.setText(" ");
                trans.setText(" ");

                    mOutput.close();


                } catch (FileNotFoundException ea) {
                    ea.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Not success!",Toast.LENGTH_SHORT).show();
                } catch (IOException ee) {
                    ee.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Not success!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
/*****************************************************************************************************************/


private static boolean exist(File file) {
    boolean rc;
    if(rc = file.exists())
        Log.d("Log_06", "Файл " + file.getName() + " существует");
    else
        Log.d("Log_06", "Файл " + file.getName() + " не найден");
    return rc;
}

    private static void create(File file) {
        try {
            file.createNewFile();
            Log.d("Log_06", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
        }
    }
   public static void write(Vocabulary person, File f) {
       if(!exist(f)) {
           create(f);
       }
    Gson gson = new GsonBuilder().create();
    String str = gson.toJson(person);
    try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
        raf.skipBytes((int)f.length());
        raf.writeBytes(str + "\n");
        Log.d("Log_06", "Данные записаны в файл " + f.getName());
    }
    catch (IOException e) {
        Log.d("Log_06", e.getMessage());
    }
}

    int getCode(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for (int i = 0; i <= n; i++)
            D2[i] = i;

        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if (D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }


    public static String read(File f) {
        {

            String str = new String();
            StringBuilder sb = new StringBuilder();
            Gson gson = new GsonBuilder().create();
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                int b = raf.read();
                do {
                    str += "{" + raf.readLine();
                    gson.fromJson(str, Vocabulary.class);
                    sb.append(str + "\n");
                    str = new String();
                    if (b == 123)      // Если символ ""
                        b = raf.read(); // Пропускаем этот символ и читаем дальше
                } while (b != -1);
            } catch (IOException e) {
                Log.d("Log_06", "Не удалось прочитать данные из файла " + f.getName());
            }
            Log.d("Log_06", "Данные из файла " + f.getName() + " прочитанны");
            return sb.toString();
        }
    }

    public void onClick_Save(View view) {
        Vocabulary person = new Vocabulary(editor.getText().toString(), trans.getText().toString());
            write(person, filePublic);
            text.setText(read(filePublic));
        }
    public Vocabulary getFromJson(String word, File f) {
        Vocabulary vocabulary = null;
        String str = new String();
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int b = raf.read();
            do{
                str += "{" + raf.readLine();
                JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                    if (getCode(jsonObject.get("word").getAsString(),editor.getText().toString()) == 1) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        //editor.setText(getCodes(jsonObject.get("word").getAsString(),editor.getText().toString()));
                        String keyword = editor.getText().toString();
                        String text = jsonObject.get("word").getAsString();
                        String[] data = text.split(" ");
                        List<Integer> dist = new ArrayList<Integer>();
                        for (int i = 0; i < data.length; i++)
                        {

                            dist.add(getCode(data[i], keyword));
                        }
                        Collections.sort(dist);
                        for (int i = 0; i < data.length; i++) {
                            if (getCode(data[i], keyword) == dist.get(0)) {
                                editor.setText(data[i] + " ");
                                trans.setText(jsonObject.get("translate").getAsString());
                            }
                        }

                    }
                    else if(word.equals(jsonObject.get("word").getAsString())) {
                        vocabulary = new Vocabulary(jsonObject.get("word").getAsString(),
                                jsonObject.get("translate").getAsString());
                    }
                str = new String();
                if ( b == 123)
                    b = raf.read();
            }while (b != -1);

        }

        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
            Log.d("Log_06", "Не удалось прочитать данные из файла " + f.getName());
        }
        Log.d("Log_06", "Данные из файла " + f.getName() + " прочитанны");
        return vocabulary;
    }

    public void translate(View view) {
    try {

        Vocabulary vocabulary = getFromJson(editor.getText().toString(), filePublic);
        if (editor.getText().toString() != null) {
            trans.setText(vocabulary.getTranslated());
        } else {
            Log.d("Log_06", "Не удалось прочитать данные из файла ");
        }
    }
    catch (Exception ex)
    {
        Log.d("Log_06",ex.getMessage());
        Log.d("Log_06", "Не удалось прочитать данные из файла ");

    }

    }
    public void clean(View view)
    {
        editor.setText(" ");
        text.setText(" ");
        trans.setText(" ");
    }
}


