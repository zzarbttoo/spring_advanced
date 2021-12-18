package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//controller을 interface로 만드는 경우는 별로 없다
@RequestMapping  //스프링은 @Controller 또는 @RequestMapping이 있어야 스프링 컨트롤러로 인식할 수 있다(둘 중 하나만 있으면 된다)
@ResponseBody
public interface OrderControllerV1 {

    //interface에서는 @RequestParam과 같은 어노테이션을 잘 명시해줘야 컴파일 할 때 인식을 잘 할 수 있다(class 부분에서는 크게 상관 없다)
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();

}
