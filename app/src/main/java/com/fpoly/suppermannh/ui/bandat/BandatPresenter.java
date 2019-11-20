package com.fpoly.suppermannh.ui.bandat;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Manage;
import com.fpoly.suppermannh.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BandatPresenter {
    Context context;
    BandatContract contract;

    public BandatPresenter(Context context, BandatContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getData(List<Manage> manages,String idusers){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.manageuser, response -> {
            if (response != null && response.length() !=2){
                int id;
                String date;
                int idname;
                String image;
                String name;
                int price;
                String status;
                String time;
                int discounts;
                int tables;
                String soluong;
                int idnhahang;
                String songuoi;
                int iduser;

                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            date = jsonObject.getString("dates");
                            idname = jsonObject.getInt("idtime");
                            image = jsonObject.getString("images");
                            name = jsonObject.getString("names");
                            price = jsonObject.getInt("prices");
                            status = jsonObject.getString("statusd");
                            time = jsonObject.getString("times");
                            discounts = jsonObject.getInt("discounts");
                            tables = jsonObject.getInt("tables");
                            soluong = jsonObject.getString("soluong");
                            idnhahang = jsonObject.getInt("idnhahang");
                            songuoi = jsonObject.getString("songuoi");
                            iduser = jsonObject.getInt("iduser");
                            manages.add(new Manage(id, date, idname, image, name, price, status, time, discounts, tables, soluong, idnhahang, songuoi, iduser));
                        }
                        contract.success(manages);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                contract.errornull();
            }
        },error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", idusers);
                hashMap.put("statusd","online");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getNH(int idnhahang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.loadnh,
                response -> {
                    int id;
                    String phones;
                    String name;
                    String image;
                    String lat;
                    String lng;
                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                phones = jsonObject.getString("phones");
                                image = jsonObject.getString("images");
                                name = jsonObject.getString("names");
                                lat = jsonObject.getString("lat");
                                lng = jsonObject.getString("lng");
                                contract.houst(name,lat,lng);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",String.valueOf(idnhahang));
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}
