package cn.choleece.zhengboot.upms.dao.mapper;

import cn.choleece.zhengboot.upms.dao.model.UpmsUserPermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpmsUserPermissionMapper {
    long countByExample(UpmsUserPermissionExample example);

    int deleteByExample(UpmsUserPermissionExample example);

    int insert(UpmsUserPermission record);

    int insertSelective(UpmsUserPermission record);

    List<UpmsUserPermission> selectByExample(UpmsUserPermissionExample example);

    int updateByExampleSelective(@Param("record") UpmsUserPermission record, @Param("example") UpmsUserPermissionExample example);

    int updateByExample(@Param("record") UpmsUserPermission record, @Param("example") UpmsUserPermissionExample example);
}