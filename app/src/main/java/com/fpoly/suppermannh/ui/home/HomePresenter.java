package com.fpoly.suppermannh.ui.home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.untils.MyTimerTask;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import es.dmoral.toasty.Toasty;

public class HomePresenter {
    private HomeContract contract;
    private Context context;
    private MyTimerTask myTimerTask;

    public HomePresenter(Context context, MyTimerTask myTimerTask,HomeContract contract) {
        this.context = context;
        this.myTimerTask = myTimerTask;
        this.contract = contract;
    }

    public void layoutParams(RoundedImageView[] dots, int dotscount, LinearLayout linearLayout){
        for (int i=0; i< dotscount; i++){
            dots[i] = new RoundedImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            linearLayout.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
    }

    public void setPosition(ViewPager viewPager,ImageView[] dots, int dotscount){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(myTimerTask, 6000, 6000);
    }

    public void getDataHoust(List<Houst> housts, int page, int count){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanhoust, response -> {
            int id;
            String phones;
            String names;
            double lat;
            double lng;
            String images;
            String address;

            if (response != null &&  response.length() != 2){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         id = jsonObject.getInt("id");
                        phones = jsonObject.getString("phones");
                        names = jsonObject.getString("names");
                        lat = jsonObject.getDouble("lat");
                         lng = jsonObject.getDouble("lng");
                         images = jsonObject.getString("images");
                        address = jsonObject.getString("diachi");
                        housts.add(new Houst(id,phones,names,lat,lng,images,address));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                contract.showSuccess();
            }else {
                contract.dismisButton();
            }
        },error -> {
            contract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("page",String.valueOf(page));
                hashMap.put("space",String.valueOf(count));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
