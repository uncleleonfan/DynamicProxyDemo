/**
 * Created by Leon on 2017/8/26.
 */
public class CivilProxyLawyer implements CivilSubject{

    private CivilSubject civilSubject;

    public CivilProxyLawyer(CivilSubject subject) {
        civilSubject = subject;
    }

    @Override
    public void civilLawsuit() {
        before();
        civilSubject.civilLawsuit();
        after();
    }

    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("收佣金");
    }
}
