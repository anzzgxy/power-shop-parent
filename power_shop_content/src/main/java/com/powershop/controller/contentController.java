package com.powershop.controller;

import com.powershop.pojo.TbContent;
import com.powershop.pojo.TbContentCategory;
import com.powershop.service.ContentService;
import com.powershop.utils.PageResult;
import com.powershop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend/content")
public class contentController {

    @Autowired
    private ContentService contentService;

    /**
     * 内容分类管理  查询
     * @param id  内容id
     * @return
     */
    @RequestMapping("/selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(defaultValue = "0") Long id){
        try {
            List<TbContentCategory> contentCategoryList= contentService.selectContentCategoryByParentId(id);
            return Result.ok(contentCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }

    /**
     * 添加内容
     * @param tbContentCategory 内容实体类
     * @return
     */
    @RequestMapping(value = "/insertContentCategory",method = RequestMethod.POST)
    public Result insertContentCategory(TbContentCategory tbContentCategory){
        try {
            contentService.insertContentCategory(tbContentCategory);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败");
        }
    }

    /**
     * 根据内容主键删除
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/deleteContentCategoryById",method = RequestMethod.POST)
    public Result deleteContentCategoryById(Long categoryId){
        try {
            contentService.deleteContentCategoryById(categoryId);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }

    /**
     * 直接根据id修改name即可
     * @return
     */
    @RequestMapping(value = "/updateContentCategory",method = RequestMethod.POST)
    public Result updateContentCategory(TbContentCategory tbContentCategory){
        try {
            contentService.updateContentCategory(tbContentCategory);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改失败");
        }
    }

    /**
     * 查询内容
     * @param categoryId 内容类目
     * @param page 当前页
     * @param rows 每页条数
     * @return
     */
    @RequestMapping(value = "/selectTbContentAllByCategoryId",method = RequestMethod.POST)
    public Result selectTbContentAllByCategoryId(Long categoryId,
                                                 @RequestParam(defaultValue = "1")Integer page,
                                                 @RequestParam(defaultValue = "10")Integer rows){
        try {
            PageResult pageResult = contentService.selectTbContentAllByCategoryId(categoryId,page,rows);
            return Result.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
    }

    /**
     * 添加内容信息
     * @param tbContent 内容信息类
     * @return
     */
    @RequestMapping(value = "/insertTbContent",method = RequestMethod.POST)
    public Result insertTbContent(TbContent tbContent){
        try {
            contentService.insertTbContent(tbContent);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增失败");
        }
    }

    /**
     * 删除内容
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteContentByIds",method = RequestMethod.POST)
    public Result deleteContentByIds(Long ids){
        try {
            contentService.deleteContentByIds(ids);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }
}
