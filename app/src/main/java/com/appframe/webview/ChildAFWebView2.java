package com.appframe.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.appframe.framework.webview.AFWebView;
import com.appframe.utils.logger.Logger;

/**
 * @author jiangkun
 * @date 2020/2/9
 */

public class ChildAFWebView2 extends AFWebView {
    public ChildAFWebView2(Context context) {
        super(context);
    }

    public ChildAFWebView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildAFWebView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {
        Logger.getLogger().e("子类2");
    }
}
