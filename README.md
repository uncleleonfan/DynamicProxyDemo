# 让我们打一场动态代理的官司--Java动态代理 #
代理模式是一种比较常用的设计模式，在某些场景下，我们要使用某些功能，并不是直接调用实现类，而是通过代理类来完成。通过代理，我们可以隐藏实现类的细节，在不修改实现类的基础上，还可以增加额外的功能，如校验，计算执行时长等。代理模式在现实生活中也是显而易见的，如房屋中介，代购，律师等。当我们要打一场官司时，我们自己对法律不是很了解，但是我们可以请专业的律师为我们提供代理，让他来帮我们打这场官司。

## 项目地址 ##
[https://github.com/uncleleonfan/DynamicProxyDemo](https://github.com/uncleleonfan/DynamicProxyDemo)


## 静态代理 ##
我们所说的代理，一般是指静态代理，即一个实现类对应一个代理类，一一对应的关系。我们用一场官司来说明静态代理。
首先，我们定义一个接口，表示我们要做的事情--打官司。

    interface Subject {
        void lawsuit();
    }
    
然后实现该接口

    public class RealSubject implements Subject {
        @Override
        public void lawsuit() {
            System.out.println("打官司");
        }
    }

但是我们对这个实现是不知道的，因为我们不知道如何打官司，所以这里需要一个代理律师：

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
代理律师也来实现lawsuite接口，并且打官司前先签合同，打完官司后收取佣金。
最后，我们通过代理律师来打这场官司。

    public class Main {
        public static void main(String[] args) {
            System.out.println("-------静态代理-------");
            Subject subject = new RealSubject();
            ProxyLawyer proxyLawyer = new ProxyLawyer(subject);
            proxyLawyer.lawsuit();
    }
    
## 动态代理 ##
静态代理是一对一的关系，一个实现类对应一个代理类，假设我要打一个民事诉讼，就要有个民事诉讼的代理律师，要打一个刑事诉讼，就要有个刑事诉讼律师。

    //民事诉讼
	interface CivilSubject {
	    void civilLawsuit();
	}

	//民事诉讼实现
	public class RealCivilSubject implements CivilSubject {
	    @Override
	    public void civilLawsuit() {
	        System.out.println("民事诉讼");
	    }
	}

	//民事诉讼代理律师
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
		........
	}

	//刑事诉讼
	public interface CriminalSubject {
	    void criminalSubject();
	}

	//刑事诉讼实现
	public class RealCriminalSubject implements CriminalSubject {
	    @Override
	    public void criminalSubject() {
	        System.out.println("刑事诉讼");
	    }
	}

	//刑事诉讼代理律师
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
		.......
	}
这里存在的问题是，民事诉讼的代理律师只能代理民事诉讼，不能代理刑事诉讼，刑事诉讼的代理律师只能代理刑事诉讼，不能代理民事诉讼，二者只能各司其职。但是一位律师是否代理各类诉讼呢？当然是可以的。另外，从代码角度看，如果新增一类诉讼，就又得新加一个代理类，代码量会更加，在代理类中的方法before()和after()方法都是重复的，不能被复用。那怎么解决这些问题了？这里就需要用到动态代理了。

动态代理不需要事先就创建代理类，而是根据需求动态地创建。就相当于一个律师，根据诉讼的需求而转换成不同的代理律师，不管诉讼类型的数目更加多少，律师只有一个。从代码角度讲就不会增加代理律师类，律师的里面公共的方法就能得到复用。

接下来我们来完成Java的动态代理，首先定义一个动态代理律师类DynamicProxyLawyer，他需要实现InvocationHandler接口，在invoke方法中触发代理方法的调用。

	public class DynamicProxyLawyer implements InvocationHandler {
	
	    //代理的真实对象
	    private Object subject;
	
	    public DynamicProxyLawyer(Object subject) {
	        this.subject = subject;
	    }
	
	    /**
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

接下来，我们可以使用这个动态代理律师来代理各种诉讼了。
	
	public class Main {
	
	    public static void main(String[] args) {
	
	        System.out.println("-------动态代理--------");
	        //动态创建一个民事诉讼代理的对象，该代理实现了接口CivilSubject
	        CivilSubject civilProxy = (CivilSubject) Proxy.newProxyInstance(
	                civilSubject.getClass().getClassLoader(),
	                new Class[]{CivilSubject.class},
	                new DynamicProxyLawyer(civilSubject)
	        );
	        civilProxy.civilLawsuit();
			//动态创建了一个刑事诉讼代理的对象，该代理实现了接口CriminalSubject
	        CriminalSubject criminalProxy = (CriminalSubject) Proxy.newProxyInstance(
	                criminalSubject.getClass().getClassLoader(),
	                new Class[]{CriminalSubject.class},
	                new DynamicProxyLawyer(criminalSubject)
	        );
	        criminalProxy.criminalSubject();
	    }
	}
