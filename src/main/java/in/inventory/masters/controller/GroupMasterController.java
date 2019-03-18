/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.masters.controller;

import in.db.inventory.entity.Classification;
import in.db.inventory.entity.MstGroup;
import in.inventory.service.ItemService;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
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
@RequestMapping("/masters/group/")
public class GroupMasterController {
    
    ResultDataMap result;
    
    @Autowired
    ItemService itemService;
    
    @GetMapping("add")
    public String addGroupForm(@RequestParam(name="message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
        model.addAttribute("groupList",itemService.getAllGroups());
          model.addAttribute("classList",itemService.getClassificationList());
        model.addAttribute("group",new MstGroup());
        
        return "group-master";
    }
    
    @PostMapping("add")
    public String addMstGroupSave(@ModelAttribute("group")MstGroup group,Model model)
    {
          model.addAttribute("classList",itemService.getClassificationList());
         model.addAttribute("groupList",itemService.getAllGroups());
        System.out.println("in.inventory.masters.controller.ItemMasterController.addGroupSave()"+group);
        if(group==null 
                || group.getClassification()==null 
                || group.getClassification().getClassificationId()==null 
                || group.getClassification().getClassificationId()==0
                 ||  group.getGroupName()==null
                || group.getGroupName().trim().equals(""))
        {
             model.addAttribute("group",group);
           System.out.println("in.inventory.masters.controller.ItemMasterController.addGroupSave()"+group);
            model.addAttribute("message", Strings.InvalidData);
            return "group-master";
        }else{
                System.out.println("in.inventory.masters.controller.ItemMasterController.addGroupSave()"+group);
            result=itemService.saveGroup(group);
            if(result.getStatus())
            {
                return "redirect:add?message="+result.getMessage();
            }else{
                 model.addAttribute("group",group);
       
            model.addAttribute("message", Strings.error);
            return "group-master";
            }
        }
        
       
       
    }
    
    
      @GetMapping("update/{groupId}")
    public String updateGroupForm(@PathVariable("groupId")Integer groupId,
                                            @RequestParam(name="message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
        model.addAttribute("groupList",itemService.getAllGroups());
        MstGroup group=itemService.getGroupById(groupId);
        if(group==null)
        {
             model.addAttribute("group",new MstGroup());
              model.addAttribute("classList",itemService.getClassificationList());
              model.addAttribute("message", Strings.InvalidData);
              return "group-master";
        }else if(!itemService.updateAble(group))
        {
              model.addAttribute("group",new MstGroup());
              model.addAttribute("classList",itemService.getClassificationList());
              model.addAttribute("message", Strings.GroupInUse);
             return "group-master-update";
        }else{
             model.addAttribute("group",group);
               model.addAttribute("classList",itemService.getClassificationList());
              return "group-master-update";
        }
       
    }
    
     @PostMapping("update")
    public String updateGroupSave(@ModelAttribute("group")MstGroup group,Model model)
    {
           System.out.println("in.inventory.masters.controller.ItemMasterController.addGroupSave()"+group);
           
         model.addAttribute("groupList",itemService.getAllGroups());
          model.addAttribute("classList",itemService.getClassificationList());
        if(group==null 
                 ||  group.getGroupName()==null
                || group.getGroupName().trim().equals(""))
        {
             model.addAttribute("group",group);
            model.addAttribute("message", Strings.InvalidData);
            return "group-master-update";
        }else if(!itemService.updateAble(group))
        {
              model.addAttribute("group",group);
              model.addAttribute("classList",itemService.getClassificationList());
              model.addAttribute("message", Strings.GroupInUse);
             return "group-master-update";
        }else{
             result=itemService.saveGroup(group);
            if(result.getStatus())
            {
                 return "redirect:add?message="+result.getMessage();
            }else{
                 model.addAttribute("group",group);
                    model.addAttribute("classList",itemService.getClassificationList());
                  model.addAttribute("message", Strings.error);
            return "group-master-update";
            }
        }
        
       
       
    }
    
    
     @PostMapping("activate/{groupId}")
    public @ResponseBody ResultDataMap activateGroupSave(@PathVariable("groupId")Integer groupId,Model model)
    {
         model.addAttribute("groupList",itemService.getAllGroups());
          model.addAttribute("classList",itemService.getClassificationList());
        if(groupId==null)
        {
          
            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        }else if(!itemService.updateAble(new MstGroup(groupId)))
        {
              return result.setStatus(Boolean.FALSE).setMessage(Strings.GroupInUse);
        }else{
               MstGroup group=new MstGroup();
               group.setGroupId(groupId);
               group.setActiveFlag('Y');
               return result=itemService.saveGroup(group);
            
            }
        }
        
        @PostMapping("deactivate/{groupId}")
    public @ResponseBody ResultDataMap deactivateGroupSave(@PathVariable("groupId")Integer groupId,Model model)
    {
        
        if(groupId==null)
        {
          
            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        }else if(!itemService.updateAble(new MstGroup(groupId)))
        {
              return result.setStatus(Boolean.FALSE).setMessage(Strings.GroupInUse);
        }else{
               MstGroup group=new MstGroup();
               group.setGroupId(groupId);
               group.setActiveFlag('N');
               return result=itemService.saveGroup(group);
            
            }
        }
        
       
       
    
}
