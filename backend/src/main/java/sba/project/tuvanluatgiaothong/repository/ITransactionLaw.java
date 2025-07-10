package sba.project.tuvanluatgiaothong.repository;


import java.util.UUID;

import sba.project.tuvanluatgiaothong.pojo.Law;

public interface ITransactionLaw {
    void commitTransaction();
    void rollbackTransaction();
    Law save(Law law);
    Law update(Law law);
    boolean delete(UUID lawId);
}