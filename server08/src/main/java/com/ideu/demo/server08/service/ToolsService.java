package com.ideu.demo.server08.service;

import com.ideu.demo.server08.common.Tree;
import com.ideu.demo.server08.entity.City;

import java.util.List;

public interface ToolsService {


    List<City>getProvince();

    List<City>getArea(Long pid);

    List<Tree> getTree();



    City getCityById(Long id);

    List<City> getCitiesByPid(Long pid);

    // 构建城市树形结构
//    public List<City> buildCityTree(List<City> cities) {
//        // 实现同上
}
