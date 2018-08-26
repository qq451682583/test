package plugin.test.yzk.zw.com.http.repository;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import plugin.test.yzk.zw.com.http.HttpManager;
import plugin.test.yzk.zw.com.http.config.DownloadConfig;
import plugin.test.yzk.zw.com.http.bean.DEntity;
import plugin.test.yzk.zw.com.http.bean.DTask;



public abstract class AbstractDownloadRepository extends AbstractRepository<DTask, DEntity> {

    @Override
    @SuppressWarnings("unchecked")
    public Observable observe(DTask task) {
        return connectServer(task);
    }

    @Override
    protected <S> S getService(Class<S> s) {
        DownloadConfig dConfig = new DownloadConfig(HttpManager.getInstance().getConfig().getBaseUrl());
        return HttpManager.getInstance().get(dConfig, s);
    }

    @Override
    protected Observable<ResponseBody> connectServer(DTask task) {
        return getDownloadResponseBody(task);
    }

    @Override
    protected Function<DEntity, DEntity> checkServerData() {
        return null;
    }

    /**
     * 获取下载文件的responseBody
     */
    protected abstract Observable<ResponseBody> getDownloadResponseBody(DTask task);

}
