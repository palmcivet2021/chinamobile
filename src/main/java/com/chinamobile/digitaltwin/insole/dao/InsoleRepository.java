package com.chinamobile.digitaltwin.insole.dao;

import com.chinamobile.digitaltwin.insole.bean.Insole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsoleRepository extends MongoRepository<Insole ,String> {

}
