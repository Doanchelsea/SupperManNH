package com.fpoly.suppermannh.ui.online;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlinePresenter {
    Context context;
    OnlineContract contract;

    public OnlinePresenter(Context context, OnlineContract contract) {
        this.context = context;
        this.contract = contract;
    }
    public void getTime(List<Time> times){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdantimenh, response -> {
            if (response != null && response.length() !=2){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int time = jsonObject.getInt("time");
                        times.add(new Time(time));
                    }
                    contract.time();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang","1");
                return  hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
