package cn.choleece.zhengboot.upms.rpc.service.mapper;


import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsRole;

import java.util.List;

/**
 * 用户VOMapper
 * Created by shuzheng on 2017/01/07.
 */
public interface UpmsApiMapper {

	/**
	 * 根据用户id获取所拥有的权限
	 * @param upmsUserId
	 * @return
	 */
	List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

	/**
	 * 根据用户id获取所属的角色
	 * @param upmsUserId
	 * @return
	 */
	List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
	
}