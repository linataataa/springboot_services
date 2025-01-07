package ma.fstt.hyfz.chatservice.query_response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QueryResponse {
    private String query;
    private String response;
    private LocalDateTime timestamp;
}
