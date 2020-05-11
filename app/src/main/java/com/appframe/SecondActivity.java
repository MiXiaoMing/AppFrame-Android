package com.appframe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appframe.utils.logger.Logger;

public class SecondActivity extends Activity {
    private LinearLayout llyRoot;
    private TextView tvOne, tvTwo;

    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        llyRoot = findViewById(R.id.llyRoot);

        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);

        TextView tvStatus = findViewById(R.id.tvStatus);

        tvOne.setOnClickListener(clickListener);
        tvTwo.setOnClickListener(clickListener);
        tvStatus.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            if (id == R.id.tvStatus) {
                if (count == 0) {

                } else if (count == 1) {
//                    llyRoot.removeView(tvOne);
                    tvOne.setVisibility(View.GONE);
                } else if (count == 2) {
                    llyRoot.removeView(tvTwo);
                }

                ++count;

                if (tvOne != null) {
                    Logger.getLogger().e(tvOne.isActivated() + "..." + tvOne.isShown() + "..." + tvOne.isEnabled()
                            + "..." + (tvOne.getParent() == null));
                }

                if (tvTwo != null) {
                    Logger.getLogger().e(tvTwo.isActivated() + "..." + tvTwo.isShown() + "..." + tvTwo.isEnabled()
                            + "..." + (tvTwo.getParent() == null));
                }
            }
        }
    };
}
