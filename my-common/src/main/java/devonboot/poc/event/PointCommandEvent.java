package devonboot.poc.event;

import devonboot.saga.core.annotation.Id;
import devonboot.saga.core.event.CommandEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointCommandEvent implements CommandEvent {

    private String userId;
    private long paymentAmount;
    private long pointUsage;
}
