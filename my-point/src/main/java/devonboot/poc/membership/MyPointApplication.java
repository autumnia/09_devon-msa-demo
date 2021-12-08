package devonboot.poc.membership;

import devonboot.common.DevOnApplication;
import devonboot.common.annotation.DevOnBootApplication;

@DevOnBootApplication
public class MyPointApplication {
    public static void main(String[] args) {
        DevOnApplication.run(MyPointApplication.class, args);
    }
}