package com.ideu.demo.server08.service;

import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.Engineer;

import java.util.List;
import java.util.Optional;

public interface EngineerService {
    List<Engineer> findAll();

    Optional<Engineer> findById(Long id);

    Engineer save(Engineer engineer);

    void deleteById(Long id);


    PageResult pageQuery(String pageNum, String pageSize, String serviceArea, String name);

    void update(Engineer engineer);
}
