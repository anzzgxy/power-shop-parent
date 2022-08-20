package com.powershop.service;

import com.powershop.pojo.TbContent;
import com.powershop.pojo.TbContentCategory;
import com.powershop.utils.PageResult;

import java.util.List;

public interface ContentService {
    List<TbContentCategory> selectContentCategoryByParentId(Long id);

    void insertContentCategory(TbContentCategory tbContentCategory);

    void deleteContentCategoryById(Long categoryId);

    void updateContentCategory(TbContentCategory tbContentCategory);

    PageResult selectTbContentAllByCategoryId(Long categoryId, Integer page, Integer rows);

    void insertTbContent(TbContent tbContent);

    void deleteContentByIds(Long ids);
}
