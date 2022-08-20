package com.powershop.controller;

import com.powershop.pojo.TbItem;
import com.powershop.service.TbItemService;
import com.powershop.utils.PageResult;
import com.powershop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
    @RequestMapping("/backend/item")
public class ItemController {

    @Autowired
    private TbItemService itemService;

    @RequestMapping("/selectItemInfo")
    public TbItem selectItemInfo(Long itemId){
        try {
            return itemService.selectItemInfo(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分页查询
     * @param page 当前页
     * @param rows 每页条数
     * @return
     */
    @RequestMapping("/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer rows){
        try {
            PageResult pageResult = itemService.selectTbItemAllByPage(page,rows);
            return Result.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查找失败");
        }
    }

    /**
     * 添加商品信息
     * @param tbItem 商品信息类
     * @param desc  商品描述信息
     * @param itemParams 商品参数
     * @return
     */
    @PostMapping("/insertTbItem")
    public Result insertTbItem(TbItem tbItem,String desc,String itemParams){
        try {
            itemService.insertTbItem(tbItem,desc,itemParams);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败");
        }
    }

    /**
     * 删除商品
     * @param itemId 商品id
     * @return
     */
    @PostMapping("/deleteItemById")
    public Result deleteItemById(@RequestParam Long itemId){
        try {
            itemService.deleteItemById(itemId);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }

    /**
     * 要修改的商品信息
     * @param itemId 商品id
     * @return
     */
    @PostMapping("/preUpdateItem")
    public Result preUpdateItem(@RequestParam Long itemId){
        try {
            Map<String,Object> map = itemService.preUpdateItem(itemId);
            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }

    /**
     * 修改商品信息
     * @param tbItem 商品信息类
     * @param desc  商品描述信息
     * @param itemParams 商品参数
     * @return
     */
    @RequestMapping("/updateTbItem")
    public Result updateTbItem(TbItem tbItem,String desc,String itemParams){
        try {
            itemService.updateTbItem(tbItem,desc,itemParams);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改失败");
        }
    }
}
