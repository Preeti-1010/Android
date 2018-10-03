package com.example.preet.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Main extends AppCompatActivity {

    private ImageView zoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView zoom = (ImageView) findViewById(R.id.imageView);
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        zoom.startAnimation(zoomAnimation);


        Thread timeThread=new Thread()
        {
            @Override
            public void run() {
                try{
                    sleep(3000);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {


                    Intent intent=new Intent(Main.this,Login.class);
                    startActivity(intent);
                }
            }
        };
        timeThread.start() ;

    }
}
