package sba.project.tuvanluatgiaothong.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.pojo.TransactionHistory;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionHistoryTransaction
{
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistory save(TransactionHistory dto) {
        return transactionHistoryRepository.save(dto);
    }

}
