package cn.choleece.zhengboot.upms.dao.mapper;

import cn.choleece.zhengboot.upms.dao.model.UpmsRole;
import cn.choleece.zhengboot.upms.dao.model.UpmsRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpmsRoleMapper {
    long countByExample(UpmsRoleExample example);

    int deleteByExample(UpmsRoleExample example);

    int insert(UpmsRole record);

    int insertSelective(UpmsRole record);

    List<UpmsRole> selectByExample(UpmsRoleExample example);

    int updateByExampleSelective(@Param("record") UpmsRole record, @Param("example") UpmsRoleExample example);

    int updateByExample(@Param("record") UpmsRole record, @Param("example") UpmsRoleExample example);
}