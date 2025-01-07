package ma.fstt.hyfz.chatservice.chat;

import ma.fstt.hyfz.chatservice.query_response.QueryResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepo chatRepository;

    public ChatService(ChatRepo chatRepository) {
        this.chatRepository = chatRepository;
    }


    public Chat createChat(String userId, String title) {
        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setTitle(title);
        chat.setCreatedAt(LocalDateTime.now());
        chat.setLastUpdated(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    public List<Chat> getChatsByUser(String userId) {
        return chatRepository.findByUserId(userId);
    }

    public Chat addQueryResponse(String chatId, QueryResponse queryResponse) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with id: " + chatId));
        queryResponse.setTimestamp(LocalDateTime.now());
        chat.getQueryResponses().add(queryResponse);
        chat.setLastUpdated(LocalDateTime.now());
        return chatRepository.save(chat);
    }



}
