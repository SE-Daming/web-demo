package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.ComRate;
import com.ideu.demo.server08.mapper.ComRateMapper;
import com.ideu.demo.server08.service.ComRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComRateServiceImpl implements ComRateService {

    @Autowired
    public ComRateMapper comRateMapper;


    @Override
    public List<ComRate> findAll()
    {
        return comRateMapper.findAll();
    }

    @Override
    public ComRate save(ComRate comRate) {

            comRateMapper.save(comRate);
            return comRate;

    }

    @Override
    public void deleteById(Long id) {
        comRateMapper.deleteById(id);
    }



}
