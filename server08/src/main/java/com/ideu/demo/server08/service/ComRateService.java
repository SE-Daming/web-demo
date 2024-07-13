package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.ComRate;

import java.util.List;

public interface ComRateService {

    ComRate save(ComRate comRate);
    void deleteById(Long id);
    List<ComRate> findAll();
}

