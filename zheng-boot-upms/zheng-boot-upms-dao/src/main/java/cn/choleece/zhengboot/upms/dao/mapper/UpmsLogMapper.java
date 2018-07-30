package cn.choleece.zhengboot.upms.dao.mapper;

import cn.choleece.zhengboot.upms.dao.model.UpmsLog;
import cn.choleece.zhengboot.upms.dao.model.UpmsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpmsLogMapper {
    long countByExample(UpmsLogExample example);

    int deleteByExample(UpmsLogExample example);

    int insert(UpmsLog record);

    int insertSelective(UpmsLog record);

    List<UpmsLog> selectByExampleWithBLOBs(UpmsLogExample example);

    List<UpmsLog> selectByExample(UpmsLogExample example);

    int updateByExampleSelective(@Param("record") UpmsLog record, @Param("example") UpmsLogExample example);

    int updateByExampleWithBLOBs(@Param("record") UpmsLog record, @Param("example") UpmsLogExample example);

    int updateByExample(@Param("record") UpmsLog record, @Param("example") UpmsLogExample example);
}