package com.zkyf.ctrl;



import com.zkyf.core.controller.BaseController;
import com.zkyf.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2017/4/21.
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController<User,String>{
private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Override
    protected String setAddPage() {
        return "/user/user_add";
    }

    @Override
    protected void setAddPara(Model model) {
    }

    @GetMapping(value = "/list")
    public String List(Model model, @RequestParam(defaultValue = "0") int pageNumber){
        Page<User> page =baseService.findAll(getPage(pageNumber));
        model.addAttribute(page);
        model.addAttribute("baseurl","/user");
        return "/user/list";
    }
}
