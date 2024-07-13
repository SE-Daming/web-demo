package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.common.Tree;
import com.ideu.demo.server08.entity.City;
import com.ideu.demo.server08.mapper.CityMapper;
import com.ideu.demo.server08.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolsServiceImpl implements ToolsService
{
    @Autowired
    private CityMapper cityMapper;




    @Override
    public City getCityById(Long id) {
        return cityMapper.getCityById(id);
    }



    @Override
    public List<City> getProvince() {
        return cityMapper.getProvince();
    }

    @Override
    public List<City> getArea(Long pid) {
        return cityMapper.getArea(pid);
    }

    @Override
    public List<Tree> getTree() {
        List<City> allCities = cityMapper.getTree(); // 获取所有城市数据
        return buildTree(allCities, 0); // 从根节点开始构建树形结构
    }

    // 递归构建树形结构
    private List<Tree> buildTree(List<City> cities, int parentId) {
        List<Tree> tree = cities.stream()
                .filter(city -> parentId == city.getPid())
                .map(city -> {
                    Tree node = new Tree();
                    node.setId(String.valueOf(city.getId()));
                    node.setPid(String.valueOf(city.getPid()));
                    node.setCityName(city.getCityName());
                    node.setType(String.valueOf(city.getType()));
                    node.setChildren(buildTree(cities, city.getId()));
                    return node;
                })
                .collect(Collectors.toList());
        return tree;
    }



    @Override
    public List<City> getCitiesByPid(Long pid) {
        return cityMapper.getCitiesByPid(pid);
    }
}
