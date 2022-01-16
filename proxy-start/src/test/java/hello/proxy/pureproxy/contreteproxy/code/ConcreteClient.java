package hello.proxy.pureproxy.contreteproxy.code;

public class ConcreteClient {

    //다형성에 의해 자식 class인 timeProxy도 주입할 수 있다
    private ConcreteLogic concreteLogic;

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute(){
        concreteLogic.operation();
    }
}
