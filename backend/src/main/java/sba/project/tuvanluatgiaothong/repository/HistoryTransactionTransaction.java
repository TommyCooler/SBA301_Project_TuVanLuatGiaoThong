package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.pojo.TransactionHistory;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryTransactionTransaction implements IHistoryTransactionTransaction {
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistory save(TransactionHistory dto) {
        return transactionHistoryRepository.save(dto);
    }

}
