package com.powershop.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powershop.mapper.TbContentCategoryMapper;
import com.powershop.mapper.TbContentMapper;
import com.powershop.pojo.TbContent;
import com.powershop.pojo.TbContentCategory;
import com.powershop.pojo.TbContentCategoryExample;
import com.powershop.pojo.TbContentExample;
import com.powershop.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public List<TbContentCategory> selectContentCategoryByParentId(Long id) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> contentCategoryList = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        return contentCategoryList;
    }

    @Override
    public void insertContentCategory(TbContentCategory tbContentCategory) {
        //新增后,将父节点的is_parent属性改为true
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setIsParent(false);
        tbContentCategoryMapper.insert(tbContentCategory);

        //当传过来的parent_id  等于 主键id时,获取父节点信息
        TbContentCategory tbContentCategory1 = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        if (!tbContentCategory1.getIsParent()){
            tbContentCategory1.setIsParent(true);
            tbContentCategory.setUpdated(new Date());
            //修改信息
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory1);
        }
    }

    @Override
    public void deleteContentCategoryById(Long categoryId) {
        //判断是否是父节点,是的话,无法删除
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(categoryId);
        Boolean isParent = tbContentCategory.getIsParent();
        if (isParent){
            throw new RuntimeException();
        }
        //删除类目下的所有内容
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        tbContentMapper.deleteByExample(tbContentExample);
        //删除节点
        tbContentCategoryMapper.deleteByPrimaryKey(categoryId);
        //判断父节点下是否还有节点,查询所有parent_id为categoryId的
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria1 = tbContentCategoryExample.createCriteria();
        criteria1.andParentIdEqualTo(categoryId);
        List<TbContentCategory> contentCategoryList = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //==0,表示父节点下没有子节点
        if (contentCategoryList.size()==0){
            //将父节点的属性改为false
            TbContentCategory contentCategory = new TbContentCategory();
            contentCategory.setId(tbContentCategory.getParentId());
            contentCategory.setIsParent(false);
            contentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        }
    }

    @Override
    public void updateContentCategory(TbContentCategory tbContentCategory) {
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    @Override
    public PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows) {
        //开启分页
        PageHelper.startPage(page,rows);
        //查询数据
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContentList = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
        //封装分页数据
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContentList);
        return new PageResult(pageInfo.getPageNum(),pageInfo.getPages(),pageInfo.getList());
    }

    @Override
    public void insertTbContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
    }

    @Override
    public void deleteContentByIds(Long ids) {
        tbContentMapper.deleteByPrimaryKey(ids);
    }
}
