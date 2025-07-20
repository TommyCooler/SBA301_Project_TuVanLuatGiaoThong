package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.pojo.AIModel;

@Service
@Transactional
@RequiredArgsConstructor
public class AIModelTransactionTransaction implements IAIModelTransaction {

    private final AIModelRepository aiModelRepository;

    @Override
    public AIModel save(AIModel aiModel) {
        return aiModelRepository.save(aiModel);
    }
}