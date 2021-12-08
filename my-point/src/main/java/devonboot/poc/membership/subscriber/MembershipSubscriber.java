package devonboot.poc.membership.subscriber;

import devonboot.poc.event.PointCommandEvent;
import devonboot.poc.event.PointDeductedEvent;
import devonboot.poc.membership.service.MembershipService;
import devonboot.saga.core.annotation.MessageListener;
import devonboot.saga.core.event.EventReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MembershipSubscriber {

    private MembershipService membershipService;

    public MembershipSubscriber(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @MessageListener(topics = "point-claimed")
    public EventReply handlePointClaimed(PointCommandEvent event) {
        log.info("MembershipSubscriber.handlePointClaimed");

        try {
            membershipService.updatePoint(event.getUserId(), event.getPaymentAmount(), event.getPointUsage(), false);
            return EventReply.builder()
                    .event(new PointDeductedEvent())
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }

    @MessageListener(topics = "compensate-point-claimed")
    public void handleCompensatePointClaimed(PointCommandEvent event) {
        log.info("MembershipSubscriber.handleCompensatePointClaimed");

        try {
            membershipService.updatePoint(event.getUserId(), event.getPaymentAmount(), event.getPointUsage(), true);
        } catch (Exception e) {
            throw e;
        }
    }
}
