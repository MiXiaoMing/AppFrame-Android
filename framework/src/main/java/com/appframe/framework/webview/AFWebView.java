package com.appframe.framework.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appframe.framework.R;
import com.appframe.library.application.observer.ActivityLifecycle;
import com.appframe.library.application.observer.ActivityLifecycleObserver;
import com.appframe.library.component.notify.AFToast;
import com.appframe.utils.NetUtil;
import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.logger.Logger;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * WebView基类
 * <p>
 * 功能：7秒倒计时、点击重新加载、加载错误页面。
 * <p>
 * 接口：
 * loadUrl(url)
 * <p>
 * 布局UI：
 * WebView、重新加载按钮、loading进度条。
 */

public class AFWebView extends RelativeLayout implements ActivityLifecycleObserver {
    private Context mContext;
    private WebView mWebView;
    private String mUrl;

    /**
     * 点我重新加载。
     */
    private TextView mEmptyView;
    /**
     * loading
     */
    private ImageView mLoadingView;
    /**
     * WebView加载超时倒计时器，超过WEB_VIEW_OVER_LOAD_TIME秒数，显示重新加载按钮。
     */
    private CountDownTimer mWebViewReloadTimer;
    /**
     * 下拉刷新：默认不可下拉刷新。激活条件：WebViewClient的onReceivedError（）。
     */
    private SmartRefreshLayout mRefreshLayout;
    private String mWebViewState;
    private boolean isCountDownTimerFinished;
    /**
     * 是否加载异常。
     */
    private boolean mIsError;
    /**
     * WebView开始加载标识。
     */
    public static final String WEB_VIEW_STATE_START = "start";
    /**
     * WebView加载完成标识。
     */
    public static final String WEB_VIEW_STATE_FINISH = "finished";
    /**
     * WebView超时时间。（单位：毫秒。超过此时间后，认为网络不好，显示重新加载按钮，给用户重新加载的机会）
     */
    private static final long WEB_VIEW_OVER_LOAD_TIME = 15 * 1000;
    /**
     * 倒计时间隔时间。（单位：毫秒）
     */
    private static final long COUNT_DOWN_INTERNAL = 1000;
    /**
     * goBack
     */
    private int mBackPageNum;

    public AFWebView(Context context) {
        this(context, null);
    }

    public AFWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AFWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initView(final Context context) {
        if (context == null) {
            return;
        }
        mContext = context;
        inflate(getContext(), R.layout.af_web_view, this);
        mRefreshLayout = findViewById(R.id.smart_refresh_layout);
        initRefreshLayout(context);

        mWebView = findViewById(R.id.prevent_crash_web_view);
        mEmptyView = findViewById(R.id.tv_empty_view);
        mLoadingView = findViewById(R.id.iv_loading);
        Glide.with(mContext).load(R.drawable.gif_loading).into(mLoadingView);
        initWebView();
        ActivityLifecycle.attach(this);
    }

