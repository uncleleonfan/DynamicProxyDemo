import java.lang.reflect.Proxy;

/**
 * Created by Leon on 2017/8/26.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("-------静态代理-------");
        Subject subject = new RealSubject();
        ProxyLawyer proxyLawyer = new ProxyLawyer(subject);
        proxyLawyer.lawsuit();

        CivilSubject civilSubject = new RealCivilSubject();
        CivilProxyLawyer civilProxyLawyer = new CivilProxyLawyer(civilSubject);
        civilProxyLawyer.civilLawsuit();

        CriminalSubject criminalSubject = new RealCriminalSubject();
        CriminalProxyLawyer criminalProxyLawyer = new CriminalProxyLawyer(criminalSubject);
        criminalProxyLawyer.criminalSubject();

        System.out.println("-------动态代理--------");
        CivilSubject civilProxy = (CivilSubject) Proxy.newProxyInstance(
                civilSubject.getClass().getClassLoader(),
                new Class[]{CivilSubject.class},
                new DynamicProxyLawyer(civilSubject)
        );
        civilProxy.civilLawsuit();

        CriminalSubject criminalProxy = (CriminalSubject) Proxy.newProxyInstance(
                criminalSubject.getClass().getClassLoader(),
                new Class[]{CriminalSubject.class},
                new DynamicProxyLawyer(criminalSubject)
        );
        criminalProxy.criminalSubject();
    }
}
