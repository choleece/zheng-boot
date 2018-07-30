package cn.choleece.zhengboot.upms.dao.mapper;

import cn.choleece.zhengboot.upms.dao.model.UpmsOrganization;
import cn.choleece.zhengboot.upms.dao.model.UpmsOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpmsOrganizationMapper {
    long countByExample(UpmsOrganizationExample example);

    int deleteByExample(UpmsOrganizationExample example);

    int insert(UpmsOrganization record);

    int insertSelective(UpmsOrganization record);

    List<UpmsOrganization> selectByExample(UpmsOrganizationExample example);

    int updateByExampleSelective(@Param("record") UpmsOrganization record, @Param("example") UpmsOrganizationExample example);

    int updateByExample(@Param("record") UpmsOrganization record, @Param("example") UpmsOrganizationExample example);
}