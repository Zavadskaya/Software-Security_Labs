package com.example.user.android_4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends Activity {

    private ObjectAnimator fadeIn1;
    private ObjectAnimator fadeIn2;
    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    private ObjectAnimator fadeOut1;
    private ObjectAnimator fadeOut2;
    private Button syncAnim;
    private Button asyncAnim;
    private Button syncAlpha;
    private Button asyncAlpha;
    private EditText duration,delay;
    private AnimatorSet set;
    private ImageView img1,img2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init()
    {
        syncAnim=(Button)findViewById(R.id.sync);
        asyncAnim=(Button)findViewById(R.id.async);
        syncAlpha=(Button)findViewById(R.id.syncAlpha);
        asyncAlpha=(Button)findViewById(R.id.asyncAlpha);
        img1=(ImageView)findViewById(R.id.imageView1);
        img2=(ImageView)findViewById(R.id.imageView2);
        duration=(EditText) findViewById(R.id.duration);
        delay=(EditText)findViewById(R.id.dela);
    }
    public int getDuration()
    {
        if(duration == null)
        {
            Toast.makeText(getApplicationContext(),"Please,enter the duration",Toast.LENGTH_LONG).show();
        }
        else {
            String time = duration.getText().toString();
            Integer number = Integer.parseInt(time);
            return number;
        }
        return 0;
    }
    public int getDelay()
    {
        if(delay == null)
        {
            Toast.makeText(getApplicationContext(),"Please,enter the delay",Toast.LENGTH_LONG).show();
        }
        else {
            String time = duration.getText().toString();
            Integer number = Integer.parseInt(time);
            return number;
        }
        return 0;
    }

    public void startSyncRotate(View view)
    {
        try {
            int durations = getDuration();
            animation1 = ObjectAnimator.ofFloat(img1, View.ROTATION, 0f, 360f);
            animation1.setDuration(durations);

            animation2 = ObjectAnimator.ofFloat(img2, View.ROTATION, 0f, 360f);
            animation2.setDuration(durations);

            set = new AnimatorSet();
            set.playTogether(animation1, animation2);
            set.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }
    public void startAsyncRotate(View view)
    {
        try {
            int delay = getDelay();
            int durations = getDuration();
            animation1 = ObjectAnimator.ofFloat(img1, View.ROTATION, 0f, 360f);
            animation1.setDuration(durations);
            animation1.start();

            animation2 = ObjectAnimator.ofFloat(img2, View.ROTATION, 0f, 360f);
            animation2.setDuration(durations);
            animation2.setStartDelay(delay);
            animation2.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }
    public void startSyncAlpha(View view) {
        try {
            int durations = getDuration();
            int delays = getDelay();
            fadeIn1 = ObjectAnimator.ofFloat(img1, View.ALPHA, 1.0f, 0f)
                    .setDuration(durations);
            fadeOut1 = ObjectAnimator.ofFloat(img1, View.ALPHA,0f,1.0f)
                    .setDuration(durations);
            fadeIn2 = ObjectAnimator.ofFloat(img2, View.ALPHA, 1.0f, 0f)
                    .setDuration(durations);
            fadeOut2 = ObjectAnimator.ofFloat(img2, View.ALPHA,0f,1.0f)
                    .setDuration(durations);

            set = new AnimatorSet();
            set.playTogether(fadeIn1, fadeIn2);
            set.setStartDelay(delays);
            set.playTogether(fadeOut1,fadeOut2);
            set.start();
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }
    public void startAsyncAlpha(View view)
    {
        try {
            int durations = getDuration();
            int delays = getDelay();
            fadeIn1 = ObjectAnimator.ofFloat(img1, View.ALPHA, 1.0f, 0f)
                    .setDuration(durations);
            fadeOut1 = ObjectAnimator.ofFloat(img1, View.ALPHA,0f,1.0f)
                    .setDuration(durations);
            set = new AnimatorSet();
            set.playTogether(fadeIn1,fadeOut1);
            set.start();
            fadeIn2 = ObjectAnimator.ofFloat(img2, View.ALPHA, 1.0f, 0f)
                    .setDuration(durations);
            fadeOut2 = ObjectAnimator.ofFloat(img2, View.ALPHA,0f,1.0f)
                    .setDuration(durations);
            set = new AnimatorSet();
            set.playTogether(fadeIn2,fadeOut2);
            set.setStartDelay(delays);
            set.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }

    }
}