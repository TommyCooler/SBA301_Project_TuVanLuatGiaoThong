package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;

import java.util.UUID;

@Repository
@Transactional
@RequiredArgsConstructor
public class TransactionUsagePackage implements ITransactionUsagePackage {

    private final UsagePackageRepository usagePackageRepository;

    @Override
    public UsagePackage save(UsagePackage usagePackage) {
        return this.usagePackageRepository.save(usagePackage);
    }

    @Override
    public void delete(UUID id) {
        this.usagePackageRepository.deleteById(id);
    }
}
