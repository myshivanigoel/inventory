/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.masters.controller;

import in.db.inventory.entity.Classification;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.inventory.service.ItemService;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author anuja
 */
@Controller
@RequestMapping("/masters/item/")
public class ItemMasterController {

    ResultDataMap result=new ResultDataMap();

    @Autowired
    ItemService itemService;

    @GetMapping("add")
    public String addItemForm(@RequestParam(name = "message", required = false) String message, Model model) {
        model.addAttribute("message", message);
        List list=itemService.getAllItemsList();
        System.out.println("in.inventory.masters.controller.ItemMasterController.addItemForm()"+list);
        model.addAttribute("itemList", list);
        
        model.addAttribute("classList", itemService.getClassificationList());
        model.addAttribute("item", new ItemMaster());

        return "item-master";
    }

    @PostMapping("add")
    public String addItemMasterSave(@ModelAttribute("item") ItemMaster item, Model model) {
        model.addAttribute("classList", itemService.getClassificationList());
        System.out.println("in.inventory.masters.controller.ItemMasterController.addItemSave()" + item);
        
        if (item == null) {
             model.addAttribute("item", item);
            model.addAttribute("message", Strings.InvalidData);
            return "item-master";
        } else if (item.getItemGroup().getClassification() == null
                || item.getItemGroup().getClassification().getClassificationId() == null
                || item.getItemGroup().getClassification().getClassificationId() == 0) {
            model.addAttribute("item", item);
            model.addAttribute("message", "Please select Classification");
            return "item-master-update";
        } else if (item.getItemGroup() == null
                || item.getItemGroup().getGroupId() == null
                || item.getItemGroup().getGroupId() == 0) {

            model.addAttribute("item", item);
            model.addAttribute("message", "Please select Group ");
            return "item-master-update";
        } else if ( item.getItemName() == null
                     || item.getItemName().trim().equals("")) {
            model.addAttribute("item", item);
            model.addAttribute("message", "item Name can not be empty ");
            return "item-master-update";

        } else {
             result = itemService.saveItem(item);
            if (result.getStatus()) {
                return "redirect:add?message=" + result.getMessage();
            } else {
                model.addAttribute("item", item);
                model.addAttribute("message", Strings.error);
                return "item-master";
            }
        }

    }

    @GetMapping("update/{itemId}")
    public String updateItemForm(@PathVariable("itemId") Integer itemId,
            @RequestParam(name = "message", required = false) String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("itemList", itemService.getAllItemsList());
        model.addAttribute("classList", itemService.getClassificationList());

        ItemMaster item = itemService.getItemById(itemId);
        if (item == null) {
            model.addAttribute("item", new ItemMaster());
            model.addAttribute("message", Strings.InvalidData);
            return "item-master";
        } else if (!itemService.updateAble(item)) {
            model.addAttribute("item", new ItemMaster());
            model.addAttribute("message", Strings.ItemInUse);
            return "item-master";
        } else {
            model.addAttribute("groupList", itemService.getGroupListByCLassification(item.getItemGroup().getClassification().getClassificationId()));
            model.addAttribute("item", item);
            return "item-master-update";
        }

    }

    @PostMapping("update")
    public String updateItemSave(@ModelAttribute("item") ItemMaster item, Model model) {
        System.out.println("in.inventory.masters.controller.ItemMasterController.addItemSave()" + item);

        model.addAttribute("itemList", itemService.getAllItemsList());
        if (item == null) {
             model.addAttribute("item", item);
            model.addAttribute("message", Strings.InvalidData);
            return "item-master-update";
        } else if (item.getItemGroup().getClassification() == null
                || item.getItemGroup().getClassification().getClassificationId() == null
                || item.getItemGroup().getClassification().getClassificationId() == 0) {
            model.addAttribute("item", item);
            System.out.println("in.inventory.masters.controller.ItemMasterController.addItemSave()" + item);

            model.addAttribute("message", "please select Classification");
            return "item-master-update";
        } else if (item.getItemGroup() == null
                || item.getItemGroup().getGroupId() == null
                || item.getItemGroup().getGroupId() == 0) {

            model.addAttribute("item", item);
            model.addAttribute("message", "Please select Group ");
            return "item-master-update";
        } else if (item.getItemId() == null
                || item.getItemName() == null
                || item.getItemName().trim().equals("")) {
            model.addAttribute("item", item);
            model.addAttribute("message", "item Name can not be empty ");
            return "item-master-update";

        } else if (!itemService.updateAble(item)) {
            model.addAttribute("item", item);
            model.addAttribute("message", Strings.ItemInUse);
            return "item-master-update";
        } else {
            result = itemService.saveItem(item);
            if (result.getStatus()) {
                System.out.println("in.inventory.masters.controller.ItemMasterController.addItemSave()" + item);

                return "redirect:add?message=" + result.getMessage();
            } else {
                model.addAttribute("item", item);
                model.addAttribute("classList", itemService.getClassificationList());
                model.addAttribute("message", Strings.error);
                return "item-master-update";
            }
        }

    }

    @PostMapping("activate/{itemId}")
    public @ResponseBody
    ResultDataMap activateItemSave(@PathVariable("itemId") Integer itemId, Model model) {
        model.addAttribute("itemList", itemService.getAllGroups());
        model.addAttribute("classList", itemService.getClassificationList());
        if (itemId == null) {

            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        } else if (!itemService.updateAble(new ItemMaster(itemId))) {
            model.addAttribute("item", new ItemMaster());
            model.addAttribute("message", Strings.ItemInUse);
            return result.setStatus(Boolean.FALSE).setMessage(Strings.ItemInUse);
        } else {
            ItemMaster item = new ItemMaster();
            item.setItemId(itemId);
            item.setActiveFlag('Y');
            return result = itemService.saveItem(item);

        }
    }

    @PostMapping("deactivate/{itemId}")
    public @ResponseBody
    ResultDataMap deactivateItemSave(@PathVariable("itemId") Integer itemId, Model model) {

        if (itemId == null) {

            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        } else if (!itemService.updateAble(new ItemMaster(itemId))) {
            model.addAttribute("item", new ItemMaster());
            model.addAttribute("message", Strings.ItemInUse);
            return result.setStatus(Boolean.FALSE).setMessage(Strings.ItemInUse);
        } else {
            ItemMaster item = new ItemMaster();
            item.setItemId(itemId);
            item.setActiveFlag('N');
            return result = itemService.saveItem(item);

        }
    }

}
