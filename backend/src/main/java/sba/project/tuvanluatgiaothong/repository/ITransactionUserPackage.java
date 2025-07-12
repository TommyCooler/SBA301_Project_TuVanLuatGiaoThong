package sba.project.tuvanluatgiaothong.repository;


import sba.project.tuvanluatgiaothong.pojo.UserPackage;

import java.util.UUID;

public interface ITransactionUserPackage {
    UserPackage save(UserPackage userPackage);
    void delete(UUID id);
    void disableAllOldPackageOfUser(UUID userId);
}
