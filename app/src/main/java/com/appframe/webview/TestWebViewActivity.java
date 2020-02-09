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
    private Button mBtnDialog, mBtnChild, mBtnChild2;
    private ChildAFWebView mChildWebView;
    private ChildAFWebView2 mChildWebView2;
    private String tuijiang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);
        tuijiang = "https://yuncaiweb.ycaibaba.com/home?token=F8A0B1921ED95543791B22DED0203CFC&version=2.6.2";

        webView = findViewById(R.id.web_view);
        webView.loadUrl(tuijiang);

        testDialog();

        testChildWebView();

        testChildWebView2();
    }

    private void testChildWebView() {
        mBtnChild = findViewById(R.id.btn_child);
        mBtnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChildWebView = new ChildAFWebView(TestWebViewActivity.this);
                mChildWebView.loadUrl(tuijiang);
                new AlertDialog.Builder(TestWebViewActivity.this)
                        .setView(mChildWebView)
                        .show();
            }
        });
    }

    private void testChildWebView2() {
        mBtnChild2 = findViewById(R.id.btn_child2);
        mBtnChild2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChildWebView2 = new ChildAFWebView2(TestWebViewActivity.this);
                mChildWebView2.loadUrl(tuijiang);
                new AlertDialog.Builder(TestWebViewActivity.this)
                        .setView(mChildWebView2)
                        .show();
            }
        });
    }

    private void testDialog() {
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
