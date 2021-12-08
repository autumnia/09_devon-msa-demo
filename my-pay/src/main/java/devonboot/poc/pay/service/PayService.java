package devonboot.poc.pay.service;

import devonboot.poc.model.Membership;
import devonboot.poc.pay.model.Record;
import devonboot.poc.pay.model.User;
import devonboot.poc.pay.model.Wallet;
import devonboot.poc.pay.repository.PayRepository;
import devonboot.poc.pay.service.api.MembershipApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PayService {

    private PayRepository payRepository;
    private MembershipApiClient membershipApiClient;

    public PayService(PayRepository payRepository, MembershipApiClient membershipApiClient) {
        this.payRepository = payRepository;
        this.membershipApiClient = membershipApiClient;
    }

    public Wallet retrieveWallet(String id) {
        return payRepository.retrieveWalletById(id);
    }

    public Wallet retrieveWallet(User user) {
        return payRepository.retrieveWalletByUser(user);
    }

    public List<Record> retrieveRecordList(User user) {
        return payRepository.retrieveRecordListByUser(user);
    }

    public Membership retrieveMembership(User user) {
        return membershipApiClient.retrieveMembership(user.getId());
    }
}
