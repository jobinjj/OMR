package sample.com.jobin.msi.omr;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TestActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    long timeleftinmilli = 120000;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        txt = findViewById(R.id.txt);
        
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeleftinmilli,1000) {
            @Override
            public void onTick(long l) {
                timeleftinmilli = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Toast.makeText(TestActivity.this, "finished" , Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void updateCountDownText(){

        int minutes = (int) (timeleftinmilli / 1000) / 60;
        int seconds = (int) (timeleftinmilli / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",minutes,minutes,seconds);

        txt.setText(timeLeftFormatted);
    }

}
