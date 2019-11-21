package com.fpoly.suppermannh.ui.home.homedetail.viewpager.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.contract.AccountContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccoutDetailPresenter {
    AccountContract contract;
    Context context;

    public AccoutDetailPresenter(AccountContract contract, Context context) {
        this.contract = contract;
        this.context = context;
    }

    public void getData(List<Menu> list, int idmo , int idnh){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
                int id;
                int idmonan;
                String dates;
                String descriptions;
                String images;
                String names;
                int prices;
                int idnhahang;
                String namenh;
                if (response != null && response.length() != 2) {
                    list.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idmonan = jsonObject.getInt("idmonan");
                            dates = jsonObject.getString("dates");
                            descriptions = jsonObject.getString("descriptions");
                            images = jsonObject.getString("images");
                            names = jsonObject.getString("names");
                            prices = jsonObject.getInt("prices");
                            idnhahang = jsonObject.getInt("idnhahang");
                            namenh = jsonObject.getString("namenh");
                            list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                        }

                        contract.showSuccess(jsonArray.length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } , error -> {
                contract.showError(R.string.error);
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("idmonan",String.valueOf(idmo));
                    hashMap.put("idnhahang",String.valueOf(idnh));
                    return hashMap ;
                }
            };
            requestQueue.add(stringRequest);
        }

    public void getDataDish(List<Menu> list, int idmo , int idnh){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
            int id;
            int idmonan;
            String dates;
            String descriptions;
            String images;
            String names;
            int prices;
            int idnhahang;
            String namenh;
            if (response != null && response.length() != 2) {
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        idmonan = jsonObject.getInt("idmonan");
                        dates = jsonObject.getString("dates");
                        descriptions = jsonObject.getString("descriptions");
                        images = jsonObject.getString("images");
                        names = jsonObject.getString("names");
                        prices = jsonObject.getInt("prices");
                        idnhahang = jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                    }

                    contract.showSuccessdish(jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } , error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmonan",String.valueOf(idmo));
                hashMap.put("idnhahang",String.valueOf(idnh));
                return hashMap ;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getDataDrinks(List<Menu> list, int idmo , int idnh){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
            int id;
            int idmonan;
            String dates;
            String descriptions;
            String images;
            String names;
            int prices;
            int idnhahang;
            String namenh;
            if (response != null && response.length() != 2) {
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        idmonan = jsonObject.getInt("idmonan");
                        dates = jsonObject.getString("dates");
                        descriptions = jsonObject.getString("descriptions");
                        images = jsonObject.getString("images");
                        names = jsonObject.getString("names");
                        prices = jsonObject.getInt("prices");
                        idnhahang = jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                    }
                    contract.showSuccessdrinks(jsonArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } , error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmonan",String.valueOf(idmo));
                hashMap.put("idnhahang",String.valueOf(idnh));
                return hashMap ;
            }
        };
        requestQueue.add(stringRequest);
    }
}
