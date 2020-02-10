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

public class ChildAFWebView extends AFWebView {
    public ChildAFWebView(Context context) {
        super(context);
    }

    public ChildAFWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildAFWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {
        super.shouldOverrideUrlLoading(view, url);
    }
}
