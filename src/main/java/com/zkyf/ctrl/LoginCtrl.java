package com.zkyf.ctrl;

import com.zkyf.core.controller.BaseController;
import com.zkyf.entity.User;
import com.zkyf.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class LoginCtrl extends BaseController<User, String> {
    @Resource
    private IUserService userService;

    @GetMapping(value = "/")
    public String index() {
        return "/admin";
    }


    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute(new User());
        return "/login";
    }

    @PostMapping(value = "/login")
    public String login(User user, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getSalting(), true);

        Subject subject = SecurityUtils.getSubject();

        String errmsg = null;
        try {
            subject.login(token);
        } catch (Exception e) {
            errmsg = "用户名或密码错误";
        }
        if (subject.isAuthenticated()) {
            subject.getSession().setAttribute("user", userService.getByuserName(user.getUserName()));
            return "redirect:/";
        } else {
            model.addAttribute(user);
            model.addAttribute("errmsg", errmsg);
            return "/login";
        }

    }

    @PostMapping(value = "/register")
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("loginModel", "register");
            return "/login";
        }
        user.setPassWord(user.getSalting());
        userService.save(user);
        return "/login";
    }

    @GetMapping(value = "/loginOut")
    public String loginOut() {

        SecurityUtils.getSubject().getSession().removeAttribute("user");
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
