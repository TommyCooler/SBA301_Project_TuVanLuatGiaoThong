package sba.project.tuvanluatgiaothong.service;

import java.util.UUID;

import sba.project.tuvanluatgiaothong.pojo.Law;

public interface ILawService {

    void save(Law law);
    void update(Law law);
    boolean delete(UUID id);
    Law getall();
    Law getById(UUID id);
    
}
