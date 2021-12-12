package com.chinamobile.digitaltwin.insole.service;

import com.chinamobile.digitaltwin.insole.bean.Insole;
import com.chinamobile.digitaltwin.insole.dao.InsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsoleService {
    @Autowired
    private InsoleRepository insoleRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(Insole insole){
        insoleRepository.insert(insole);
    }

    public void insert(List<Insole> insole){
        insoleRepository.insert(insole);
    }

    public Page<Insole> findAll(int page,int size){
        QPageRequest pageRequest = new QPageRequest(1, 5);
        Page<Insole> insoles = insoleRepository.findAll(pageRequest);
        return insoles;
    }
}
