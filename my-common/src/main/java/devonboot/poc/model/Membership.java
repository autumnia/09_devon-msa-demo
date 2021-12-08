package devonboot.poc.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Membership implements Serializable {
    private String id;
    private String userId;
    private Long point;
    private String type;
}
