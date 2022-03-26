package com.keli.worktalk.Model;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetHelper {
    public static String requestHttp(Request request) throws IOException {
        String ret;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        ret = response.body().string();
        return ret;
    }
}
