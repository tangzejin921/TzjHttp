package com.tzj.http.http;

/**
 * 进度
 * 请求类    实现 IProgressListener 即可拥有进度
 * CallBack 实现 IProgressListener 即可拥有进度
 */
public interface IProgressListener {
    /**
     * @param progress  已经下载或上传字节数
     * @param total     总字节数
     * @param done      是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
