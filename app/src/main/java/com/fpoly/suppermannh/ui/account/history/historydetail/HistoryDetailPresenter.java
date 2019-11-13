package com.fpoly.suppermannh.ui.account.history.historydetail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Pay;
import com.fpoly.suppermannh.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class HistoryDetailPresenter {

    Context context;
    HistoryDetailContract contract;

    public HistoryDetailPresenter(Context context, HistoryDetailContract contract) {
        this.context = context;
        this.contract = contract;
    }
    public void getPay(List<Pay> pays, String idtrangthai){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanpayhistory, response -> {
            int id;
            String namemonan;
            String images;
            int price;
            int banan;
            int discounts;
            int soluong;
            int iduser;
            if (response != null) {
                pays.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        namemonan = jsonObject.getString("namemonan");
                        images = jsonObject.getString("images");
                        price = jsonObject.getInt("price");
                        banan = jsonObject.getInt("banan");
                        discounts = jsonObject.getInt("discounts");
                        soluong = jsonObject.getInt("soluong");
                        iduser = jsonObject.getInt("iduser");
                        pays.add(new Pay(id,namemonan,images,price,banan,discounts,soluong,iduser));
                    }
                    contract.showSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },error -> {
            contract.showErorr(R.string.error);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idtrangthai",idtrangthai);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getRatting(int ratting,String comment, int idnhahang,int idhistory){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanaddratting,response -> {
            contract.showRatting();
        },error -> {
            contract.showErorr(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("ratting",String.valueOf(ratting));
                hashMap.put("idnhahang",String.valueOf(idnhahang));
                hashMap.put("comment",comment);
                hashMap.put("iduser", MainActivity.ID);
                hashMap.put("nameuser", MainActivity.NAME);
                hashMap.put("imageuser",MainActivity.IMAGES);
                hashMap.put("idhistory",String.valueOf(idhistory));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getButton(int idhistory){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanbuttonratting,response -> {
            if (response != null && response.length() != 2){

            }else {
                contract.showButton();
            }
        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idhistory",String.valueOf(idhistory));
                hashMap.put("iduser",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
