package com.powershop.service;

import com.powershop.pojo.TbItem;
import com.powershop.utils.PageResult;

import java.util.Map;

public interface TbItemService {
    TbItem selectItemInfo(Long itemId);

    PageResult selectTbItemAllByPage(Integer page, Integer rows);

    void insertTbItem(TbItem tbItem,String desc,String itemParams);

    void deleteItemById(Long itemId);

    Map<String,Object> preUpdateItem(Long itemId);

    void updateTbItem(TbItem tbItem, String desc, String itemParams);
}
