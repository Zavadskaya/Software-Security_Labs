package com.example.user.android_1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends Activity  implements View.OnKeyListener {
    private EditText username;
    private EditText password;
    private Button login;
    private int Attemps = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editText1);
        username.setOnKeyListener(this);
        password = (EditText) findViewById(R.id.editText2);
        password.setOnKeyListener(this);
        login = (Button) findViewById(R.id.button1);
        password.setTransformationMethod(new PasswordTransformation());
        TextView responseText = (TextView) findViewById(R.id.textView);


    }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        TextView responseText = (TextView) findViewById(R.id.textViews);
        EditText myEditText = (EditText) view;

        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.editText1:
                        responseText
                                .setText("Just pressed the ENTER key, " +
                                        "focus was on Text Box1. " +
                                        "You typed:\n" + myEditText.getText());
                        break;
                    case R.id.editText2:
                        responseText
                                .setText("Just pressed the ENTER key, " +
                                        "focus was on Text Box2. " +
                                        "You typed:\n" + myEditText.getText());
                        break;
                }
                return true;
            }

        }
        return false; // pass on to other listeners.

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

    public void login(View view) {
        if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Attemps = 0;
            Toast.makeText(getApplicationContext(), "You are welcome!",
                    LENGTH_SHORT).show();
        } else if (getCode(password.getText().toString(), "admin") == 1) {
            Attemps = 5;
            Toast.makeText(getApplicationContext(), "Number of attamps +5", LENGTH_SHORT).show();
        }
        /*else if (getCode(password.getText().toString(), "admin") == 0) {
            Attemps = 3;
            Toast.makeText(getApplicationContext(), "Number of attamps +3", LENGTH_SHORT).show();

        }
        else*/
         {
            Attemps--;
             if(Attemps == 0) {
                 Toast.makeText(getApplicationContext(), "Wrong password and login", LENGTH_SHORT).show();
                 finish();
                 login.setEnabled(false);
             }
        }
    }

    public void Clean(View view){
        Intent i = new Intent( this , this.getClass() );
        finish();
        this.startActivity(i);
    }

}