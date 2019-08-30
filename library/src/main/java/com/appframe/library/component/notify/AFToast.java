package com.appframe.library.component.notify;

import android.content.Context;
import android.widget.Toast;

public class AFToast {

    public static void showShort(Context context, int resStr) {
        Toast.makeText(context, resStr, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
