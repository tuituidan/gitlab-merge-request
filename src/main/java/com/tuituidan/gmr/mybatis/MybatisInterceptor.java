package com.tuituidan.gmr.mybatis;

import com.tuituidan.gmr.bean.entity.BaseEntity;
import com.tuituidan.gmr.util.StrExtUtils;

import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

/**
 * mybatis拦截器，统一处理数据ID、创建时间和最后修改时间.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
@Component
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object obj = invocation.getArgs()[1];
        if (obj instanceof BaseEntity) {
            BaseEntity<?> entity = (BaseEntity<?>) obj;
            switch (mappedStatement.getSqlCommandType()) {
                case INSERT:
                    entity.setId(StrExtUtils.getUuid());
                    entity.setCreateTime(LocalDateTime.now());
                    entity.setUpdateTime(LocalDateTime.now());
                    break;
                case UPDATE:
                    entity.setUpdateTime(LocalDateTime.now());
                    break;
                default:
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 这里可以不用实现.
    }
}
