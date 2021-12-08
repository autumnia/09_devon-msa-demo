package devonboot.poc.pay.service.saga;

import devonboot.poc.event.PointCommandEvent;
import devonboot.poc.event.PointDeductedEvent;
import devonboot.poc.exception.InsufficientAccountBalanceException;
import devonboot.poc.exception.InsufficientPointBalanceException;
import devonboot.poc.pay.model.Record;
import devonboot.poc.pay.model.Wallet;
import devonboot.poc.pay.model.saga.PaymentSagaObject;
import devonboot.poc.pay.repository.PayRepository;
import devonboot.saga.core.saga.SagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PaySagaService extends SagaService<PaymentSagaObject> {

    PayRepository payRepository;

    public PaySagaService(PaySagaInstance sagaInstance, PayRepository payRepository) {
        super(sagaInstance);
        this.payRepository = payRepository;
    }

    public PaymentSagaObject createSagaObject(Record record) {
        return PaymentSagaObject.builder()
                .walletId(record.getWallet().getId())
                .record(record)
                .build();
    }

    public void createRecord(PaymentSagaObject payment) {
        Record record = payment.getRecord();
        record.setId(UUID.randomUUID().toString());
        record.setStatus(Record.Status.PROCESSING);
        payment.setRecord(record);

        payRepository.createRecord(record);
    }

    public void withdraw(PaymentSagaObject paymentSagaObject) {
        Wallet wallet = paymentSagaObject.getRecord().getWallet();
        Record record = paymentSagaObject.getRecord();

        // 페이 잔액 부족
        if(wallet.getBalance() < record.getAmount()) {
            // accountApiClient.requestPayCharge // 연결된 충전 계좌(계정계) 출금 요청
            if(wallet.getBalance() - record.getAmount() < -100000) { // 연결된 충전 계좌의 잔액이 부족한 경우라고 가정
                log.debug("========== 연결된 pay 충전 계좌의 잔액 부족 ==========");
                throw new InsufficientAccountBalanceException(
                        String.format("balance: %d, claimed: %d", wallet.getBalance(), record.getAmount()));
            }
        }

        wallet.setBalance(wallet.getBalance() - record.getAmount());
        payRepository.updateWallet(wallet);
        log.debug("========== pay 머니 차감 ==========");
    }

    public void compensateWithdraw(PaymentSagaObject  paymentSagaObject) {
        Wallet wallet = paymentSagaObject.getRecord().getWallet();
        Record record = paymentSagaObject.getRecord();
        wallet.setBalance(wallet.getBalance() + record.getAmount());
        payRepository.updateWallet(wallet);

        record.setStatus(Record.Status.CANCELLED);
        paymentSagaObject.setRecord(record);

        payRepository.updateRecord(record);

        log.debug("========== pay 머니 차감에 대한 보상처리 완료 ==========");
    }

    public PointCommandEvent claimPoint(PaymentSagaObject paymentSagaObject) {
        Record record = paymentSagaObject.getRecord();
        return new PointCommandEvent(record.getUser().getId(), record.getAmount(), record.getPointDiff());
    }

    public void compensateClaimedPoint(PaymentSagaObject  paymentSagaObject) {
        Record record = paymentSagaObject.getRecord();
        send(new PointCommandEvent(record.getUser().getId(), record.getAmount(), record.getPointDiff()),
                "compensate-point-claimed");
    }

    public void handlePointDeductedEvent(PointDeductedEvent event) {
        log.debug("========== my-point 로부터 포인트 차감이 처리됨을 수신 ==========");
    }

    public void handleInsufficientPointBalanceException(InsufficientPointBalanceException exception) {
        log.debug("========== my-point 로부터 포인트 부족 에러를 수신 ==========");
        Record record = this.getSagaObject().getRecord();
        Long discountedAmount = record.getAmount();
        Long pointDiff = record.getPointDiff();
        record.setAmount(discountedAmount + pointDiff);
        record.setPointDiff(0L);
        this.getSagaObject().setRecord(record);

        payRepository.updateRecord(record);

        log.debug("========== 사용할 포인트를 0으로 조정하고 결제 금액 원복 ==========");
    }

    public void abortRecord(PaymentSagaObject paymentSagaObject) {
        Record record = paymentSagaObject.getRecord();
        record.setStatus(Record.Status.CANCELLED);
        paymentSagaObject.setRecord(record);

        payRepository.updateRecord(record);

        log.debug("========== pay 결제 원복 완료 ==========");
    }

    public void completeRecord(PaymentSagaObject paymentSagaObject) {
        Record record = paymentSagaObject.getRecord();
        record.setStatus(Record.Status.COMPLETED);
        paymentSagaObject.setRecord(record);

        payRepository.updateRecord(record);

        log.debug("========== saga 처리 완료 ==========");
    }
}
