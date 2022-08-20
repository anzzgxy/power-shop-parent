package com.powershop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powershop.mapper.TbItemCatMapper;
import com.powershop.mapper.TbItemDescMapper;
import com.powershop.mapper.TbItemMapper;
import com.powershop.mapper.TbItemParamItemMapper;
import com.powershop.pojo.*;
import com.powershop.utils.IDUtils;
import com.powershop.utils.PageResult;
import com.sun.demo.jvmti.hprof.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public TbItem selectItemInfo(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        //开启分页
        PageHelper.startPage(page,rows);
        //数据库查询
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated DESC");
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusNotEqualTo((byte)3);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        //封装数据
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        return new PageResult(pageInfo.getPageNum(),pageInfo.getPages(),pageInfo.getList());
    }

    @Override
    public void insertTbItem(TbItem tbItem,String desc,String itemParams) {
        //添加item数据
        tbItem.setId(IDUtils.genItemId());
        tbItem.setStatus((byte)1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItemMapper.insertSelective(tbItem);
        //关联添加item_desc的数据
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(tbItemDesc);
        //添加item_param_item
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(tbItem.getId());
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(new Date());
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insert(tbItemParamItem);
    }

    @Override
    public void deleteItemById(Long itemId) {
        //需要先删除关联的表
        //删除item_param_item
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        tbItemParamItemMapper.deleteByExample(tbItemParamItemExample);
        //删除item_desc的数据
        tbItemDescMapper.deleteByPrimaryKey(itemId);
        //item数据
        tbItemMapper.deleteByPrimaryKey(itemId);
    }

    @Override
    public  Map<String,Object> preUpdateItem(Long itemId) {
        //查询关联表的数据,返回map集合
        Map<String, Object> map = new HashMap<>();
        //查询item的信息
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item",tbItem);
        //查询item_desc的信息
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc",tbItemDesc.getItemDesc());
        //item_param_item
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        map.put("itemParamItem",tbItemParamItems.get(0).getParamData());
        //根据item中的cid查询item_cat表
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
        map.put("itemCat",tbItemCat.getName());
        return map;
    }

    @Override
    public void updateTbItem(TbItem tbItem, String desc, String itemParams) {
        //修改item表
        tbItem.setUpdated(new Date());
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        //修改item_desc表--根据itemId
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        //修改item_param_item表--根据itemid
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(tbItem.getId());
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(new Date());
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(tbItem.getId());
        tbItemParamItemMapper.updateByExampleSelective(tbItemParamItem,tbItemParamItemExample);
    }
    
}
