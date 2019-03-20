package com.tzj.http.cache;

import com.tzj.http.HttpApplication;
import com.tzj.http.platform.PlatformHandler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static okhttp3.internal.Util.UTF_8;

public class CacheImp implements ICache {
    private DiskLruCache cache;

    public CacheImp(HttpUrl url,String methed) {
        String path = null;
        if (PlatformHandler.isAndroid){
            path = HttpApplication.mCtx.getCacheDir().getAbsolutePath()+"/http/"+Cache.key(url)+"/"+methed;
        }else{
            path = "C://http/"+Cache.key(url)+"/"+methed;
        }
        cache = DiskLruCache.create(
                FileSystem.SYSTEM,
                new File(path),
                VERSION,
                ENTRY_COUNT,
                1024 * 1024 * 50
        );
        try {
            cache.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response get(Request request,String key) throws IOException {
        key = key(request,key);
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = cache.get(key);
            if (snapshot == null) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        Source source = snapshot.getSource(ENTRY_METADATA);
        BufferedSource buffer = Okio.buffer(source);
        RealResponseBody realResponseBody = new RealResponseBody(
                null,//todo 这里可能有问题
                snapshot.getLength(ENTRY_METADATA),
                buffer);
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)//todo 这里写死了1.1
                .code(200)
                .message("OK")
                .body(realResponseBody)
                .build();
    }

    @Override
    public void put(Response response,String key) {
        if (response.code()!=200){
            return;
        }
        try {
            key = key(response.request(),key);
            DiskLruCache.Editor editor = cache.edit(key);
            if (editor != null) {
                Sink sink = editor.newSink(ENTRY_METADATA);
                BufferedSink bufferedSink = Okio.buffer(sink);
                ResponseBody body = response.peekBody(Long.MAX_VALUE);
                bufferedSink.write(body.source(),body.contentLength());
                bufferedSink.close();
                body.close();
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            cache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求体得到key
     */
    private String key(Request request,String key) throws IOException {
        Buffer buffer = new Buffer();
        if (key != null){
            buffer.writeString(key,Charset.defaultCharset());
        }else{
            request.body().writeTo(buffer);
        }
        return buffer.md5().hex();
    }
    private Charset charset(ResponseBody body) {
        MediaType contentType = body.contentType();
        return contentType != null ? contentType.charset(UTF_8) : UTF_8;
    }
}
