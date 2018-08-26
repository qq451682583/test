package plugin.test.yzk.zw.com.http.observer;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import plugin.test.yzk.zw.com.http.exception.CustomException;
import plugin.test.yzk.zw.com.http.bean.DEntity;
import plugin.test.yzk.zw.com.http.bean.DTask;

/**
 * 下载
 *
 * @author 0220000957
 */
public abstract class AbstractDownloadObserver extends DefaultObserver<ResponseBody> {

    public Consumer<ResponseBody> consumer;
    private Activity context;
    private DTask task;

    public AbstractDownloadObserver(Activity context, DTask task) {
        this.context = context;
        this.task = task;
        init();
    }

    /**
     * 下载进度回调
     *
     * @param entity 包含下载文件信息
     */
    public abstract void onProgress(DEntity entity);

    /**
     * subscriber onNext回调
     *
     * @param entity 文件信息
     */
    public abstract void onNext(DEntity entity);

    @Override
    public void onNext(ResponseBody body) {

    }

    private void init() {
        this.consumer = new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody body) throws Exception {
                saveFile(body);
            }
        };
    }


    /**
     * 保存文件
     */
    private void saveFile(ResponseBody body) throws Exception {
        //总文件长度
        long fileLength = body.contentLength();
        //文件保存路径
        File filePath = getFilePath(task);

        final DEntity entity = new DEntity();
        entity.filePath = filePath.getAbsolutePath();
        entity.fileLength = fileLength;

        //创建写入流
        InputStream inputStream = body.byteStream();
        RandomAccessFile rf = new RandomAccessFile(filePath, "rw");
        try {
            byte[] buf = new byte[4096];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                //写入文件
                rf.write(buf, 0, len);
                //文件已读长度
                entity.readLength += len;

                //回调
                if (context != null && !context.isFinishing()) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(entity);
                        }
                    });
                }
            }
            if (context != null && !context.isFinishing()) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNext(entity);
                    }
                });
            }
        } finally {
            rf.close();
            inputStream.close();
        }
    }

    /**
     * 获取保存文件名
     *
     * @param task task
     * @return 文件名
     */
    private File getFilePath(DTask task) {
        if (TextUtils.isEmpty(task.url)) {
            throw new CustomException("download url can not be null");
        }
        File filePath;
        String fileName;
        if (TextUtils.isEmpty(task.fileName)) {
            fileName = task.url.substring(task.url.lastIndexOf("/") + 1);
        } else {
            fileName = task.fileName;
        }
        if (TextUtils.isEmpty(task.fileName)) {
            filePath = getDiskCacheDir(context, fileName);
        } else {
            File parent = new File(task.path);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            filePath = new File(task.path, fileName);
            if (filePath.exists()) {
                filePath.delete();
            }
        }
        return filePath;
    }

    /**
     * 获取文件存储路径
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}
