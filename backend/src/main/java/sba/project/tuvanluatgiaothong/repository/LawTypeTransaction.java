package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.pojo.LawType;

@Service
@Transactional
@RequiredArgsConstructor
public class LawTypeTransaction implements ILawTypeTransaction {

    private final LawTypeRepository lawTypeRepository;

    @Override
    public LawType save(LawType lawType) {
        return this.lawTypeRepository.save(lawType);
    }
}
