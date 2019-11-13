package com.fpoly.suppermannh.ui.account.history;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.History;
import com.fpoly.suppermannh.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPresenter {
    private Context context;
    private HistoryContract historyContract;

    public HistoryPresenter(Context context, HistoryContract historyContract) {
        this.context = context;
        this.historyContract = historyContract;
    }

    public void getData  (List<History> histories, int page, int space){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanhistory, response -> {
            if (response != null &&  response.length() != 2 ){
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int iduser = jsonObject.getInt("iduser");
                    int idnhahang = jsonObject.getInt("idnhahang");
                    String price = jsonObject.getString("price");
                    String date = jsonObject.getString("date");
                    String namenh = jsonObject.getString("namenh");
                    String images = jsonObject.getString("images");
                    int tables = jsonObject.getInt("tables");
                    String idtrangthai = jsonObject.getString("idtrangthai");
                    histories.add(new History(id,iduser,idnhahang,price,date,namenh,images,tables,idtrangthai));

                }
                historyContract.showList(histories,true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }else {
                historyContract.showList(histories,false);
            }
        },error -> {
            historyContract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser", MainActivity.ID);
                hashMap.put("page",String.valueOf(page));
                hashMap.put("space",String.valueOf(space));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
