package devonboot.poc.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class WalletDto {
    private String id;
    private long balance;
    private String userId;
    private String accountId;
}
