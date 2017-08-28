import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Leon on 2017/8/26.
 */
public class DynamicProxyLawyer implements InvocationHandler {

    //代理的真实对象
    private Object subject;

    public DynamicProxyLawyer(Object subject) {
        this.subject = subject;
    }

    /**
     * @param proxy 所代理的真实对象
     * @param method 所代理的真实对象某个方法的Method对象
     * @param args 所代理的真实对象某个方法接受的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(subject, args);
        after();
        return null;
    }

    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("收佣金");
    }
}
