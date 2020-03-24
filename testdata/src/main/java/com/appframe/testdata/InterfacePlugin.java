package com.appframe.testdata;

import com.appframe.library.network.http.AFHttpClient;
import com.appframe.testdata.entity.PointData;
import com.appframe.utils.device.SystemUtil;
import com.appframe.utils.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InterfacePlugin {
    public ArrayList<PointData> interfaces = new ArrayList<>();
    private DurationInterceptor interceptor;

    public InterfacePlugin() {
        interfaces.clear();

        interceptor = new DurationInterceptor();

        OkHttpClient.Builder builder = AFHttpClient.getInstance().newBuilder();
        List<Interceptor> interceptors = builder.interceptors();
        interceptors.add(interceptor);

        AFHttpClient.setHttpClient(builder.build());
    }

    public void stop() {
        // TODO: 2020/3/24 其实没啥用，除非retrofit重置
        OkHttpClient.Builder builder = AFHttpClient.getInstance().newBuilder();
        List<Interceptor> interceptors = builder.interceptors();
        interceptors.remove(interceptor);

        AFHttpClient.setHttpClient(builder.build());

        interceptor = null;
    }

    public class DurationInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            long startNs = System.nanoTime();

            Request request = chain.request();
            Response response = chain.proceed(request);

            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            PointData data = new PointData();
            data.name = request.url().toString();
            data.duration = tookMs;
            data.recordTime = System.currentTimeMillis();
            interfaces.add(data);

            return response;
        }
    }
}
