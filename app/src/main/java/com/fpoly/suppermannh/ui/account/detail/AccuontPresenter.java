package com.fpoly.suppermannh.ui.account.detail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccuontPresenter {
    Context  context;
    AccountContract contract;

    public AccuontPresenter(Context context, AccountContract contract) {
        this.context = context;
        this.contract = contract;
    }
    public void UpdateUser(String name,String image){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupdateuser, response -> {
             LoadUser();
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id", MainActivity.ID);
                hashMap.put("name",name);
                hashMap.put("password",MainActivity.PASSWORD);
                hashMap.put("images",image);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void LoadUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanloadupdateuser,response -> {
            int id;
            String names;
            String images;
            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        names = jsonObject.getString("name");
                        images = jsonObject.getString("images");
                        contract.ShowSuccuer(names,images);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
