package com.appodeal.support.test;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String APP_KEY = "9c85d2cfc5bf39fb654168042c0b44febcf500b36d60d4bc";
    private Context context;
    private Chronometer chronometer;
    private Button button;
    private TextView text;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private boolean isCheck = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.start_button);
        text = findViewById(R.id.time_value);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AdvertisingAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Appodeal.setTesting(true);
        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, APP_KEY, Appodeal.BANNER_TOP);
        Appodeal.initialize(this, APP_KEY, Appodeal.INTERSTITIAL);
        Appodeal.show(this, Appodeal.BANNER_TOP);


        chronometer = findViewById(R.id.chronometer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            chronometer.setCountDown(true);
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * 30);
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * 30);
        }
        chronometer.setOnChronometerTickListener(chronometer -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                int elapsedMillis = (int) (SystemClock.elapsedRealtime() - chronometer.getBase() );
                if (elapsedMillis > 0) {
                    chronometer.stop();
                    if (isCheck) {
                        Appodeal.show(this, Appodeal.INTERSTITIAL);
                        button.setEnabled(false);
                    }
                }
            } else {
                int elapsedMillis = (int) (SystemClock.elapsedRealtime() - chronometer.getBase() );
                long x = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);
                int seconds = (int) Math.abs(x);
                chronometer.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                text.setText(String.valueOf(seconds));
                if (seconds == 0) {
                    text.setText("0");
                    chronometer.stop();
                    if (isCheck) {
                        Appodeal.show(this, Appodeal.INTERSTITIAL);
                        button.setEnabled(false);
                    }
                }
                System.out.println(seconds);
            }

        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Appodeal.hide((Activity) context, Appodeal.BANNER_TOP);
            }
        };

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                Log.d("Appodeal", "onBannerLoaded");

            }

            @Override
            public void onBannerFailedToLoad() {
                Log.d("Appodeal", "onBannerFailedToLoad");
            }

            @Override
            public void onBannerShown() {
                Log.d("Appodeal", "onBannerShown");
                timer.schedule(timerTask, 5000);
            }

            @Override
            public void onBannerClicked() {
                Log.d("Appodeal", "onBannerClicked");
            }
        });

        Appodeal.initialize(this, APP_KEY, Appodeal.INTERSTITIAL);

        button.setOnClickListener(
                v -> {
                    isCheck = false;
                    Toast.makeText(this, "Вы отключили показ рекламы", Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                    button.setEnabled(false);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheck){
            chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * 30);
            chronometer.start();
        }
        Log.d("Appodeal", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        chronometer.stop();
        Log.d("Appodeal", "onStop");
    }

}
