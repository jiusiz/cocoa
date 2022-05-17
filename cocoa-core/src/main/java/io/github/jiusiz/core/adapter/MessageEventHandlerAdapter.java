package io.github.jiusiz.core.adapter;

import java.util.List;

import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.event.Event;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 上午 10:30
 */
public class MessageEventHandlerAdapter extends AbstractHandlerMethodAdapter {

    private List<ArgumentResolver> argumentResolvers;

    @Override
    public void afterPropertiesSet() throws Exception {
        initArgumentResolvers();
    }

    private void initArgumentResolvers() {
        // TODO: 2022-5-17 添加参数解析器
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return true;
    }

    @Override
    protected void handleInternal(Event event, HandlerMethod handler) {
        // 准备参数
        Object[] args = new Object[handler.getParameterCount()];

        MethodParameter[] parameters = handler.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            ArgumentResolver resolver = findArgumentResolver(parameters[i]);
            args[i] = resolver.resolveArgument(event, handler);
        }

        invokeMethod();
        // TODO: 2022-5-17 这里是返回值处理器工作的地方
    }

    private ArgumentResolver findArgumentResolver(MethodParameter parameter) {
        if (argumentResolvers != null) {
            for (ArgumentResolver argumentResolver : argumentResolvers) {
                if (argumentResolver.supportsArgument(parameter)) {
                    return argumentResolver;
                }
            }
        }
        throw new RuntimeException("未找到参数解析器");
    }

    private Object invokeMethod() {
        // TODO: 2022-5-17 目标方法的执行
        return null;
    }
}
