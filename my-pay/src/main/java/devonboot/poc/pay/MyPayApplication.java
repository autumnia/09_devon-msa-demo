package devonboot.poc.pay;

import devonboot.common.DevOnApplication;
import devonboot.common.annotation.DevOnBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@DevOnBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MyPayApplication {
    public static void main(String[] args) {
        DevOnApplication.run(MyPayApplication.class, args);
    }
}
