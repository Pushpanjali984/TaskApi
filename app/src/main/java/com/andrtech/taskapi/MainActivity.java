package com.andrtech.taskapi;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;

Button nextAct;
ArrayList<UserData> details;
    MyAdapter myAdapter;
    LinearLayoutManager linearLayoutManager;
RequestQueue requestQueue;
    String s,UrlDel;

public static final String URL="http://www.kritikaassociates.com/app/getUserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler1);
        nextAct=findViewById(R.id.nextAct);
        myAdapter = new MyAdapter(details,this);
        details = new ArrayList<UserData>();
        requestQueue = Volley.newRequestQueue(this);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        jsonParse();
        myAdapter.notifyDataSetChanged();



        nextAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(in);
            }
        });

    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());

    }*/

    public void jsonParse() {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


               /* try {
                    usr = response.getJSONArray("userData");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
              try{
                  JSONArray usr = response.getJSONArray("userData");
                  for (int i = 0; i < usr.length(); i++) {

                    JSONObject student = usr.getJSONObject(i);
                   String image=student.getString("image");
                    String id = student.getString("id");
                    String name = student.getString("name");
                    String city = student.getString("city");
                    String phone = student.getString("phone");
                    UserData det=new UserData(image,city,phone,name,id);
                    details.add(det);

                      myAdapter.notifyDataSetChanged();


                }
            }

              catch (JSONException e) {
                  e.printStackTrace();
              }
              }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });
        requestQueue.add(jsonObjectRequest);

        }



            public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
                private int selectedPosition = -1;
                private List<UserData>list_data;
                private Context context;

                public MyAdapter(List<UserData> list_data, Context context) {
                    this.list_data = list_data;
                    this.context = context;
                }

                @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v = inflater.inflate(R.layout.row,
                    parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final UserData obj =details.get(position);

            Picasso.with(context).load("http://i.imgur.com/7spzG.png").into(holder.img);
            holder.tv1.setText(obj.getId());
            holder.tv2.setText(obj.getName());
            holder.tv3.setText(obj.getCity());
            holder.tv4.setText(obj.getPhone());


                     holder.del.setVisibility(View.VISIBLE);
                     holder.del.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             s = obj.getId();

                             UrlDel = "http://www.kritikaassociates.com/app/deleteUserData/"+s;

                             JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, UrlDel, null, new Response.Listener<JSONObject>() {
                                 @Override
                                 public void onResponse(JSONObject response) {

                                     try {
                                         String status=response.getString("api_status");
                                         if(status.equals("success")){

                                             Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                            /* finish();
                                             startActivity(getIntent());*/
                                             details.remove(position);
                                             recyclerView.removeViewAt(position);
                                             myAdapter.notifyItemRemoved(position);
                                             myAdapter.notifyItemRangeChanged(position, details.size());



                                         }
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {

                                 }
                             });
                             requestQueue.add(jsonObjectRequest1);
                         }});



               /* ImageRequest request = new ImageRequest(image,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            img.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
// Access the RequestQueue through your singleton class.
                requestQueue.add(request);*/


                }

        @Override
        public int getItemCount() {
            return details.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv1, tv2, tv3, tv4;

ImageView del,img;

            public ViewHolder(View itemView) {
                super(itemView);
                img=itemView.findViewById(R.id.imageView);

                tv1 = itemView.findViewById(R.id.userid);
                tv2 = itemView.findViewById(R.id.username);
                tv3 = itemView.findViewById(R.id.usercity);
                tv4 = itemView.findViewById(R.id.userphone);
                del=itemView.findViewById(R.id.del);

            }
        }
    }
}

