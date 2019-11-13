package com.fpoly.suppermannh.ui.account.history.historydetail.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.lisenner.RattingLisenner;
import com.fpoly.suppermannh.untils.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RattingBottomSheet extends BottomSheetDialogFragment {

    private RattingLisenner rattingLisenner;

    public static RattingBottomSheet getInstance() {
        RattingBottomSheet rattingBottomSheet = new RattingBottomSheet();
        return rattingBottomSheet;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rattingLisenner  = (RattingLisenner) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rattingLisenner = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ratting_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        Button btn_xac_nhan_ratting_history = view.findViewById(R.id.btn_xac_nhan_ratting_history);
        RatingBar ratingBar = view.findViewById(R.id.rattingBar_history_detail);
        EditText editText = view.findViewById(R.id.edt_comment_history_detail);
        ImageView img_dismiss = view.findViewById(R.id.img_clear_diglog_history_detail);

        addDisposable(RxView.clicks(img_dismiss).throttleFirst(2,TimeUnit.SECONDS)
        .subscribe(unit -> {
            getDialog().dismiss();
        }));

        addDisposable(RxView.clicks(btn_xac_nhan_ratting_history)
                .throttleFirst(2,TimeUnit.SECONDS)
        .subscribe(unit -> {
            int ratting = Integer.valueOf((int) ratingBar.getRating());
            String commment = editText.getText().toString().trim();
            if (ratting <= 0){
                Toasty.success(getContext(),R.string.error_ratiing).show();
            }else if ( StringUtils.isEmpty(commment)){
                Toasty.success(getContext(),R.string.error_null).show();
            }else {
                rattingLisenner.comment(ratting,commment);
                hideDialog();
            }
        }));

    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    protected void hideDialog() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
