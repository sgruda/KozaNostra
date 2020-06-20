package pl.lodz.p.it.ssbd2020.ssbd05.interceptors;

import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.time.LocalDateTime;

/**
 * Klasa, której instancje przechwytują wywołania metod biznesowych.
 */
@Log
public class TrackerInterceptor {

    @Resource
    private SessionContext sessionContext;

    /**
     * Śledź wywołanie metody biznesowej.
     *
     * @param invocationContext Kontekst wywołania.
     * @return Wynik wywołania metody.
     * @throws Exception wyjątek napotkany przy wywołaniu danej metody.
     */
    @AroundInvoke
    public Object traceInvoke(InvocationContext invocationContext) throws Exception {
        LocalDateTime interceptionTime = LocalDateTime.now();
        StringBuilder message = new StringBuilder("Intercepted method invocation: ");
        message.append(invocationContext.getMethod().toString());
        message.append(" Interception time: ").append(interceptionTime);
        message.append(" User: ").append(sessionContext.getCallerPrincipal().getName());
        message.append(" With parameters: ");

        if (invocationContext.getParameters() != null) {
            for (Object param : invocationContext.getParameters()) {
                if (null == param) {
                    message.append("null ");
                } else {
                    message.append(param.toString()).append(", ");
                }
            }
        }
        else {
            message.append("null ");
        }

        Object result;
        try {
            result = invocationContext.proceed();
        }catch (Exception e) {
            message.append("With exception: ");
            message.append(TrackerInterceptor.class.getName()).append(" ");
            message.append(e);
            log.severe(message.toString());
            throw e;
        }
            message.append(" Returned value: ");
            if (null == result) {
                message.append("null ");
            } else {
                message.append(result.toString());
            }
            log.info(message.toString());
            return result;
    }
}
