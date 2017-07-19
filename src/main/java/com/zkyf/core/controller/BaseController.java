package com.zkyf.core.controller;


import com.zkyf.common.Constant;
import com.zkyf.common.UtilFun;
import com.zkyf.core.service.IBaseService;
import com.zkyf.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2017/3/3.
 * Controller 模板
 */
public abstract class BaseController<M, PK extends Serializable> {

    protected IBaseService<M,PK> baseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected void setBaseDao (IBaseService<M,PK> baseService) {
        this.baseService = baseService;
    }

    /**
     * 获取page对象
     * @param page 页数
     * @return
     */
    protected PageRequest getPage(int page) {
        return new PageRequest(page, Constant.DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取session的user
     * @return
     */
    protected User getSessionUser() {
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getSession().getAttribute("user");
    }

    /**
     * 获取session
     * @return
     */
    protected Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    /**
     * 设置跳转到添加页面使需要携带的参数
     * @param model
     */
    protected void setAddPara (Model model) {}

    /**
     * 设置对象的属性
     * @param m
     * @param request
     */
    protected void setAddAttr (M m,HttpServletRequest request) {}

    /**
     * 设置添加页面的路径
     * @return
     */
    protected String setAddPage () {
        return this.getClass().getAnnotation(RequestMapping.class).value()[0] + "/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected String save(@RequestParam(defaultValue = "") PK id, Model model) {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<M> clazz = (Class<M>) pt.getActualTypeArguments()[0];
        try {
            M m = clazz.newInstance();

            model.addAttribute (id != null && baseService.findOne (id) != null ? baseService.findOne (id) :m);

            setAddPara (model);

            return setAddPage ();

        } catch (InstantiationException e) {
            e.printStackTrace();
            return "/error";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "/error";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    protected String save (@Valid M m,BindingResult result,Model model,HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return result.getAllErrors().get(0).getDefaultMessage();
            }
            setAddAttr (m,request);
            baseService.save (m);
            return Constant.RECEIPT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.RECEIPT_ERROR;
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    protected String delete(@RequestParam(defaultValue = "") PK id) {
        try {
            if(!UtilFun.isEmptyString(id.toString())){
                return Constant.RECEIPT_ERROR;
            }else{
                baseService.delete (id);
                return Constant.RECEIPT_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.RECEIPT_ERROR;
        }
    }
}
