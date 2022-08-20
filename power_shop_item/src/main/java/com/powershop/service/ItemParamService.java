package com.powershop.service;

import com.powershop.pojo.TbItemParam;
import com.powershop.utils.PageResult;

import java.util.List;

public interface ItemParamService {
    TbItemParam selectItemParamByItemCatId(Long itemCatId);

    PageResult selectItemParamAll(Integer page, Integer rows);

    void insertItemParam(TbItemParam itemParam);

    void deleteItemParamById(Long id);
}
