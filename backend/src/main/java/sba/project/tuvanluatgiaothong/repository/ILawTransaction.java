package sba.project.tuvanluatgiaothong.repository;

import sba.project.tuvanluatgiaothong.pojo.Law;

import java.util.UUID;

public interface ILawTransaction {
    void commitTransaction();
    void rollbackTransaction();
    Law save(Law law);
    Law update(Law law);
    boolean delete(UUID lawId);
}
