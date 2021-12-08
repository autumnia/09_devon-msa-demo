package devonboot.poc.membership.controller;

import devonboot.poc.model.Membership;
import devonboot.poc.membership.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MembershipController {

    private MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/membership/{userId}")
    public Membership retrieveMembership(@PathVariable("userId") String userId) {
        return membershipService.retrieveMembershipByUserId(userId);
    }
}
