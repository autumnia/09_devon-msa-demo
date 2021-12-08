package devonboot.poc.pay.model;

import devonboot.saga.core.saga.object.SagaObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Wallet {
    private String id;
    private long balance;
    private User user;
    private Account account;
}
