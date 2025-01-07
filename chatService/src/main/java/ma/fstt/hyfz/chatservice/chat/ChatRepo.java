package ma.fstt.hyfz.chatservice.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepo extends MongoRepository<Chat , String> {
    List<Chat> findByUserId(String userId);
}
