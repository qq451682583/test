package plugin.test.yzk.zw.com.http.observer;


import io.reactivex.observers.DisposableObserver;
import plugin.test.yzk.zw.com.http.exception.ExceptionManager;


public abstract class DefaultObserver<T> extends DisposableObserver<T> {

    @Override
    public void onError(Throwable e) {
        ExceptionManager.getInstance().getEHandler().handleException(e);
    }
}

