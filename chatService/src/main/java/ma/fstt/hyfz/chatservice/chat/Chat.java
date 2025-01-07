package ma.fstt.hyfz.chatservice.chat;

import lombok.Getter;
import lombok.Setter;
import ma.fstt.hyfz.chatservice.query_response.QueryResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "chats")
@Getter
@Setter
public class Chat {
    @Id
    private String chatId;
    private String userId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private List<QueryResponse> queryResponses = new ArrayList<>();
}
