package com.fpoly.suppermannh.ui.home.menudetail;

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
import com.fpoly.suppermannh.model.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MenuDetailPresenter {
    Context context;
    MenuDetailContract contract;

    public MenuDetailPresenter(Context context, MenuDetailContract contract) {
        this.context = context;
        this.contract = contract;
    }
    public void getData(List<Menu> list, String idmo, int page){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenus, response ->{
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
                    contract.showlist(list,true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.showlist(list,false);
            }

        } , error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("page",String.valueOf(page));
                hashMap.put("space","15");
                hashMap.put("idmonan",idmo);
                return hashMap ;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void getHouts(Menu menu,List<Houst> housts){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanhousts, response -> {
            int id;
            String phones;
            String names;
            double lat;
            double lng;
            String images;
            if (response != null &&  response.length() != 2){
                housts.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        names = jsonObject.getString("names");
                        lat = jsonObject.getDouble("lat");
                        lng = jsonObject.getDouble("lng");
                        images = jsonObject.getString("images");
                        housts.add(new Houst(id,names,lat,lng,images));
                    }
                    contract.showSuccess(housts,menu);
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
                hashMap.put("id",String.valueOf(menu.getIdnhahang()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
