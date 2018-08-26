package plugin.test.yzk.zw.com.http.executor;


import io.reactivex.Scheduler;


public interface ExecutionThread {
    Scheduler getScheduler();
}
