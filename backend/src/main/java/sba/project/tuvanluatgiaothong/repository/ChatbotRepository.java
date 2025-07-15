package sba.project.tuvanluatgiaothong.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.pojo.ChatHistory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChatbotRepository implements IChatbotRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public void save(ChatHistory chatHistory) {
        dynamoDBMapper.save(chatHistory);
    }

    @Override
    public ChatHistory findById(UUID id) {
        return dynamoDBMapper.load(ChatHistory.class, id);
    }

    @Override
    public List<ChatHistory> findByUserId(UUID userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("user_id = :userId")
                .withExpressionAttributeValues(Map.of(
                        ":userId", new AttributeValue().withS(userId.toString())
                ));

        return dynamoDBMapper.scan(ChatHistory.class, scanExpression);
    }

    @Override
    public void delete(ChatHistory chatHistory) {
        dynamoDBMapper.delete(chatHistory);
    }

    @Override
    public void update(ChatHistory chatHistory) {
        dynamoDBMapper.save(
                chatHistory,
                DynamoDBMapperConfig.builder()
                        .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                        .build()
        );
    }

    @Override
    public List<ChatHistory> findAll() {
        return List.of();
    }

    public DynamoDBSaveExpression buildExpression(ChatHistory chatHistory) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = Map.of(
                "id", new ExpectedAttributeValue(
                        new AttributeValue().withS(chatHistory.getId().toString())
                )
        );
        saveExpression.setExpected(expectedAttributeValueMap);
        return saveExpression;
    }
}
