package com.powershop.controller;

import com.powershop.service.ItemCatService;
import com.powershop.utils.CatResult;
import com.powershop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend/itemCategory")
public class ItemCategoryController {

    @Autowired
    private ItemCatService itemCatService;


    @RequestMapping("/selectItemCategoryAll")
    public Result selectItemCategoryAll(){
        try {
            CatResult catResult = itemCatService.selectItemCategoryAll();
            return Result.ok(catResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }
}
