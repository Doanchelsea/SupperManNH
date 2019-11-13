package com.fpoly.suppermannh.ui.like;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Houst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikePresenter {
    Context context;
    LikeContract contract;

    public LikePresenter(Context context, LikeContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getData(List<Houst> likes, String idlogin){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanlove, response -> {
            int id;
            int iduser;
            int idnhahang;
            String namenh;
            double lat;
            double lng;
            String images;
            String address;

            if (response != null &&  response.length() != 2){
                likes.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        iduser =jsonObject.getInt("iduser");
                        idnhahang =jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        lat = jsonObject.getDouble("lat");
                        lng = jsonObject.getDouble("lng");
                        images = jsonObject.getString("images");
                        address = jsonObject.getString("address");
                        likes.add(new Houst(iduser,idnhahang,namenh,lat,lng,images,address));
                    }
                    contract.showSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.showNull();
            }
        },error -> {
            contract.showError(R.string.error);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                Log.d("adadadad",idlogin);
                hashMap.put("iduser",idlogin);
                return hashMap;

            }
        };
        requestQueue.add(stringRequest);
    }
}
