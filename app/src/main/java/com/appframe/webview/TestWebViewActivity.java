package com.appframe.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.appframe.R;
import com.appframe.framework.activity.AFBaseActivity;
import com.appframe.framework.webview.AFWebView;

/**
 * @author jiangkun
 * @date 2020/2/7
 */

public class TestWebViewActivity extends AFBaseActivity {
    private AFWebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);

        webView = findViewById(R.id.web_view);
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onBackPressed() {
        webView.goBack();
    }
}
