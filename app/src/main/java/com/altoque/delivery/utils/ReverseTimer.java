package com.altoque.delivery.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class ReverseTimer {
    public ReverseTimer() {
    }

    public void reverseTimer(int Seconds,final TextView tv){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText("Tiempo para intentar nuevamente: " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }


            public void onFinish() {
                tv.setVisibility(View.INVISIBLE);

            }
        }.start();
    }
}
