package com.zkyf.service.impl;

import com.zkyf.core.service.impl.BaseServiceImpl;
import com.zkyf.entity.News;
import com.zkyf.service.INewService;
import org.springframework.stereotype.Service;

/**
 * Created by wanghuiwen on 17-7-18.
 */
@Service("newService")
public class NewServiceImpl extends BaseServiceImpl<News, String> implements INewService {
}
