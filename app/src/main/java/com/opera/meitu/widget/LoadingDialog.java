package com.opera.meitu.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opera.meitu.R;


public class LoadingDialog extends Dialog {

    private TextView tv;
    private RelativeLayout rl;


    public LoadingDialog(Context context) {
        super(context, R.style.Dialog_bocop);
        init();
    }

    private void init() {
        View contentView = View.inflate(getContext(),
                R.layout.activity_custom_loding_dialog_layout, null);
        setContentView(contentView);
        rl = (RelativeLayout) contentView.findViewById(R.id.loading_rl);
        tv = (TextView) contentView.findViewById(R.id.tv);
        setText("请稍候");
        setCancelable(false);
    }

    public void setText(String str) {
        tv.setText(str);
    }

    @Override
    public void show() {

        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {

        try {
            super.dismiss();

        } catch (Exception e) {

        }
    }

    @Override
    public void setTitle(CharSequence title) {
        tv.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }
}
