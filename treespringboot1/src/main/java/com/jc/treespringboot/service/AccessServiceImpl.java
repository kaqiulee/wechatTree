package com.jc.treespringboot.service;

import com.jc.treespringboot.dao.AccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {
    @Autowired
    private AccessMapper accessMapper;

    @Override
    public void doAccess(){
        accessMapper.doAccess();

    }

}
