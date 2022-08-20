package com.powershop.service;

import com.powershop.utils.CatResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("power-shop-item")
@RequestMapping("/backend/itemCategory")
public interface ItemCatService {

    @RequestMapping("/selectItemCategoryAll")
    CatResult selectItemCategoryAll();
}
