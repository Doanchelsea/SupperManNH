package com.fpoly.suppermannh.ui.login;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.PolyRetrofit;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.model.messing.Messing;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.Constans;
import com.fpoly.suppermannh.untils.StringUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanloginuserpost, response -> {
            int id;
            String username;
            String password;
            String name;
            String phone;
            String images;

            if (response != null && response.length() != 2) {
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
                        setToken(id);
                        manager.updateUserInfoSharedPreference(String.valueOf(id),name,images,username,password,phone,true);
                        contract.ShowSuccer();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.ShowError(R.string.error_login);
            }
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("username",taikhoan);
                hashMap.put("password",matkhau);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void setToken(int iduser){
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (task != null){
                           String token = task.getResult().getToken();
                            updateToken(token,iduser);
                        }
                    })
                    .addOnFailureListener(e -> {

                    });
        }


    private void updateToken(String token,int idnhahang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanupdatetoken,response -> {
            manager.setToken(token);
        },error -> {
            contract.ShowError(R.string.error);
            return;
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser",String.valueOf(idnhahang));
                hashMap.put("tokenuser",token);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
