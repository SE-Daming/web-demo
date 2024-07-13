package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.PlatformRule;
import com.ideu.demo.server08.mapper.PlatformRuleMapper;
import com.ideu.demo.server08.service.PlatformRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PlatformRuleServiceImpl implements PlatformRuleService
{

    @Autowired
    private PlatformRuleMapper platformRuleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlatformRule> findAll()
    {
        return platformRuleMapper.findAll();
    }

    @Override
    @Transactional
    public PlatformRule save(PlatformRule platformRule)
    {
        if (platformRule.getId() == null)
        {
            platformRuleMapper.save(platformRule);
        } else
        {
            return null;
        }
        return platformRule;
    }

    @Override
    @Transactional
    public void deleteById(Long id)
    {
        platformRuleMapper.delete(id);
    }
}
