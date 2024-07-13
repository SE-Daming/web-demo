package com.ideu.demo.server08.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.Engineer;
import com.ideu.demo.server08.mapper.EngineerMapper;
import com.ideu.demo.server08.service.EngineerService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // 声明此类所有方法都处于事务中
public class EngineerServiceImpl implements EngineerService {

    @Autowired
    private EngineerMapper engineerMapper;

    @Override
    public List<Engineer> findAll() {
        return engineerMapper.findAll();
    }

    @Override
    public Optional<Engineer> findById(Long id) {
        Engineer engineer = engineerMapper.findById(id);
        return Optional.ofNullable(engineer);
    }

    @Override
    @Transactional
    public Engineer save(Engineer engineer) {
        if (engineer.getId() == null) {
            engineerMapper.save(engineer);
        } else {
            engineerMapper.update(engineer);
        }
        return engineer;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        engineerMapper.delete(id);
    }


    @Override
    public PageResult pageQuery(String pageNum, String pageSize, String serviceArea, String name) {
        PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));

        // 调用查询方法获取分页后的结果
        List<Engineer> engineers = engineerMapper.pageQuery2(serviceArea, name);

        // 将查询结果封装到 PageInfo 中，以便获取分页相关信息
        PageInfo<Engineer> pageInfo = new PageInfo<>(engineers);

        // 创建 PageResult 对象并返回，包括数据列表和总记录数
        return new PageResult(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public void update(Engineer engineer) {
        engineerMapper.update(engineer);
    }
}
