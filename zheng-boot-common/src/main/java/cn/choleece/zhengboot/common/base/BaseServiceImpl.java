package cn.choleece.zhengboot.common.base;

import cn.choleece.zhengboot.common.db.DataSourceEnum;
import cn.choleece.zhengboot.common.db.DynamicDataSource;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 实现BaseService的抽象类
 * Created by choleece on 2018/7/26.
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {

    private Mapper mapper;

    @Override
    public int countByExample(Example example) {
        try {
            DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getDefault());
            Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
            Object result = countByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByExample(Example example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Record record) {
        return 0;
    }

    @Override
    public int insertSelective(Record record) {
        return 0;
    }

    @Override
    public List<Record> selectByExampleWithBlobs(Example example) {
        return null;
    }

    @Override
    public List<Record> selectByExample(Example example) {
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Record> selectByExampleForStartPage(Example example, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBsForOffsetPage(Example example, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Record selectFirstByExample(Example example) {
        return null;
    }

    @Override
    public Record selectFirstByExampleWithBLOBs(Example example) {
        return null;
    }

    @Override
    public Record selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public Record selectByPrimaryKeyWithBLOBs(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example) {
        return 0;
    }

    @Override
    public int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example) {
        return 0;
    }

    @Override
    public int updateByExample(@Param("record") Record record, @Param("example") Example example) {
        return 0;
    }

    @Override
    public int updateByPrimayKeySelective(Record record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBS(Record record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        return 0;
    }

    @Override
    public void initMapper() {

    }
}
