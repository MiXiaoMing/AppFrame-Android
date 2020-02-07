package com.appframe.framework.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appframe.framework.dialog.ProgressDialog;
import com.appframe.library.component.notify.AFToast;
import com.appframe.utils.app.AppRuntimeUtil;

/**
 * fragment基类
 * 添加了一部分之前的代码
 */
public abstract class AFBaseFragment extends Fragment {
    private View mRootView;
    private Activity activity;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this.getActivity());
    }

    public void setContentView(int layoutResID) {
        mRootView = LayoutInflater.from(getActivity()).inflate(layoutResID, null);
        initViews();
        addListener();
    }

    protected void initViews() {
    }

    protected void addListener() {
    }

    /**
     * 查找控件
     */
    public View findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    public Activity getActivityContext() {
        if (activity == null) {
            activity = getActivity();
        }
        return activity;
    }

    public void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShow()) {
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShow()) {
            mProgressDialog.dismiss();
        }
    }

    public void myToast(String message) {
        AFToast.showShort(AppRuntimeUtil.getInstance().getCurrentActivity(), message);
    }

    public void myToast(int id) {
        AFToast.showShort(AppRuntimeUtil.getInstance().getCurrentActivity(), id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }
}
