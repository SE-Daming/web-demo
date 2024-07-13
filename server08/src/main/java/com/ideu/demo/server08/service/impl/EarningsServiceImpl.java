package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.Earnings;
import com.ideu.demo.server08.mapper.EarningsMapper;
import com.ideu.demo.server08.service.EarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EarningsServiceImpl implements EarningsService {

    private EarningsMapper earningsMapper;

    @Autowired
    public EarningsServiceImpl(EarningsMapper earningsMapper) {
        this.earningsMapper = earningsMapper;
    }

    @Override
    public Earnings saveEarnings(Earnings earnings) {
        earningsMapper.save(earnings);
        return earnings;
    }

    @Override
    public Optional<Earnings> getEarningsById(Long id) {
        Earnings earnings = earningsMapper.findById(id);
        return Optional.ofNullable(earnings);
    }

    @Override
    public List<Earnings> findAll() {
        return earningsMapper.findAll();
    }

    @Override
    public void deleteEarnings(Long id) {
        earningsMapper.deleteById(id);
    }

    @Override
    public List<Earnings> findAllPaged(int pageNo, int pageSize) {
        // 假设这里的实现是从数据库中分页查询数据
        int offset = (pageNo - 1) * pageSize;
        return earningsMapper.findAllPaged(offset, pageSize);
    }

    @Override
    public long countAll() {
        return earningsMapper.countAll();
    }


    @Override
    public void deleteBatch(List<Long> ids) {
        earningsMapper.deleteBatch(ids);
    }

    // 其他自定义查询方法的实现可以根据需要在这里添加
}
