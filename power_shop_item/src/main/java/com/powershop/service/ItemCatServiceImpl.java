package com.powershop.service;

import com.powershop.mapper.TbItemCatMapper;
import com.powershop.pojo.TbItemCat;
import com.powershop.pojo.TbItemCatExample;
import com.powershop.pojo.TbItemExample;
import com.powershop.utils.CatNode;
import com.powershop.utils.CatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItemCat> selectItemCategoryByParentId(Long id) {
        //根据父id查询,需要条件查询,用TbItemCatExample
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
        return tbItemCatMapper.selectByExample(tbItemCatExample);
    }

    @Override
    public CatResult selectItemCategoryAll() {
        CatResult catResult = new CatResult();
        catResult.setData(this.getCatList(0L));
        return catResult;
    }

    private List<?> getCatList(Long parentId){
        //首先查询父节点为0的,这是一级目录
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> oneList = tbItemCatMapper.selectByExample(tbItemCatExample);
        List resultList = new ArrayList();
        //
        for (TbItemCat tbItemCat : oneList) {
            if (tbItemCat.getIsParent()){
                //不是父节点
                CatNode catNode = new CatNode();
                catNode.setName(tbItemCat.getName());
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
            }else {
                //是父节点,继续根据id查
                resultList.add(tbItemCat.getName());
            }
        }
        return resultList;

    }
}






















/*
//在查询父节点为刚刚查出来的,这是二级目录
        List<CatNode> catNodeList = new ArrayList<>();
        for (TbItemCat tbItemCat : oneList) {
            CatNode oneCatNode = new CatNode();
            //一级目录节点名字
            oneCatNode.setName(tbItemCat.getName());
            //再根据一级节点的主键id,查询所有parent_id相同的,获得二级目录
            criteria.andParentIdEqualTo(tbItemCat.getId());
            List<TbItemCat> twoList = tbItemCatMapper.selectByExample(tbItemCatExample);

            for (TbItemCat itemCat : twoList) {
                CatNode twoCatNode = new CatNode();
                //二级目录节点名字
                twoCatNode.setName(itemCat.getName());
                //重复第二步
                criteria.andParentIdEqualTo(itemCat.getId());
                List<TbItemCat> threeList = tbItemCatMapper.selectByExample(tbItemCatExample);

                for (TbItemCat cat : threeList) {
                    // CatNode threeCatNode = new CatNode();
                    //三级目录节点名字
                    // threeCatNode.setName(cat.getName());
                    List<Object> list = new ArrayList<>();
                    list.add(cat.getName());
                    twoCatNode.setItem(list);
                }
                oneCatNode.getItem().add(twoCatNode);
            }
            catNodeList.add(oneCatNode);
        }
        return catNodeList;
 */