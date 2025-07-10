package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.pojo.LawType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionLawType implements ITransactionLawType {

    private final LawTypeRepository lawTypeRepository;

    @Override
    public LawType save(LawType lawType) {
        return this.lawTypeRepository.save(lawType);
    }
}
