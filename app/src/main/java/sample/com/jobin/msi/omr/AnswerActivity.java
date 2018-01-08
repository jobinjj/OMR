package sample.com.jobin.msi.omr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class AnswerActivity extends AppCompatActivity {
    public static String url2 = "https://techpayyans.000webhostapp.com/omr/viewanswers.php?";
    public static String url = "http://techpayyans.000webhostapp.com/omr/update.php?";

    public static NetworkInfo networkInfo;
    Button check;
    ConnectivityManager connectivityManager;
    public static ArrayList<AnswerData> AList = new ArrayList<>();
    private RecyclerView recyclerView;
    MoviesAdapter adapter2;
    RelativeLayout main;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        pref = getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();


        check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerActivity.this,FinalActivity.class);
                truncate();
                startActivity(intent);
                finish();
            }
        });
        progressBar2 = findViewById(R.id.progressBar2);
        recyclerView = (RecyclerView)findViewById(R.id.recycler2);
        main = findViewById(R.id.main);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter2 = new MoviesAdapter(AList);
        recyclerView.setAdapter(adapter2);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        viewQuestions();
//        Toast.makeText(getApplicationContext(), String.valueOf(AList.size()), Toast.LENGTH_SHORT).show();

    }

    private void truncate() {
//        if (networkInfo == null) {
//            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
//        } else {
//
//            JsonArrayRequest request = new JsonArrayRequest(url3,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject obj = response.getJSONObject(i);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                adapter2.notifyDataSetChanged();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//            AppController.getInstance().addToRequestQueue(request);
//
//        }
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        private java.util.List<AnswerData> moviesList;

        public MoviesAdapter(List<AnswerData> moviesList) {
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

            final AnswerData movie = moviesList.get(position);
//            holder.opt1.setText(movie.getOpt1());
//            holder.opt2.setText(movie.getOpt2());
//            holder.opt3.setText(movie.getOpt3());
//            holder.opt4.setText(movie.getOpt4());

            holder.img_opt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewQuestions();
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
    public static class AnswerData {
        public  String opt1,opt2,opt3,opt4;

        public AnswerData(){

        }
        public AnswerData(String opt1,String opt2,String opt3,String opt4){

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
    public void viewQuestions() {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url2,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            main.setVisibility(View.VISIBLE);
                            progressBar2.setVisibility(View.GONE);
                            AList.clear();
                            for (int i = 0; i < pref.getInt("attend",0); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    AnswerData AnswerData = new AnswerData();
                                    AnswerData.setOpt1(obj.getString("opt1"));
                                    AnswerData.setOpt2(obj.getString("opt2"));
                                    AnswerData.setOpt3(obj.getString("opt3"));
                                    AnswerData.setOpt4(obj.getString("opt4"));
                                    AList.add(AnswerData);

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
    private void updateTable(String id,String opt) {

        if (networkInfo == null) {
            Toast.makeText(getApplicationContext(), "no network connection", Toast.LENGTH_SHORT).show();
        } else {

            JsonArrayRequest request = new JsonArrayRequest(url + "aopt=" + opt + "&aid=" + id,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);

                                    Toast.makeText(AnswerActivity.this,  obj.getString("staus"), Toast.LENGTH_SHORT).show();


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