    private void initRefreshLayout(final Context context) {
        // 默认不可下拉刷新，如果出现错误了（小猫跳绳），激活下拉刷新。
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                try {
                    // 下拉刷新组件500毫秒后收起。
                    mRefreshLayout.finishRefresh(500);
                    // 500毫秒后设置不可下拉。
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (NetUtil.isConnect(context)) {
                                    // 有网才设置不可下拉刷新。
                                    mRefreshLayout.setEnableRefresh(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 500);
                    webViewStartLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // https中可以加载http图片（头像）。
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.requestFocus();

        setWebViewClient(new AFWebViewClient(mContext, this));

        setWebChromeClient(new AFWebChromeClient(mContext, this));
    }

    private void initData() {

    }

    private void initListener() {
        mEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isConnect(mContext)) {
                    webViewStartLoading();
                } else {
                    AFToast.showShort(getContext(), R.string.network_failed);
                }
            }
        });
    }

    /**
     * 是否可返回。
     *
     * @return mWebView.canGoBack()。
     */
    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void clearCache(boolean includeDiskFiles) {
        mWebView.clearCache(includeDiskFiles);
    }

    public void clearCacheAndReload() {
        mWebView.clearCache(true);
        mWebView.reload();
    }

    public void destroy() {
        mWebView.destroy();
    }

    /**
     * Activity等外部调用
     */
    public void goBack() {
        if (mContext instanceof Activity) {
            // 适配加载error.html后的返回
            if (mBackPageNum > 0) {
                mWebView.goBack();
                mBackPageNum -= 1;
            } else {
                ((Activity) mContext).finish();
            }
        }
    }

    /**
     * 倒计时器。
     */
    private void initCountDownTimer() {
        mWebViewReloadTimer = new CountDownTimer(WEB_VIEW_OVER_LOAD_TIME, COUNT_DOWN_INTERNAL) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (!isAvailable()) {
                    return;
                }
                isCountDownTimerFinished = true;// 设置flag
                // WebView未加载完，或者，加载完了但没网络：显示重新加载。
                if (!isWebViewFinished() || (isWebViewFinished() && !NetUtil.isConnect(mContext))) {
                    mWebView.stopLoading();
                    mEmptyView.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    mLoadingView.setVisibility(View.GONE);
                } else {
                }
            }
        };
    }

    /**
     * WebView是否加载完成。
     *
     * @return 是否加载完成
     */
    public boolean isWebViewFinished() {
        return mWebViewState != null && mWebViewState.equals(WEB_VIEW_STATE_FINISH);
    }

    /**
     * 执行javascript方法
     */
    public void loadJavaScriptFunction(String methodName) {
        this.loadJavaScriptFunction(methodName, null);
    }

    /**
     * 执行javascript方法
     */
    public void loadJavaScriptFunction(String methodName, String param) {
        if (mWebView == null) {
            return;
        }

        String javascript;
        if (param == null) {
            javascript = String.format("javascript:%s()", methodName);
        } else {
            javascript = String.format("javascript:%s(" + "\"" + param + "\"" + ")", methodName);
        }
        mWebView.loadUrl(javascript);
    }

    /**
     * 对外暴露的加载接口
     */
    public void loadUrl(String url) {
        if (url == null) {
            return;
        }
        if (url.startsWith("javascript:")) {
            Logger.getLogger().e("url：" + url);
            mWebView.loadUrl(url);
        } else {
            mUrl = url;
            webViewStartLoading();
        }
    }

    /**
     * 从窗口移除。
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mWebViewReloadTimer != null) {
            try {
                mWebViewReloadTimer.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ActivityLifecycle.detach(this);
    }

    public void postUrl(String url, byte[] postData) {
        mWebView.postUrl(url, postData);
    }

    /**
     * 重新加载。
     */
    public void reload() {
        Logger.getLogger().e("url：" + mWebView.getUrl());
        mWebView.reload();
    }

    public void setWebChromeClient(WebChromeClient client) {
        mWebView.setWebChromeClient(client);
    }

    public void setWebViewClient(WebViewClient client) {
        mWebView.setWebViewClient(client);
    }

    public void stopLoading() {
        mWebView.stopLoading();
    }

    /**
     * 显示loading。
     * 时机：重新加载时。
     */
    private void showLoading() {
        if (mLoadingView.getVisibility() != View.VISIBLE) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 倒计时开始。
     */
    private void startCountDownTimer() {
        isCountDownTimerFinished = false;
        try {
            if (mWebViewReloadTimer == null) {
                initCountDownTimer();
            }
            mWebViewReloadTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * WebView开始加载
     */
    public void webViewStartLoading() {
        if (mUrl == null) {
            return;
        }
        Logger.getLogger().d("url：" + mUrl);
        mWebView.loadUrl(mUrl);
        // 显示loading
        showLoading();
        // 设置为GONE，否则EmptyView占满全屏导致WebView显示不出来
        mEmptyView.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        // 倒计时开始
        startCountDownTimer();
    }

    /**
     * WebView加载完成
     */
    public void webViewFinished() {
        if (NetUtil.isConnect(mContext)) {
            mWebView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mWebView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mLoadingView.setVisibility(View.GONE);
        if (mWebViewReloadTimer != null) {
            try {
                mWebViewReloadTimer.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAvailable() {
        return mContext instanceof Activity && AppRuntimeUtil.getInstance().isCurrentActivityAvailable() || mContext instanceof ContextThemeWrapper;
    }

    /**
     * 网络变化
     */
    private void networkStatus(int state) {
        loadJavaScriptFunction("networkStatus", "");
    }

    /**
     * 前后台切换
     */
    private void enterForeground(boolean isForeground) {
        loadJavaScriptFunction("enterForeground", "");
    }

    /************* 子类可以重写 *************/
    public void shouldOverrideUrlLoading(WebView view, String url) {
        Logger.getLogger().e("父类");
        setBackPageNum(mBackPageNum + 1);
        loadUrl(url);
    }

    public void onPageFinished(WebView view, String url) {
    }

    public void onReceivedTitle(WebView view, String title) {
    }

    /************* get set *************/
    public void setDownloadListener(DownloadListener downloadListener) {
        this.mWebView.setDownloadListener(downloadListener);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setCacheMode(int mode) {
        mWebView.getSettings().setCacheMode(mode);
    }

    @Override
    public void activityResumed(String s, String s1, long l) {

    }

    @Override
    public void activityPaused(String s, String s1, long l) {

    }

    @Override
    public void changeToForeground() {
        enterForeground(true);
    }

    @Override
    public void changeToBackground() {
        enterForeground(false);
    }

    @Override
    public boolean frontOrBack() {
        return false;
    }

    @Override
    public void attached() {

    }

    @Override
    public void detached() {

    }

    public void setBackPageNum(int backPageNum) {
        this.mBackPageNum = backPageNum;
    }

    public int getBackPageNum() {
        return mBackPageNum;
    }

    public void setWebViewState(String webViewState) {
        this.mWebViewState = webViewState;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public boolean isCountDownTimerFinished() {
        return isCountDownTimerFinished;
    }

    public boolean isIsError() {
        return mIsError;
    }

    public void setIsError(boolean isError) {
        this.mIsError = isError;
    }

    public void setEnableRefresh(boolean enableRefresh) {
        mRefreshLayout.setEnableRefresh(enableRefresh);
    }
}
