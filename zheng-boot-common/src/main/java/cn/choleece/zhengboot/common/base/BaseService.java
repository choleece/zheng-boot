package cn.choleece.zhengboot.common.base;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author choleece
 * @description: BaseService接口
 * @date 2018/7/26 17:44
 */
public interface BaseService<Record, Example> {

    /**
     * 根据条件查询记录数量
     * @param example
     * @return
     */
    int countByExample(Example example);

    /**
     * 根据条件删除记录
     * @param example
     * @return
     */
    int deleteByExample(Example example);

    /**
     * 根据主键删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 插入记录有效字段
     * @param record
     * @return
     */
    int insertSelective(Record record);

    /**
     * 根据条件查询记录，附带blob字段
     * @param example
     * @return
     */
    List<Record> selectByExampleWithBlobs(Example example);

    /**
     * 根据条件查询记录
     * @param example
     * @return
     */
    List<Record> selectByExample(Example example);

    /**
     * 根据条件查询记录并按页面分页，附带blob字段
     * @param example 条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize);

    /**
     * 根据条件查询记录并按页面分页
     * @param example 条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize);

    /**
     * 根据条件查询记录并按最后记录数分页，附带blob字段
     * @param example 条件
     * @param offset 跳过数量
     * @param limit 查询数量
     * @return
     */
    List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit);

    /**
     * 根据条件查询记录并按最后记录数分页
     * @param example 条件
     * @param offset 跳过数量
     * @param limit 查询数量
     * @return
     */
    List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit);

    /**
     * 根据条件查询第一条记录
     * @param example
     * @return
     */
    Record selectFirstByExample(Example example);

    /**
     * 根据条件查询第一条记录，附带blob字段
     * @param example
     * @return
     */
    Record selectFirstByExampleWithBLOBs(Example example);

    /**
     * 根据主键查询记录
     * @param id
     * @return
     */
    Record selectByPrimaryKey(Integer id);

    /**
     * 根据主键查询记录，附带blob字段
     * @param id
     * @return
     */
    Record selectByPrimaryKeyWithBLOBs(Integer id);

    /**
     * 根据条件更新有效字段
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    /**
     * 根据条件更新有效字段，附带blob字段
     * @param record
     * @param example
     * @return
     */
    int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example);

    /**
     * 根据条件更新记录
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Record record, @Param("example") Example example);

    /**
     * 根据主键更新有效字段
     * @param record
     * @return
     */
    int updateByPrimayKeySelective(Record record);

    /**
     * 根据主键更新有效字段，附带BLOB字段
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBS(Record record);

    /**
     * 根据主键更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Record record);

    /**
     * 根据主键批量删除记录
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(String ids);

    /**
     * 初始化mapper
     */
    void initMapper();
}
