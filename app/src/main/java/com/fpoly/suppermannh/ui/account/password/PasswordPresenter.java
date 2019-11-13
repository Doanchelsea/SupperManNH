package com.fpoly.suppermannh.ui.account.password;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class PasswordPresenter {
    Context context;
    PasswordContract contract;

    public PasswordPresenter(Context context, PasswordContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void ChangePassword(String password){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupdateuser, response -> {
            contract.ShowSuccer();
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id", MainActivity.ID);
                hashMap.put("name",MainActivity.NAME);
                hashMap.put("images","");
                hashMap.put("password",password);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
