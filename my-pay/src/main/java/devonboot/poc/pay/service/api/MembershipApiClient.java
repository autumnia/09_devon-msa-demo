package devonboot.poc.pay.service.api;

import devonboot.poc.model.Membership;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="pay", url="${devon.poc.url.my-point}")
public interface MembershipApiClient {
	@GetMapping("/membership/{userId}")
	Membership retrieveMembership(@PathVariable("userId") String userId);
}