package com.fpoly.suppermannh.ui.login;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.local.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter  {
    Context context;
    LoginContract contract;
    DataManager manager;

    public LoginPresenter(Context context, LoginContract contract, DataManager manager) {
        this.context = context;
        this.contract = contract;
        this.manager = manager;
    }


    public void login(String taikhoan, String matkhau){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanloginuser, response -> {
            int id;
            String username;
            String password;
            String name;
            String phone;
            String images;

            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        username = jsonObject.getString("username");
                        password = jsonObject.getString("password");
                        name = jsonObject.getString("name");
                        phone = jsonObject.getString("phone");
                        images = jsonObject.getString("images");
                        if (taikhoan.equals(username) && matkhau.equals(password)){
                            manager.updateUserInfoSharedPreference(String.valueOf(id),name,images,username,password,phone,true);
                            contract.ShowSuccer();
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                contract.ShowError(R.string.error_login);
            }
        },error -> {
            contract.ShowError(R.string.error);
        });
        requestQueue.add(stringRequest);
    }
}
