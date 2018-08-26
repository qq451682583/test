package plugin.test.yzk.zw.com.http.repository;


import io.reactivex.Observable;
import io.reactivex.functions.Function;

import plugin.test.yzk.zw.com.http.HttpManager;
import plugin.test.yzk.zw.com.http.bean.Entity;
import plugin.test.yzk.zw.com.http.bean.Task;

public abstract class AbstractRepository<T extends Task, E extends Entity> implements Repository<T> {

    @Override
    @SuppressWarnings("unchecked")
    public Observable observe(T task) {
        return connectServer(addExtraInfo(task))
                .map(checkServerData())
                .map(parseData());
    }

    /**
     * 获取用于请求网络的service
     */
    protected <S> S getService(Class<S> s) {
        return HttpManager.getInstance().get(s);
    }

    /**
     * 请求网络
     */
    protected abstract Observable connectServer(T task);

    /**
     * 校验服务器数据
     *
     * @return 验证过后的JSON数据结构
     */
    protected abstract Function<E, E> checkServerData();

    /**
     * 从服务器返回中取出需要的数据
     */
    protected E parseServerData(E e) {
        return e;
    }

    /**
     * 给所有请求附加额外信息
     */
    protected T addExtraInfo(T t) {
        return t;
    }

    /**
     * 解析数据
     */
    private Function<E, E> parseData() {
        return new Function<E, E>() {
            @Override
            public E apply(E e) {
                return parseServerData(e);
            }
        };
    }


}
