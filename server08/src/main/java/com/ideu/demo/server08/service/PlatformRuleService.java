package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.PlatformRule;

import java.util.List;


public interface PlatformRuleService {
    List<PlatformRule> findAll();


    PlatformRule save(PlatformRule platformRule);

    void deleteById(Long id);
}
