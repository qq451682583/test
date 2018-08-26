package plugin.test.yzk.zw.com.http.usecase;


import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import plugin.test.yzk.zw.com.http.executor.ExecutionThread;
import plugin.test.yzk.zw.com.http.executor.ThreadExecutor;
import plugin.test.yzk.zw.com.http.bean.Task;
import plugin.test.yzk.zw.com.http.observer.AbstractDownloadObserver;

public abstract class UseCase<T extends Task> {

    //逻辑运行的线程
    private ExecutionThread executionThread;
    //执行器
    private ThreadExecutor threadExecutor;
    //RxJava订阅者
    private DisposableObserver oberver;

    UseCase(ExecutionThread thread, ThreadExecutor executor) {
        this.executionThread = thread;
        this.threadExecutor = executor;
    }

    public abstract Observable buildObservable(T t);

    @SuppressWarnings("unchecked")
    public void execute(DisposableObserver subscriber, T t) {
        this.oberver = subscriber;
        this.buildObservable(t)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(executionThread.getScheduler())
                .subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    public void download(AbstractDownloadObserver subscriber, T t) {
        this.oberver = subscriber;
        this.buildObservable(t)
                .subscribeOn(Schedulers.from(threadExecutor))
                .doOnNext(subscriber.consumer)
                .observeOn(executionThread.getScheduler())
                .subscribe(subscriber);
    }

    /**
     * 解除观察者
     */
    public void dispose() {
        if (oberver != null && !oberver.isDisposed()) {
            oberver.dispose();
        }
    }

    public boolean isDisposed() {
        return oberver == null || oberver.isDisposed();
    }

}
