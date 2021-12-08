package devonboot.poc.membership.service;

import devonboot.poc.exception.InsufficientPointBalanceException;
import devonboot.poc.model.Membership;
import devonboot.poc.membership.repository.MembershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static devonframe.util.NullUtil.isNone;

@Slf4j
@Service
public class MembershipService {

    private MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership retrieveMembership(Membership membership) {
        return membershipRepository.retrieveMembershipById(membership.getId());
    }

    public Membership retrieveMembershipByUserId(String userId) {
        return membershipRepository.retrieveMembershipByUserId(userId);
    }

    public Membership updatePoint(String userId, long paymentAmount, long pointClaimed, boolean isRevert) {
        Membership membership = Membership.builder()
                .userId(userId)
                .build();
        membership = retrieveMembershipByUserId(membership.getUserId());
        long balance = membership.getPoint();
        long pointDiff;

        if(membership.getPoint() < pointClaimed) {
            // 포인트 부족 case
            throw new InsufficientPointBalanceException(
                    String.format("balance: %d, claimed: %d", membership.getPoint(), pointClaimed));
        } else if(pointClaimed == 0) {
            // 포인트 적립 case
            long reward;

            if("GOLD".equals(membership.getType())) {
                reward = (long)(paymentAmount * 0.1);
            } else if("SILVER".equals(membership.getType())) {
                reward = (long)(paymentAmount * 0.05);
            } else {
                reward = (long)(paymentAmount * 0.01);
            }
            pointDiff = reward;
        } else {
            // 포인트 차감 case
            pointDiff = -1 * pointClaimed;
        }

        if(isRevert) {
            balance -= pointDiff;
        } else {
            balance += pointDiff;
        }
        membership.setPoint(balance);
        membershipRepository.updateMembership(membership);

        return membership;
    }
}
