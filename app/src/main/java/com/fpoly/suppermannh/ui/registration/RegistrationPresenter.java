package com.fpoly.suppermannh.ui.registration;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationPresenter {
    Context context;
    RegistrationContract contract;

    public RegistrationPresenter(Context context, RegistrationContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void checkuser(String name,String phone,String password){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanloginuser, response -> {
            String username;
            String phones;

            if (response != null && response.length() !=2) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        username = jsonObject.getString("username");
                        phones = jsonObject.getString("phone");
                        if (name.equals(username) || phone.equals(phones)){
                            contract.error(R.string.error_coincide);
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postLogin(name,phone,password);
            }
        },error -> {
            contract.error(R.string.error);
        });
        requestQueue.add(stringRequest);
    }
    private void postLogin(String name,String phone,String password){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.registration,response -> {
            contract.success();
        },error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("phone",phone);
                hashMap.put("username",name);
                hashMap.put("password",password);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
