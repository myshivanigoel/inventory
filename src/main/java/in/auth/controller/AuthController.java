/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.auth.controller;

import in.auth.user.service.UserService;
import in.db.auth.entity.MstUser;
import in.db.auth.entity.Tocken;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author anuja
 */
@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/")
    public String home() {
        //return "home";
        return "redirect:/dashboard";
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "message", required = false) String message, Model model) {

        //return "home";
        return "redirect:/dashboard";
    }

    @GetMapping("forgot-password")
    public String forgotPassword(Model model) {
        return "forgot-password";
    }

    @PostMapping("forgot-password")
    public String sendOtp(@RequestParam("username") String userName, Model model) {
        ResultDataMap result = new ResultDataMap();
        MstUser user = userService.getUserByIdOrEmailOrMobile(userName);
        if (user != null) {
            result = userService.sendOtp(user);
            model.addAttribute("message", Strings.OtpSent);

            return "redirect:verify-otp";

        } else {
            model.addAttribute("message", Strings.EmailNotRegistered);
            return "forgot-password";
        }

    }

    @GetMapping("verify-otp")
    public String verifyOtp(Model model) {
        return "verify-otp";
    }

    @PostMapping("reset-password")
    public String resetPassword(@RequestParam("otp") String otp,
            @RequestParam("password") String password,
            Model model) {
        Tocken tocken = userService.ifValidTocken(otp, Strings.PasswordResetTocken);
        if (otp.trim().equals("")) {
            model.addAttribute("message", "Empty OTP not allowed");
            return "verify-otp";
        }else if(tocken == null)
        {
            model.addAttribute("message", "Invalid Tocken");
                    return "verify-otp";
        }else if(password.trim().equals(""))
        {
            model.addAttribute("message", "Empty password not allowed");
                    return "verify-otp";
        }else{
             ResultDataMap result = userService.resetPassword(otp, password);
                    model.addAttribute("message", result.getMessage());
                    
                    return "success-message";

        }
    }

}
