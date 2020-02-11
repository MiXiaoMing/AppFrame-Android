package com.appframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.appframe.api.interfaces.UserDataManager;
import com.appframe.api.user.SendSmsEntity;
import com.appframe.framework.config.MetaDataConfig;
import com.appframe.framework.dialog.ProgressDialog;
import com.appframe.framework.share.ShareMedia;
import com.appframe.framework.share.ShareUtils;
import com.appframe.library.component.image.ImageLoader;
import com.appframe.utils.logger.Logger;
import com.appframe.webview.TestWebViewActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        sendHttp();

        loadImage();

        share();

        loginQq();

        progressDialog();

        webView();
    }

    private void initConfig() {
        new MetaDataConfig()
                .initCacheDirectory("com.qiumi")
                .initSPStoreName("qiumi");
    }

    private void sendHttp() {
        new UserDataManager()
                .sendSms("13718863263")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendSmsEntity>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.getLogger().e("服务地址：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SendSmsEntity result) {
                        if (!result.getStatus().equals("true")) {
                            Logger.getLogger().e("服务地址，msgCode：" + result.getMessage() + "/n");
                        } else {
                            if (result.getData() == null) {
                                Logger.getLogger().e("服务地址, result为空");
                                return;
                            }

                            finish();
                        }
                    }
                });
    }

    private void loadImage() {
        ImageView imageView = findViewById(R.id.iv_img);
        ImageLoader.circle(this, R.drawable.default_image, imageView);
    }

    private void share() {
        // 微信和qq都需要appkey才能正常用。
        findViewById(R.id.btn_share_display_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareDisplayList(MainActivity.this);
            }
        });

        findViewById(R.id.btn_share_platform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.sharePlatform(MainActivity.this, ShareMedia.QZONE);
            }
        });
    }

    private void loginQq() {
        findViewById(R.id.btn_login_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 因未配置正确的appkey，这里无法正常登录，需要注册并获取appkey。
                ShareUtils.login(MainActivity.this, ShareMedia.QQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 分享回调需要。
        ShareUtils.onActivityResult(this, requestCode, resultCode, data);
    }

    private void progressDialog() {
    }

    private void webView() {
        findViewById(R.id.btn_web_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestWebViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
