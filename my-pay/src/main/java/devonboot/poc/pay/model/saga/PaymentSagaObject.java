package devonboot.poc.pay.model.saga;

import devonboot.poc.pay.model.Record;
import devonboot.poc.pay.model.User;
import devonboot.poc.pay.model.Wallet;
import devonboot.saga.core.annotation.Id;
import devonboot.saga.core.saga.object.SagaObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentSagaObject implements SagaObject {

    @Id
    private String walletId;
    private Record record;
}
