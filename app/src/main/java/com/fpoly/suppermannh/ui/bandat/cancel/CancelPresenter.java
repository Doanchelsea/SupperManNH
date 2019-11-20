package com.fpoly.suppermannh.ui.bandat.cancel;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.PolyRetrofit;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Manage;
import com.fpoly.suppermannh.model.messing.Messing;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.Constans;
import com.fpoly.suppermannh.untils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelPresenter {
    Context context;
    CancelContract cancelContract;
    Manage manage;
    String token;

    public CancelPresenter(Context context, CancelContract cancelContract, Manage manage) {
        this.context = context;
        this.cancelContract = cancelContract;
        this.manage = manage;
    }
    public void delete() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandeletemanage, response -> {
            getToken();
        }, error -> {
            cancelContract.error(R.string.error);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("idmanage",String.valueOf(manage.getId()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void getToken(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.tokennh, response -> {
            if (response.length() != 2) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        token = jsonObject.getString("tokennh");
                    }
                    postMessing(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                cancelContract.error(R.string.error);
            }
        },error -> {
            cancelContract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(manage.getIdnhahang()));
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
        map.put(Constans.CODE, Constans.HOUSTERROR);
        map.put(Constans.MESSAGE,Constans.MESSAGE102);
        map.put(Constans.NAME, MainActivity.NAME);
        map.put(Constans.ID,String.valueOf(manage.getId()));

        Map<String,Object> map1 = new HashMap<>();
        map1.put(Constans.TO,token);
        map1.put(Constans.DATA,map);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(Constans.HTTP3),
                (new JSONObject(map1)).toString());

        PolyRetrofit.getInstance().getMesssing(Constans.KEY,body).enqueue(new Callback<Messing>() {
            @Override
            public void onResponse(Call<Messing> call, Response<Messing> response) {
                cancelContract.success();
            }

            @Override
            public void onFailure(Call<Messing> call, Throwable t) {
            }
        });
    }
}
