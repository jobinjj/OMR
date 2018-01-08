package sample.com.jobin.msi.omr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {
    public static NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    MoviesAdapter adapter2;
    CountDownTimer countDownTimer;
    long timeleftinmilli = 120000;
    private Button check;
    TextView txt,title,txt_noq_notset;
    int attend = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref2;
    String str_response;
    private SharedPreferences.Editor editor2;
    RelativeLayout main;
    public static ArrayList<QData> QList = new ArrayList<>();
    private RecyclerView recyclerView;
    ProgressBar progressBar2;
    String posA,posB,posC,posD;
    public static String url2 = "http://techpayyans.000webhostapp.com/omr/viewquestions.php?";
    public static String url = "http://techpayyans.000webhostapp.com/omr/update.php?";
    public static String url3 = "https://techpayyans.000webhostapp.com/omr/truncate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        pref = getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
        pref2 = getApplicationContext().getSharedPreferences("Mypref2",MODE_PRIVATE);
        editor = pref.edit();
        editor2 = pref2.edit();
        editor2.apply();
        editor.putString("first","true");
        editor.apply();
        progressBar2 = findViewById(R.id.progressBar2);
        main = findViewById(R.id.main);
        txt = findViewById(R.id.txt);
        txt_noq_notset = findViewById(R.id.txt_noq_notset);
        startTimer();
        check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(QuestionActivity.this,AnswerActivity.class);
//                editor.remove(posA).apply();
//                editor.remove(posB).apply();
//                editor.remove(posC).apply();
//                editor.remove(posD).apply();
                editor2.clear().apply();
                countDownTimer.cancel();
                startActivity(intent);
                finish();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        title = findViewById(R.id.title);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter2 = new MoviesAdapter(QList);
        recyclerView.setAdapter(adapter2);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        viewQuestions();
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
                Intent intent = new Intent(QuestionActivity.this,AnswerActivity.class);
                startActivity(intent);

                finish();

            }
        }.start();
    }

    private void updateCountDownText(){

        int minutes = (int) (timeleftinmilli / 1000) / 60;
        int seconds = (int) (timeleftinmilli / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        txt.setText(timeLeftFormatted);
    }
    public void viewQuestions() {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url2,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            main.setVisibility(View.VISIBLE);
                            str_response = String.valueOf(response);
                            if (str_response.equals("[]")){
                                main.setVisibility(View.GONE);
                                txt_noq_notset.setVisibility(View.VISIBLE);
                            }
                            progressBar2.setVisibility(View.GONE);
//                            txt_noq_notset.setVisibility(View.GONE);
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

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        private List<QData> moviesList;

        public MoviesAdapter(List<QData> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_layout, parent, false);

            return new MoviesAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MoviesAdapter.MyViewHolder holder, final int position) {

            final QData movie = moviesList.get(position);

//            holder.opt2.setText(movie.getOpt2());
//            holder.opt3.setText(movie.getOpt3());
//            holder.opt4.setText(movie.getOpt4());

            holder.img_opt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewQuestions();

                    posA = "posA" + String.valueOf(position);
                    posB = "posB" + String.valueOf(position);
                    posC = "posC" + String.valueOf(position);
                    posD = "posD" + String.valueOf(position);
                    if (pref2.getString(posA,"true").equals("true")){
                        editor2.putString(posA,"false");
                        editor2.putString(posB,"false");
                        editor2.putString(posC,"false");
                        editor2.putString(posD,"false");
                        editor2.apply();
                        attend = attend + 1;
                        editor.putInt("attend",attend);
                        editor.apply();

                    }
                    updateTable(String.valueOf(position + 1),"opt1");
                    holder.img_a.setImageResource(R.drawable.fill);
                    holder.img_b.setImageResource(R.drawable.border);
                    holder.img_c.setImageResource(R.drawable.border);
                    holder.img_d.setImageResource(R.drawable.border);
                    holder.opt1.setTextColor(getResources().getColor(R.color.white));
                    holder.opt2.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt3.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt4.setTextColor(getResources().getColor(R.color.blue));

                }
            });
            holder.img_opt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewQuestions();
                    posA = "posA" + String.valueOf(position);
                    posB = "posB" + String.valueOf(position);
                    posC = "posC" + String.valueOf(position);
                    posD = "posD" + String.valueOf(position);
                    if (pref2.getString(posB,"true").equals("true")){
                        editor2.putString(posA,"false");
                        editor2.putString(posB,"false");
                        editor2.putString(posC,"false");
                        editor2.putString(posD,"false");
                        editor2.apply();
                        attend = attend + 1;
                        editor.putInt("attend",attend);
                        editor.apply();
                    }
                    updateTable(String.valueOf(position + 1),"opt2");
                    holder.img_b.setImageResource(R.drawable.fill);
                    holder.img_a.setImageResource(R.drawable.border);
                    holder.img_c.setImageResource(R.drawable.border);
                    holder.img_d.setImageResource(R.drawable.border);
                    holder.opt2.setTextColor(getResources().getColor(R.color.white));
                    holder.opt1.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt3.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt4.setTextColor(getResources().getColor(R.color.blue));
                }
            });
            holder.img_opt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewQuestions();
                    posA = "posA" + String.valueOf(position);
                    posB = "posB" + String.valueOf(position);
                    posC = "posC" + String.valueOf(position);
                    posD = "posD" + String.valueOf(position);
                    if (pref2.getString(posC,"true").equals("true")){
                        editor2.putString(posA,"false");
                        editor2.putString(posB,"false");
                        editor2.putString(posC,"false");
                        editor2.putString(posD,"false");
                        editor2.apply();
                        attend = attend + 1;
                        editor.putInt("attend",attend);
                        editor.apply();
                    }
                    updateTable(String.valueOf(position + 1),"opt3");
                    holder.img_c.setImageResource(R.drawable.fill);
                    holder.img_a.setImageResource(R.drawable.border);
                    holder.img_b.setImageResource(R.drawable.border);
                    holder.img_d.setImageResource(R.drawable.border);
                    holder.opt3.setTextColor(getResources().getColor(R.color.white));
                    holder.opt1.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt2.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt4.setTextColor(getResources().getColor(R.color.blue));
                }
            });
            holder.img_opt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewQuestions();
                    posA = "posA" + String.valueOf(position);
                    posB = "posB" + String.valueOf(position);
                    posC = "posC" + String.valueOf(position);
                    posD = "posD" + String.valueOf(position);
                    if (pref2.getString(posD,"true").equals("true")){
                        editor2.putString(posA,"false");
                        editor2.putString(posB,"false");
                        editor2.putString(posC,"false");
                        editor2.putString(posD,"false");
                        editor2.apply();
                        attend = attend + 1;
                        editor.putInt("attend",attend);
                        editor.apply();
                    }
                    updateTable(String.valueOf(position + 1),"opt4");
                    holder.img_d.setImageResource(R.drawable.fill);
                    holder.img_a.setImageResource(R.drawable.border);
                    holder.img_b.setImageResource(R.drawable.border);
                    holder.img_c.setImageResource(R.drawable.border);
                    holder.opt4.setTextColor(getResources().getColor(R.color.white));
                    holder.opt1.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt2.setTextColor(getResources().getColor(R.color.blue));
                    holder.opt3.setTextColor(getResources().getColor(R.color.blue));
                }
            });

        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView opt1,opt2,opt3,opt4;
            private RelativeLayout img_opt1,img_opt2,img_opt3,img_opt4;
            private ImageView img_a,img_b,img_c,img_d;


            public MyViewHolder(View view) {
                super(view);
                opt1 = view.findViewById(R.id.opt1);
                opt2 = view.findViewById(R.id.opt2);
                opt3 = view.findViewById(R.id.opt3);
                opt4 = view.findViewById(R.id.opt4);
                title = view.findViewById(R.id.title);
                img_opt1 = view.findViewById(R.id.img_opt1);
                img_opt2 = view.findViewById(R.id.img_opt2);
                img_opt3 = view.findViewById(R.id.img_opt3);
                img_opt4 = view.findViewById(R.id.img_opt4);
                img_a = view.findViewById(R.id.img_a);
                img_b = view.findViewById(R.id.img_b);
                img_c = view.findViewById(R.id.img_c);
                img_d = view.findViewById(R.id.img_d);
            }
        }
    }

    private void updateTable(String id,String opt) {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url + "qopt=" + opt + "&qid=" + id,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);

                                    Toast.makeText(QuestionActivity.this,  obj.getString("staus"), Toast.LENGTH_SHORT).show();


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

    public static class QData {

        public  String opt1,opt2,opt3,opt4;
        public QData(){

        }
        public QData(String opt1,String opt2,String opt3,String opt4){

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
    public void onBackPressed() {
        if (str_response.equals("[]")){
            countDownTimer.cancel();
            QuestionActivity.super.onBackPressed();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit examination?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            countDownTimer.cancel();
                            truncate();
                            QuestionActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
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
        }
