/**
 * Created by Leon on 2017/8/26.
 */
public class CriminalProxyLawyer implements CriminalSubject{

    private CriminalSubject criminalSubject;

    public CriminalProxyLawyer(CriminalSubject subject) {
        criminalSubject = subject;
    }

    @Override
    public void criminalSubject() {
        before();
        criminalSubject.criminalSubject();
        after();
    }
    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("收佣金");
    }
}
