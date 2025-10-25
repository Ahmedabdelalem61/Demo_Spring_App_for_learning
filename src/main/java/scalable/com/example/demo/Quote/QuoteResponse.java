package scalable.com.example.demo.Quote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteResponse {
    private Long id;
    private String text;
    private Long ownerId;
    private String ownerName; // optional
    private int loveCount;
    private boolean lovedByCurrentUser;
}
