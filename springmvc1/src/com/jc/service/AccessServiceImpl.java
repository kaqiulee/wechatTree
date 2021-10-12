package com.jc.service;

import com.jc.dao.AccessMapper;
import com.jc.dao.TreeMapper;
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
