package sba.project.tuvanluatgiaothong.repository;

import org.springframework.stereotype.Repository;

import sba.project.tuvanluatgiaothong.pojo.ChatHistory;

import java.util.List;
import java.util.UUID;

@Repository
public interface IChatbotRepository {

    public void save(ChatHistory chatHistory);

    public ChatHistory findById(UUID id);

    public List<ChatHistory> findByUserId(UUID userId);

    public void delete(ChatHistory chatHistory);

    public void update(ChatHistory chatHistory);

    public List<ChatHistory> findAll();

}
