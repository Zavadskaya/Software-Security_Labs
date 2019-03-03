package com.example.user.laba_14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

private ImageView image,image2;
private String[] mAnimTitles;
private ListView mDrawerListView;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.cat);
        image2=findViewById(R.id.cat2);

        mAnimTitles = getResources().getStringArray(R.array.anim_array);
        mDrawerListView = findViewById(R.id.left_drawer);
        mDrawerListView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mAnimTitles));
        mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());
        }


        protected void rotate() {
        Animation animation1 =
        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        image.startAnimation(animation1);
        }
        protected void zoom() {
        Animation animation =
        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        image.startAnimation(animation);
        }

        protected void fade() {
        Animation animation1 =
        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        image.startAnimation(animation1);
        }

        protected void move() {
        Animation animation1 =
        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        Animation animation2 =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate2);
        image.startAnimation(animation1);
        image2.startAnimation(animation2);
        }


        private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                rotate();
                break;
            case 1:
                zoom();
                break;
            case 2:
                fade();
                break;
            case 3:
                move();
                break;
        }
    }
}
}

