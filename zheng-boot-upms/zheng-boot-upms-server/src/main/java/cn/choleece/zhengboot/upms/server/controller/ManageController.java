package cn.choleece.zhengboot.upms.server.controller;

import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystem;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystemExample;
import cn.choleece.zhengboot.upms.dao.model.UpmsUser;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsApiService;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author choleece
 * @description: 后台管理controller
 * @date 2018/8/3 18:44
 */
@RestController
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController {

    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private IUpmsSystemService systemService;
    @Autowired
    private IUpmsApiService iUpmsApiService;

    @ApiOperation(value = "后台首页")
    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        // 已注册系统
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andStatusEqualTo((byte)1);
        List<UpmsSystem> upmsSystems = systemService.selectByExample(upmsSystemExample);
        modelMap.put("upmsSystem", upmsSystems);
        // 当前登录用户权限
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser user = iUpmsApiService.selectUpmsUserByUsername(username);
        List<UpmsPermission> upmsPermissions = iUpmsApiService.selectUpmsPermissionByUpmsUserId(user.getUserId());
        modelMap.put("upmsPermissions", upmsPermissions);
        return "/manage/index";
    }

}
