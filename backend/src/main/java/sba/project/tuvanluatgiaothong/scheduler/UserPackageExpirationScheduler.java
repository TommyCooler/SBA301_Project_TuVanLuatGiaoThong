package sba.project.tuvanluatgiaothong.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;
import sba.project.tuvanluatgiaothong.pojo.UserPackage;
import sba.project.tuvanluatgiaothong.repository.UserPackageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPackageExpirationScheduler {
    private final UserPackageRepository userPackageRepository;

    @Scheduled(cron = "0 0 0 * * *") // Runs at midnight every day
    @Transactional
    public void checkAndUpdateExpiredPackages() {
        log.info("Starting expired packages check at {}", LocalDateTime.now());

        List<UserPackage> expiredPackages = userPackageRepository
                .findByExpirationDateBeforeAndStatus(LocalDateTime.now(), UserPackageStatus.ACTIVE);

        if (!expiredPackages.isEmpty()) {
            expiredPackages.forEach(userPackage -> {
                userPackage.setStatus(UserPackageStatus.EXPIRED);
                userPackage.setUpdatedDate(LocalDateTime.now());
                log.info("Package ID {} marked as expired", userPackage.getId());
            });

            userPackageRepository.saveAll(expiredPackages);
            log.info("Updated {} expired packages", expiredPackages.size());
        }

        log.info("Completed expired packages check at {}", LocalDateTime.now());
    }
}