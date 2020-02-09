package com.appframe.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.appframe.R;
import com.appframe.framework.activity.AFBaseActivity;
import com.appframe.framework.webview.AFWebView;

/**
 * @author jiangkun
 * @date 2020/2/7
 */

public class TestWebViewActivity extends AFBaseActivity {
    private AFWebView webView;
    private Button mBtnDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);

        webView = findViewById(R.id.web_view);
        webView.loadUrl("https://yuncaiweb.ycaibaba.com/home?token=F8A0B1921ED95543791B22DED0203CFC&version=2.6.2");
//        webView.loadUrl("http://www.baidu.com");

        mBtnDialog = findViewById(R.id.btn_dialog);
        mBtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(TestWebViewActivity.this)
                        .setTitle("")
                        .setView(R.layout.activity_test_web_view)
                        .show();

                AFWebView afWebView = alertDialog.findViewById(R.id.web_view);
                afWebView.loadUrl("http://www.baidu.com");
            }
        });
    }

    @Override
    public void onBackPressed() {
        webView.goBack();
    }
}
