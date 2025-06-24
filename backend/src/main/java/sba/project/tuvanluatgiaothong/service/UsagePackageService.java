package sba.project.tuvanluatgiaothong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.dto.request.CreateUsagePackageDTO;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageDTO;
import sba.project.tuvanluatgiaothong.exception.ResourceNotFoundException;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
import sba.project.tuvanluatgiaothong.repository.UsagePackageRepository;
import sba.project.tuvanluatgiaothong.service.IUsagePackageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsagePackageService implements IUsagePackageService {
    private final UsagePackageRepository usagePackageRepository;

    @Override
    public List<UsagePackageDTO> getAllPackages() {
        return usagePackageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsagePackageDTO> getAllActivePackages() {
        return usagePackageRepository.findByIsEnableTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsagePackageDTO getPackageById(UUID id) {
        return usagePackageRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
    }

    @Override
    public List<UsagePackageDTO> searchPackages(String keyword) {
        return usagePackageRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsagePackageDTO> getPackagesWithinPrice(Float minPrice, Float maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return getAllPackages();
        }

        float effectiveMinPrice = minPrice != null ? minPrice : Float.MIN_VALUE;
        float effectiveMaxPrice = maxPrice != null ? maxPrice : Float.MAX_VALUE;

        return usagePackageRepository.findByPriceBetween(effectiveMinPrice, effectiveMaxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsagePackageDTO createPackage(CreateUsagePackageDTO dto) {
        UsagePackage usagePackage = UsagePackage.builder()
                .usagePackageId(UUID.randomUUID())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .dailyLimit(dto.getDailyLimit())
                .daysLimit(dto.getDaysLimit())
                .isEnable(true)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        return convertToDTO(usagePackageRepository.save(usagePackage));
    }

    @Override
    @Transactional
    public UsagePackageDTO updatePackage(UUID id, CreateUsagePackageDTO dto) {
        UsagePackage existingPackage = usagePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        if (dto.getName() != null) {
            existingPackage.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingPackage.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            existingPackage.setPrice(dto.getPrice());
        }
        if (dto.getDailyLimit() != null) {
            existingPackage.setDailyLimit(dto.getDailyLimit());
        }
        if (dto.getDaysLimit() != null) {
            existingPackage.setDaysLimit(dto.getDaysLimit());
        }

        existingPackage.setUpdateDate(LocalDateTime.now());
        return convertToDTO(usagePackageRepository.save(existingPackage));
    }

    @Override
    @Transactional
    public void disablePackage(UUID id) {
        UsagePackage existingPackage = usagePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
        existingPackage.setIsEnable(false);
        existingPackage.setUpdateDate(LocalDateTime.now());
        usagePackageRepository.save(existingPackage);
    }

    @Override
    @Transactional
    public void enablePackage(UUID id) {
        UsagePackage existingPackage = usagePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
        existingPackage.setIsEnable(true);
        existingPackage.setUpdateDate(LocalDateTime.now());
        usagePackageRepository.save(existingPackage);
    }

    private UsagePackageDTO convertToDTO(UsagePackage entity) {
        UsagePackageDTO dto = new UsagePackageDTO();
        dto.setId(entity.getUsagePackageId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDailyLimit(entity.getDailyLimit());
        dto.setDaysLimit(entity.getDaysLimit());
        dto.setIsEnable(entity.getIsEnable());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateDate(entity.getUpdateDate());
        return dto;
    }
}