package sample.com.jobin.msi.omr;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public static NetworkInfo networkInfo;
    EditText ed_no,ed_pm,ed_nm;
    String questions,positive_mark,negative_mark;
    RelativeLayout rl_hide;
    private SharedPreferences pref;
    Button no_ques,btn_view;
    private SharedPreferences.Editor editor;
    ConnectivityManager connectivityManager;
    public static ArrayList<Data> List = new ArrayList<>();

    public static String url = "http://techpayyans.000webhostapp.com/omr/question.php?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ed_no = (EditText) findViewById(R.id.ed_no);
        ed_pm = (EditText) findViewById(R.id.ed_pm);
        ed_nm = (EditText) findViewById(R.id.ed_nm);
        rl_hide = (RelativeLayout)findViewById(R.id.rl_hide);
        no_ques = (Button)findViewById(R.id.no_ques);
        btn_view = (Button)findViewById(R.id.btn_view);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
                startActivity(intent);
            }
        });

        no_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("first","true");
                editor.apply();
                rl_hide.setVisibility(View.VISIBLE);
                no_ques.setVisibility(View.GONE);
                btn_view.setVisibility(View.GONE);
            }
        });
        connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        pref = getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void sentQuestions(View view) {

        questions = ed_no.getText().toString();
        positive_mark = ed_pm.getText().toString();
        negative_mark = ed_nm.getText().toString();
        editor.putString("noq",questions);
        editor.putString("pm",positive_mark);
        editor.putString("nm",negative_mark);
        editor.apply();
        no_ques.setVisibility(View.VISIBLE);
        btn_view.setVisibility(View.VISIBLE);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
                startActivity(intent);
            }
        });
        rl_hide.setVisibility(View.GONE);

        if (pref.getString("first","true").equals("true")) {
            if (networkInfo == null) {
                Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
            } else {

                editor.putString("first","false");
                editor.apply();
                JsonArrayRequest request = new JsonArrayRequest(url + "question=" + questions,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                List.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject obj = response.getJSONObject(i);
                                        Data data = new Data();
                                        data.setOpt1("opt1");
                                        data.setOpt2("opt2");
                                        data.setOpt3("opt3");
                                        data.setOpt4("opt4");
                                        List.add(data);
                                        Toast.makeText(MainActivity.this, obj.getString("status"), Toast.LENGTH_SHORT).show();

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
    }



    public static class Data {
        public  String opt1,opt2,opt3,opt4;
        public Data(){

        }
        public Data(String opt1,String opt2,String opt3,String opt4){

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



}
