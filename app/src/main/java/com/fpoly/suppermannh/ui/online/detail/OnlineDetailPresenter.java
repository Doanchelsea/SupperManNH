package com.fpoly.suppermannh.ui.online.detail;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.PolyRetrofit;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.messing.Messing;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.Constans;
import com.fpoly.suppermannh.untils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineDetailPresenter {
    Context context;
    OnlineDetailContract contract;
    Menu menu;
    String token;
    String times;
    String dates;
    int songuoidats;

    public OnlineDetailPresenter(Context context, OnlineDetailContract contract, Menu menu) {
        this.context = context;
        this.contract = contract;
        this.menu = menu;
    }

    public void checkuser(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.checkmanageuser,response -> {

            if (response !=null && response.length() != 2){
                contract.showError(R.string.error_online_cancel);
            }else {
                getban();
            }

        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getban(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                contract.showban(jsonArray.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(menu.getIdnhahang()));
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getcheck(String time,String date,int soban, int songuoidat){
        if (StringUtils.isEmpty(time)){
            return;
        }
        if (StringUtils.isEmpty(date)){
            return;
        }
        if (songuoidat <= 0){
            return;
        }
        times = time;dates = date;songuoidats = songuoidat;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdancheckonline, response -> {
            if (response.length() !=2){
                int tong = 0;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                       int songuoi = jsonObject.getInt("songuoi");
                       if (songuoi <= 6){
                           tong = tong +1;
                       }else {
                           tong = tong + songuoi/6 + 1;
                       }
                    }

                    if (soban <= tong + (songuoidat/6)){
                        contract.showErrorOnline(R.string.error_online);
                    }else {
                        contract.showSuccess();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                if (soban <= (songuoidat/6)){
                    contract.showErrorOnline(R.string.error_online);
                }else {
                    contract.showSuccess();
                }
            }

        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(menu.getIdnhahang()));
                hashMap.put("times",time);
                hashMap.put("dates",date);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addmanage(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanaddmanage,response -> {
            postMessing(token);
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("dates",dates);
                hashMap.put("idtime",String.valueOf(menu.getIdmonan()));
                hashMap.put("images",menu.getImages());
                hashMap.put("names",menu.getNames());
                hashMap.put("prices",String.valueOf(menu.getPrices()));
                hashMap.put("statusd","online");
                hashMap.put("times",times);
                hashMap.put("discounts","15");
                hashMap.put("tables","0");
                hashMap.put("soluong","1");
                hashMap.put("idnhahang",String.valueOf(menu.getIdnhahang()));
                hashMap.put("songuoi",String.valueOf(songuoidats));
                hashMap.put("iduser",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void postMessing(String token){
        if (StringUtils.isEmpty(token)){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put(Constans.CODE, Constans.HOUSTSUCCESS);
        map.put(Constans.MESSAGE,Constans.MESSAGE101);
        map.put(Constans.NAME, MainActivity.NAME);

        Map<String,Object> map1 = new HashMap<>();
        map1.put(Constans.TO,token);
        map1.put(Constans.DATA,map);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(Constans.HTTP3),
                (new JSONObject(map1)).toString());

        PolyRetrofit.getInstance().getMesssing(Constans.KEY,body).enqueue(new Callback<Messing>() {
            @Override
            public void onResponse(Call<Messing> call, Response<Messing> response) {
                contract.showMesing();
            }

            @Override
            public void onFailure(Call<Messing> call, Throwable t) {
            }
        });
    }

    public void getToken(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.tokennh, response -> {
            if (response.length() != 2) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        token = jsonObject.getString("tokennh");
                    }
                    if (token.equals("null")){
                        contract.showError(R.string.error_maintenance);
                        return;
                    }
                    addmanage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.showError(R.string.error);
            }
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(menu.getIdnhahang()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
