package com.ayao.product_service.controller;

import com.ayao.product_service.dataobject.ItemDo;
import com.ayao.product_service.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/23 19:31
 * @version:
 */
@Controller
@RequestMapping("/item")
public class ItemController{
  @Resource
  private ItemService itemService;

  @PostMapping("/create")
  @ResponseBody
  public int createItem(@RequestParam(name = "title") String title,
                        @RequestParam(name = "description") String description,
                        @RequestParam(name = "price") Double price,
                        @RequestParam(name = "imgUrl") String imgUrl,
                        @RequestParam(name = "sales") Integer sales){
    ItemDo itemModel = new ItemDo();
    itemModel.setTitle(title);
    itemModel.setDescription(description);
    itemModel.setPrice(price);
    itemModel.setImgUrl(imgUrl);
    itemModel.setSales(sales);
    return itemService.createItem(itemModel);
  }
  @GetMapping("/listitem")
  @ResponseBody
  public List<ItemDo> listItem(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "4") Integer pageSize){
    List<ItemDo> itemModelList = itemService.listItemByPage(pageNum,pageSize);

    return itemModelList;
  }

  @GetMapping("/query")
  @ResponseBody
  public ItemDo findById(@RequestParam(name = "itemId") Integer itemId){
    return itemService.getItemById(itemId);
  }

}
