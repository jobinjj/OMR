package sample.com.jobin.msi.omr;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class CountDownTest extends AppCompatActivity {

    EditText editText,ed2,ed3;
    String hour,minute,second;
    Button submit;
    long lhour;
    TextView text1;
    TextView txt_minutes,txt_seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_test);

        editText = findViewById(R.id.editText);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        text1 = findViewById(R.id.txt1);
        txt_minutes = findViewById(R.id.txt_minutes);
        txt_seconds = findViewById(R.id.txt_seconds);


    }

    public void getValues(View view) {
       hour =  editText.getText().toString();
       minute =  ed2.getText().toString();
       second =  ed3.getText().toString();

       if (hour.equals("")){

       }
       else {
           lhour = Long.parseLong(hour);
           lhour = lhour * 3600000;
       }

        long lminute = Long.parseLong(minute);
        long lsecond = Long.parseLong(second);


        lminute = lminute * 60000;
        lsecond = lsecond * 1000;

        new CountDownTimer(lhour, 1000) {

            public void onTick(long millisUntilFinished) {

                text1.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + " :");

            }

            public void onFinish() {
                text1.setText("00 : ");
            }
        }.start();

        new CountDownTimer(lminute, 1000) {

            public void onTick(long millisUntilFinished) {

                txt_minutes.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) + " :");


            }

            public void onFinish() {
                if (String.valueOf(lhour).equals("0")){
                    finish();
                }
                txt_minutes.setText("done!");
            }
        }.start();

        new CountDownTimer(lsecond, 1000) {

            public void onTick(long millisUntilFinished) {

                txt_seconds.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) + " seconds remaining");

            }

            public void onFinish() {
                txt_seconds.setText("done!");
            }
        }.start();
    }
}
