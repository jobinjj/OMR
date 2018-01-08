package sample.com.jobin.msi.omr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FinalActivity extends AppCompatActivity {
    public ArrayList<AnswerData> AList = new ArrayList<>();
    public ArrayList<QData> QList = new ArrayList<>();
    public static String url1 = "https://techpayyans.000webhostapp.com/omr/viewanswers.php?";
    public static String url2 = "http://techpayyans.000webhostapp.com/omr/viewquestions.php?";
    public static NetworkInfo networkInfo;
    RelativeLayout rl_progress, rl_result;
    TextView tq, tm, txt_correct, txt_wrong, txt_attend;
    ConnectivityManager connectivityManager;
    private SharedPreferences pref;
    int i = 0;
    public int total = 0;
    public static String url3 = "https://techpayyans.000webhostapp.com/omr/truncate.php";
    public int noq = 0;
    public int negative;
    Button back;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        pref = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();

        tq = findViewById(R.id.tq);
        txt_attend = findViewById(R.id.txt_attend);
        txt_correct = findViewById(R.id.txt_correct);
        txt_wrong = findViewById(R.id.txt_wrong);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                truncate();
                finish();
            }
        });
        tm = findViewById(R.id.tm);
        rl_progress = findViewById(R.id.rl_progress);
        rl_result = findViewById(R.id.rl_result);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url1,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    AnswerData AnswerData = new AnswerData();
                                    AnswerData.setOpt1(obj.getString("opt1"));
                                    AnswerData.setOpt2(obj.getString("opt2"));
                                    AnswerData.setOpt3(obj.getString("opt3"));
                                    AnswerData.setOpt4(obj.getString("opt4"));
                                    AList.add(AnswerData);


                                    viewQuestions();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(request);

        }
        checkAnswer();

    }

    private void truncate() {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url3,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(request);

        }
    }


    public void viewQuestions() {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url2,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            QList.clear();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    QData QData = new QData();
                                    QData.setOpt1(obj.getString("opt1"));
                                    QData.setOpt2(obj.getString("opt2"));
                                    QData.setOpt3(obj.getString("opt3"));
                                    QData.setOpt4(obj.getString("opt4"));
                                    QList.add(QData);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            AppController.getInstance().addToRequestQueue(request);

        }
    }

    private void checkAnswer() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          rl_progress.setVisibility(View.GONE);
                                          rl_result.setVisibility(View.VISIBLE);

                                          tq.setText(pref.getString("noq", "10"));
                                          int limit = Integer.parseInt(pref.getString("noq", "10"));
                                          int pm = Integer.parseInt(pref.getString("pm", "10"));
                                          int nm = Integer.parseInt(pref.getString("nm", "10"));
                                          while (i < limit) {
//              Toast.makeText(FinalActivity.this, String.valueOf(AList.size()), Toast.LENGTH_SHORT).show();
//              Toast.makeText(FinalActivity.this, String.valueOf(QList.size()), Toast.LENGTH_SHORT).show();
                                              QData list1 = QList.get(i);
                                              AnswerData list2 = AList.get(i);
                                              String qopt1 = list1.getOpt1();
                                              String aopt1 = list2.getOpt1();
                                              String qopt2 = list1.getOpt2();
                                              String aopt2 = list2.getOpt2();
                                              String qopt3 = list1.getOpt3();
                                              String aopt3 = list2.getOpt3();
                                              String qopt4 = list1.getOpt4();
                                              String aopt4 = list2.getOpt4();

                                              if (qopt1.equals("select") && aopt1.equals("select")) {
                                                  total = total + pm;
                                                  noq = noq + 1;
                                              }
                                              if (qopt2.equals("select") && aopt2.equals("select")) {
                                                  total = total + pm;
                                                  noq = noq + 1;
                                              }
                                              if (qopt3.equals("select") && aopt3.equals("select")) {
                                                  total = total + pm;
                                                  noq = noq + 1;
                                              }
                                              if (qopt4.equals("select") && aopt4.equals("select")) {
                                                  total = total + pm;
                                                  noq = noq + 1;
                                              }

                                              i = i + 1;
//                     if (first.equals(second)){
//                         total = total + 1;
//                         Toast.makeText(FinalActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//
//                     }
//
//                      if (list1.getOpt2().equals(list2.getOpt2())){
//                          Toast.makeText(FinalActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//                         total = total + 1;
//
//                     }if (list1.getOpt3().equals(list2.getOpt3())){
//                        Toast.makeText(FinalActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//                         total = total + 1;
//
//                     }if (list1.getOpt4().equals(list2.getOpt4())){
//                        Toast.makeText(FinalActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//                         total = total + 1;
//
//                     }

                                          }
                                          int wq = limit - Integer.parseInt(pref.getString("noq", "10"));
                                          negative = wq * nm;
                                          total = total - negative;

                                          tq.setText(pref.getString("noq", "10"));
                                          txt_correct.setText(String.valueOf(noq));
                                          String str_wrong = String.valueOf(pref.getInt("attend", 1) - noq);
                                          txt_wrong.setText(str_wrong);
                                          txt_attend.setText(String.valueOf(pref.getInt("attend", 1)));
                                          editor.putInt("attend", 0);
//                                          total = pref.getInt("attend", 1) - Integer.parseInt(str_wrong) * Integer.parseInt(pref.getString("nm","2"));
                                          total= total - Integer.parseInt(str_wrong) *  Integer.parseInt(pref.getString("nm","negative"));
                                          tm.setText(String.valueOf(total));

//
//                Toast.makeText(FinalActivity.this, String.valueOf(total), Toast.LENGTH_SHORT).show();
//
                                      }
                                  },
                4000);
    }

    public static class AnswerData {
        public String opt1, opt2, opt3, opt4;

        public AnswerData() {

        }

        public AnswerData(String opt1, String opt2, String opt3, String opt4) {

        }

        public String getOpt1() {
            return opt1;
        }

        public void setOpt1(String opt1) {
            this.opt1 = opt1;
        }

        public String getOpt2() {
            return opt2;
        }

        public void setOpt2(String opt2) {
            this.opt2 = opt2;
        }

        public String getOpt3() {
            return opt3;
        }

        public void setOpt3(String opt3) {
            this.opt3 = opt3;
        }

        public String getOpt4() {
            return opt4;
        }

        public void setOpt4(String opt4) {
            this.opt4 = opt4;
        }
    }

    public static class QData {
        public String opt1, opt2, opt3, opt4;

        public QData() {

        }

        public QData(String opt1, String opt2, String opt3, String opt4) {

        }

        public String getOpt1() {
            return opt1;
        }

        public void setOpt1(String opt1) {
            this.opt1 = opt1;
        }

        public String getOpt2() {
            return opt2;
        }

        public void setOpt2(String opt2) {
            this.opt2 = opt2;
        }

        public String getOpt3() {
            return opt3;
        }

        public void setOpt3(String opt3) {
            this.opt3 = opt3;
        }

        public String getOpt4() {
            return opt4;
        }

        public void setOpt4(String opt4) {
            this.opt4 = opt4;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        total = 0;
    }

    @Override
    public void onBackPressed() {
        truncate();
        total = 0;
        FinalActivity.super.onBackPressed();

    }
}
