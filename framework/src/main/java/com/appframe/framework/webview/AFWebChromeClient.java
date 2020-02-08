package com.appframe.framework.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.appframe.framework.R;
import com.appframe.utils.NetUtil;
import com.appframe.utils.app.AppRuntimeUtil;

/**
 * @author jiangkun
 * @date 2020/2/8
 */

public class AFWebChromeClient extends WebChromeClient {
    private Context mContext;
    private AFWebView mWebView;

    public AFWebChromeClient(Context context, AFWebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (!isAvailable()) {
            return;
        }
        // 防止显示无网络的title
        if (!NetUtil.isConnect(mContext)) {
            return;
        }
        if (title == null) {
            return;
        }
    }

    // 支持js弹框
    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (isAvailable()) {
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle("Alert");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();
        }
        return true;
    }

    // 支持js弹框
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        if (isAvailable()) {
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle("Confirm");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });
            b.create().show();
        }
        return true;
    }

    // 支持js弹框
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        if (isAvailable()) {
            final View v = View.inflate(mContext, R.layout.support_prompt_dialog, null);
            ((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
            ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle("Prompt");
            b.setView(v);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
                    result.confirm(value);
                }
            });
            b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });
            b.create().show();
        }
        return true;
    }

    private boolean isAvailable() {
        return mContext != null && mWebView != null && AppRuntimeUtil.getInstance().isCurrentActivityAvailable();
    }
}
