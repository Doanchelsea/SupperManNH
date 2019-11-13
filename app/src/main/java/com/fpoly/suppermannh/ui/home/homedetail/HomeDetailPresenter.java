package com.fpoly.suppermannh.ui.home.homedetail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeDetailPresenter {
    Context context;
    HomeDetailContract contract;
    Houst houst;

    public HomeDetailPresenter(Context context, HomeDetailContract contract, Houst houst) {
        this.context = context;
        this.contract = contract;
        this.houst = houst;
    }

    public void love(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanaddlove, response -> {
            contract.showSuccess();
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", MainActivity.ID);
                if (houst.getId() != 0){
                    hashMap.put("idnhahang",String.valueOf(houst.getId()));
                }else {
                    hashMap.put("idnhahang",String.valueOf(houst.getIdnhahang()));
                }
                hashMap.put("namenh",String.valueOf(houst.getNames()));
                hashMap.put("address",String.valueOf(houst.getAddress()));
                hashMap.put("images",String.valueOf(houst.getImages()));
                hashMap.put("lat",String.valueOf(houst.getLat()));
                hashMap.put("lng",String.valueOf(houst.getLng()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void delete(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandeletelove, response -> {
            contract.showDelete();
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", MainActivity.ID);
                if (houst.getId() != 0){
                    hashMap.put("idnhahang",String.valueOf(houst.getId()));
                }else {
                    hashMap.put("idnhahang",String.valueOf(houst.getIdnhahang()));
                }
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void checklove(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanchecklove,
                response -> {
            if (response.length() !=2 && response != null){
                contract.showSuccess();
            }else {
                contract.showDelete();
            }
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", MainActivity.ID);
                if (houst.getId() != 0){
                    hashMap.put("idnhahang",String.valueOf(houst.getId()));
                }else {
                    hashMap.put("idnhahang",String.valueOf(houst.getIdnhahang()));
                }
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void ratting(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanratting,
                response -> {
                    double ratting;
                    double count = 0;
                    if (response != null &&  response.length() != 2){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ratting = jsonObject.getDouble("ratting");
                                count = count + ratting;
                            }
                            contract.showCountRatting(count/(jsonArray.length()),jsonArray.length());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        contract.showCountRatting(0.0,0);
                    }
                },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", MainActivity.ID);
                if (houst.getId() != 0){
                    hashMap.put("idnhahang",String.valueOf(houst.getId()));
                }else {
                    hashMap.put("idnhahang",String.valueOf(houst.getIdnhahang()));
                }
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
