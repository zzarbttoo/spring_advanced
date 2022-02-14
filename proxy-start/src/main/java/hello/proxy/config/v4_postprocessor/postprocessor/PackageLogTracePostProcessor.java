package hello.proxy.config.v4_postprocessor.postprocessor;

//특정 패키지에 있는 bean들만 proxy로 만들 것이다


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage; //특정 패키지 하위에만 적용
    private final Advisor advisor; //advisor안에 pointcut과 advise가 있기 때문에 이거만 받아도 된다

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName ::: {}, bean ::: {} ", beanName, bean.getClass());

        //proxy 적용 대상 여부를 체크
        //proxy 적용 대상 아니면 원본을 그대로 진행
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)){ //다른 객체면 그냥 원본을 반환
            return bean;
        }

        //프록시 대상이면 프록시를 만들어서 반환
        ProxyFactory proxyFactory = new ProxyFactory(bean);//target으로 bean을 넣어준다
        proxyFactory.addAdvisor(advisor);

        Object proxy= proxyFactory.getProxy();
        log.info("create proxy : target ::: {}, proxy ::: {}" , bean.getClass(), proxy.getClass());
        return proxy;
    }
}
