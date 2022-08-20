package com.powershop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powershop.mapper.TbItemParamMapper;
import com.powershop.pojo.TbItemParam;
import com.powershop.pojo.TbItemParamExample;
import com.powershop.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.isDistinct();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        return tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample).get(0);
    }

    @Override
    public PageResult selectItemParamAll(Integer page, Integer rows) {
        //分页
        PageHelper.startPage(page,rows);
        //查询
        List<TbItemParam> tbItemParamList = tbItemParamMapper.selectByExampleWithBLOBs(null);
        //封装
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParamList);
        return new PageResult(pageInfo.getPageNum(),pageInfo.getPages(),pageInfo.getList());
    }

    @Override
    public void insertItemParam(TbItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        tbItemParamMapper.insert(itemParam);
    }

    @Override
    public void deleteItemParamById(Long id) {
        tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
