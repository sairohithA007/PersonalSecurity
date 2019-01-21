package com.example.security;



import android.app.Activity;	
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.*;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class SplashActivity extends Activity //ActionBarActivity 
 implements AnimationListener
{
	ImageView imageAnimation;
	Animation anim;

	//GifView gifView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imageAnimation = (ImageView) findViewById(R.id.animation_image_1);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.layout.animation_rotate);
		anim.setAnimationListener(this);
		imageAnimation.setVisibility(View.VISIBLE);
		imageAnimation.startAnimation(anim);

        // gifView = (GifView) findViewById(R.id.gif_view);
        
        //startService(new Intent(SplashActivity.this, MainActivity.class));
// METHOD 1     
         
         /****** Create Thread that will sleep for 5 seconds *************/        
        Thread background = new Thread() {
            public void run() {
                 
                try {
                    // Thread will sleep for 5 seconds
                    sleep(7*1000);
                     
                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                     
                    //Remove activity
                    finish();
                     
                } catch (Exception e) {
                 
                }
            }
        };
         
        // start thread
        background.start();
         
//METHOD 2  
         
        /*
        new Handler().postDelayed(new Runnable() {
              
            // Using handler with postDelayed called runnable run method
  
            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);
  
                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
        */
    }
     
    @Override
    protected void onDestroy() {
         
        super.onDestroy();
         
    }

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}

