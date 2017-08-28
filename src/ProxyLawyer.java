/**
 * Created by Leon on 2017/8/26.
 */
public class ProxyLawyer implements Subject{

    private Subject subject;

    public ProxyLawyer(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void lawsuit() {
        before();
        subject.lawsuit();
        after();
    }

    private void before() {
        System.out.println("签合同");
    }

    private void after() {
        System.out.println("收佣金");
    }
}
