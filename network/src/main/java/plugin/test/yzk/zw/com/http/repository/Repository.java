package plugin.test.yzk.zw.com.http.repository;

import io.reactivex.Observable;
import plugin.test.yzk.zw.com.http.bean.Task;

public interface Repository<T extends Task> {
     Observable observe(T t);
}
