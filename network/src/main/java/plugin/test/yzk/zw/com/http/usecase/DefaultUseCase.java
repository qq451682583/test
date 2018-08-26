package plugin.test.yzk.zw.com.http.usecase;


import io.reactivex.Observable;
import plugin.test.yzk.zw.com.http.executor.DefaultJobExecutor;
import plugin.test.yzk.zw.com.http.executor.ExecutionThread;
import plugin.test.yzk.zw.com.http.executor.PoolExecutorManager;
import plugin.test.yzk.zw.com.http.executor.ThreadExecutor;
import plugin.test.yzk.zw.com.http.executor.UIThread;
import plugin.test.yzk.zw.com.http.bean.Task;
import plugin.test.yzk.zw.com.http.repository.Repository;

public class DefaultUseCase<T extends Task>  extends UseCase<T> {

    private Repository repository;

    public DefaultUseCase(Repository repository) {
        this(new UIThread(), new DefaultJobExecutor(PoolExecutorManager.getInstance().getPoolExecutor()), repository);
    }

    public DefaultUseCase(ExecutionThread thread, ThreadExecutor executor, Repository repository) {
        super(thread, executor);
        this.repository = repository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Observable buildObservable(T t) {
        return repository.observe(t);
    }


}
