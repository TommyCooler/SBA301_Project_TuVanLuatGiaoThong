package sba.project.tuvanluatgiaothong.repository;

import sba.project.tuvanluatgiaothong.pojo.UsagePackage;

import java.util.UUID;

public interface ITransactionUsagePackage {

    public UsagePackage save(UsagePackage usagePackage);

    public void delete(UUID id);

}
