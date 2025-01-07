package ma.fstt.hyfz.chatservice.chat;

import ma.fstt.hyfz.chatservice.query_response.QueryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestParam String userId, @RequestParam String title) {
        Chat chat = chatService.createChat(userId, title);
        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Chat>> getChats(@PathVariable String userId) {
        List<Chat> chats = chatService.getChatsByUser(userId);
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/{chatId}/query-response")
    public ResponseEntity<Chat> addQueryResponse(@PathVariable String chatId, @RequestBody QueryResponse queryResponse) {
        Chat updatedChat = chatService.addQueryResponse(chatId, queryResponse);
        return ResponseEntity.ok(updatedChat);
    }

}
