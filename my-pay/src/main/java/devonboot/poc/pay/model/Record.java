package devonboot.poc.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Record {

    private String id;
    private Long amount;
    private Long pointDiff;
    private User user;
    private Wallet wallet;
    private Status status;
    private String timestamp;

    public enum Status {
        PROCESSING, COMPLETED, CANCELLED
    }
}
