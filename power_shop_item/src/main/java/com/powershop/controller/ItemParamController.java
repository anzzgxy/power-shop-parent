package com.powershop.controller;

import com.powershop.pojo.TbItemParam;
import com.powershop.service.ItemParamService;
import com.powershop.utils.PageResult;
import com.powershop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 查询商品规格参数
     * @param itemCatId
     * @return
     */
    @RequestMapping("/selectItemParamByItemCatId/{itemCatId}")
    public Result selectItemParamByItemCatId(@PathVariable Long itemCatId){
        try {
            TbItemParam itemParam=itemParamService.selectItemParamByItemCatId(itemCatId);
            return Result.ok(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }


    /**
     * 查询所有商品规格参数
     * @return
     */
    @RequestMapping("/selectItemParamAll")
    public Result selectItemParamAll(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "100") Integer rows){
        try {
            PageResult pageResult= itemParamService.selectItemParamAll(page,rows);
            return Result.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }


    /**
     * 添加分组规格参数
     * @return
     */
    @RequestMapping("/insertItemParam")
    public Result insertItemParam(TbItemParam itemParam){
        try {
            itemParamService.insertItemParam(itemParam);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败");
        }
    }


    @RequestMapping("/deleteItemParamById")
    public Result deleteItemParamById(Long id){
        try {
            itemParamService.deleteItemParamById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }
}
