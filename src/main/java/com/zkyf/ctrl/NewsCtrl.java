package com.zkyf.ctrl;

import com.zkyf.core.controller.BaseController;
import com.zkyf.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wanghuiwen on 17-7-18.
 */
@Controller
@RequestMapping(value = "/news")
public class NewsCtrl extends BaseController<News,String> {

    @GetMapping(value = "/list")
    public String list(Model model,@RequestParam(defaultValue = "0") int pageNumber){
        Page<News> news=baseService.findAll(getPage(pageNumber));
        model.addAttribute(news);
        return "/news/list";
    }
}
