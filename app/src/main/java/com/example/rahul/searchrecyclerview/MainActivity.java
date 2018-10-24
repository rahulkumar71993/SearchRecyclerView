package com.example.rahul.searchrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editTextSearch;
    List<Dat> names;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = new ArrayList<>();

        fetchData();

//        names.add("Ramiz");
//        names.add("Belal");
//        names.add("Azad");
//        names.add("Manish");
//        names.add("Sunny");
//        names.add("Shahid");
//        names.add("Deepak");
//        names.add("Deepika");
//        names.add("Sumit");
//        names.add("Mehtab");
//        names.add("Vivek");
//

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomAdapter(MainActivity.this,names);

        recyclerView.setAdapter(adapter);


        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                //    filter(editable.toString());
                filter(editable.toString());

            }
        });


    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Dat> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Dat row : names) {
            //if the existing elements contains the search input
            if (row.getName().toLowerCase().contains(text.toLowerCase()) || row.getPhone().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(row);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }


    private void fetchData() {
        java.lang.String url_fetch = "https://api.androidhive.info/json/contacts.json";
        StringRequest stringreq = new StringRequest(url_fetch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject parm = jsonArray.getJSONObject(i);

                        Dat dat = new Dat();
                        dat.setName(parm.getString("name"));
                        dat.setImg(parm.getString("image"));
                        dat.setPhone(parm.getString("phone"));
                        names.add(dat);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(stringreq);
    }


}