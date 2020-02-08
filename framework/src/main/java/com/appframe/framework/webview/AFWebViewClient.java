package com.appframe.framework.webview;

import android.content.Context;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appframe.framework.R;
import com.appframe.library.component.notify.AFToast;
import com.appframe.utils.NetUtil;
import com.appframe.utils.app.AppRuntimeUtil;

/**
 * @author jiangkun
 * @date 2020/2/8
 */

public class AFWebViewClient extends WebViewClient {
    private Context mContext;
    private AFWebView mWebView;

    public AFWebViewClient(Context context, AFWebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!isAvailable()) {
            return true;
        }
        if (mWebView.getUrl() == null || url == null) {
            return true;
        }
        if (!mWebView.getUrl().equals(url)) {
            mWebView.setBackPageNum(mWebView.getBackPageNum() + 1);
            mWebView.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!isAvailable()) {
            return;
        }
        // 设置状态。
        mWebView.setWebViewState(AFWebView.WEB_VIEW_STATE_START);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!isAvailable()) {
            return;
        }

        // 防止出错后赋值
        if (url != null && !url.contains("error.html")) {
            mWebView.setUrl(url);
        }

        if (mWebView.isCountDownTimerFinished()) {
        } else {
            mWebView.setWebViewState(AFWebView.WEB_VIEW_STATE_FINISH);
            if (mWebView.isWebViewFinished()) {
                mWebView.webViewFinished();
            }
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (!isAvailable()) {
            return;
        }
        if (!mWebView.isIsError()) {
            mWebView.setIsError(true);
            // 防止用户看到我们的域名
            mWebView.loadUrl("file:///android_asset/error.html");
            // 出现错误后，激活下拉刷新。
            if (NetUtil.isConnect(mContext)) {
                mWebView.setEnableRefresh(true);
            } else {
                AFToast.showShort(mContext, R.string.network_failed);
            }
        }
    }

    @Override
    public void onReceivedSslError(WebView view,
                                   SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    private boolean isAvailable() {
        return mContext != null && mWebView != null && AppRuntimeUtil.getInstance().isCurrentActivityAvailable();
    }
}
