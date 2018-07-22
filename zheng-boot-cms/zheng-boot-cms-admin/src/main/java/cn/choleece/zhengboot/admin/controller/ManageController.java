package cn.choleece.zhengboot.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台controller
 * Created by choleece on 2018/7/21.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @GetMapping("/index")
    String index() {
        logger.info("redirect into manage");

        return "";
    }
}
