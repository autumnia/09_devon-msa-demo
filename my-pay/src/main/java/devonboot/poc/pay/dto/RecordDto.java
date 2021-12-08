package devonboot.poc.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class RecordDto {

    private String id;
    private long amount;
    private long pointDiff;
    private String userId;
    private String walletId;
    private String status;
    private String timestamp;
}
