package devonboot.poc.pay.service.saga;

import devonboot.poc.event.PointDeductedEvent;
import devonboot.poc.exception.InsufficientPointBalanceException;
import devonboot.poc.pay.model.saga.PaymentSagaObject;
import devonboot.saga.core.saga.SagaInstance;
import devonboot.saga.core.saga.SagaInstanceFactory;
import devonboot.saga.core.saga.builder.SagaStage;
import org.springframework.stereotype.Component;

@Component
public class PaySagaInstance implements SagaInstanceFactory<PaySagaService, PaymentSagaObject> {

    @Override
    public SagaInstance<PaymentSagaObject> createSagaInstance(PaySagaService service) {
        SagaInstance<PaymentSagaObject> sagaInstance = buildSaga()
                .doStep(SagaStage.START)
                    .local(service::createRecord)
                    .revert(service::abortRecord)
                .doStep()
                    .process(service::claimPoint)
                    .sendAndReceive("point-claimed", "point-claimed-reply")
                    .receiveOnEvent(PointDeductedEvent.class, service::handlePointDeductedEvent)
                    .receiveOnException(InsufficientPointBalanceException.class, service::handleInsufficientPointBalanceException)
                    .compensation(service::compensateClaimedPoint)
                .doStep(SagaStage.END)
                    .local(service::withdraw)
                .doStep()
                    .local(service::completeRecord)
                .build();

        return sagaInstance;
    }
}
