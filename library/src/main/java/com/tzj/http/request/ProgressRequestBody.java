package com.tzj.http.request;

import com.tzj.http.http.IProgressListener;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import java.io.IOException;

/**
 * 上传进度
 */
public class ProgressRequestBody extends RequestBody {

    private final RequestBody requestBody;
    private final IProgressListener progressListener;
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody requestBody, IProgressListener progressListener) {
        this.requestBody = requestBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //刷新
        bufferedSink.flush();
    }

    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                bytesWritten += byteCount != -1 ? byteCount : 0;
                if (progressListener != null) {
                    progressListener.onProgress(bytesWritten, contentLength(), bytesWritten == contentLength());
                }
            }
        };
    }
}
