package devonboot.poc.pay.controller;

import devonboot.poc.model.Membership;
import devonboot.poc.pay.dto.UserDto;
import devonboot.poc.pay.model.Record;
import devonboot.poc.pay.model.User;
import devonboot.poc.pay.model.Wallet;
import devonboot.poc.pay.model.saga.PaymentSagaObject;
import devonboot.poc.pay.service.PayService;
import devonboot.poc.pay.service.saga.PaySagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static devonframe.util.NullUtil.isNone;

@Slf4j
@Controller
public class PayController {

    private PayService payService;
    private PaySagaService paySagaService;

    public PayController(PayService payService, PaySagaService paySagaService) {
        this.payService = payService;
        this.paySagaService = paySagaService;
    }

    @RequestMapping("/walletView.do")
    public String retrieveWallet(User user, ModelMap model) {
        // PoC용 샘플데이터
        user = User.builder()
                .id("20001")
                .build();
        Wallet wallet = payService.retrieveWallet(user);

        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);

        return "pay/wallet";
    }

    @RequestMapping("/paymentView.do")
    public String retrieveWalletForPayment(User user, ModelMap model) {
        // PoC용 샘플데이터
        user = User.builder()
                .id("20001")
                .build();
        Wallet wallet = payService.retrieveWallet(user);
        Record record = Record.builder()
                .wallet(wallet)
                .amount(0L)
                .pointDiff(0L)
                .user(user)
                .build();

        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        model.addAttribute("membership", Membership.builder().build());
        model.addAttribute("record", record);

        return "pay/payment";
    }

    @RequestMapping("/loadPoint.do")
    public String loadPoint(User user, ModelMap model) {
        // PoC용 샘플데이터
        user = User.builder()
                .id("20001")
                .build();
        Wallet wallet = payService.retrieveWallet(user);
        Membership membership = payService.retrieveMembership(user);
        Record record = Record.builder()
                .wallet(wallet)
                .amount(0L)
                .pointDiff(0L)
                .user(user)
                .build();

        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        model.addAttribute("membership", membership);
        model.addAttribute("record", record);

        return "pay/payment";
    }

    @RequestMapping("/payment.do")
    public String payment(Record recordDto, ModelMap model) {
        // PoC용 샘플데이터
        User user = User.builder()
                .id("20001")
                .build();
        Wallet wallet = payService.retrieveWallet(user);
        Record record = Record.builder()
                .user(user)
                .wallet(wallet)
                .amount(recordDto.getAmount())
                .pointDiff(recordDto.getPointDiff())
                .build();
        PaymentSagaObject paymentSagaObject = paySagaService.createSagaObject(record);
        paySagaService.startSaga(paymentSagaObject);

        wallet = payService.retrieveWallet(user);
        Membership membership = payService.retrieveMembership(user);

        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        model.addAttribute("membership", membership);
        model.addAttribute("record", record);

        return "pay/paymentSucceed";
    }

    @RequestMapping("/historyView.do")
    public String retrieveRecordList(UserDto userDto, ModelMap model) {
        // PoC용 샘플데이터
        if(isNone(userDto.getId())) {
            userDto.setId("20001");
        }
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();
        List<Record> paymentList = payService.retrieveRecordList(user);

        model.addAttribute("user", userDto);
        model.addAttribute("recordList", paymentList);

        return "pay/history";
    }

}
