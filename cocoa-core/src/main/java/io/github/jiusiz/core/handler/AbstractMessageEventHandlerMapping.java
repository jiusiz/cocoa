package io.github.jiusiz.core.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 7:30
 */
public abstract class AbstractMessageEventHandlerMapping implements InitializingBean {

    /**
     * 初始化此bean
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 属性设置之后检测候选 方法
    }
}
