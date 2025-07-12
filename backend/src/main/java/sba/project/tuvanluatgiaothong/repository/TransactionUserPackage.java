package sba.project.tuvanluatgiaothong.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.pojo.UserPackage;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionUserPackage implements ITransactionUserPackage{

    private final UserPackageRepository userPackageRepository;

    @Override
    public UserPackage save(UserPackage userPackage) {
        return userPackageRepository.save(userPackage);
    }

    @Override
    public void delete(UUID id) {
        userPackageRepository.deleteById(id);
    }

    @Override
    public void disableAllOldPackageOfUser(UUID userId) {
        List<UserPackage> oldPackages = userPackageRepository.findByUserId(userId);
        for (UserPackage oldPackage : oldPackages) {
            if (oldPackage.isEnable()) {
                oldPackage.setEnable(Boolean.FALSE);
                userPackageRepository.save(oldPackage);
            }
        }
    }
}
