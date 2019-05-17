package com.company.common.db.mapper;

import com.company.common.db.mapper.condition.DynamicDataInserter;
import com.company.common.db.mapper.condition.DynamicDataUpdater;
import com.company.common.db.mapper.condition.DynamicQueryFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 动态数据通用映射器 <br>
 * Created by zheng.hao At 2018/11/5 13:31
 */
@Mapper
public interface DynamicDataCommonMapper {

    /**
     * 动态操作
     * 创建表、对字段的增删==
     */
    void exeSQL(@Param("exeSql") String exeSql);

    /**
     * 查询多条数据
     *
     * @param filter 查询条件等
     * @return 数据list
     */
    List<Map> queryObjs(@Param("filter") DynamicQueryFilter filter);

    /**
     * 查询单条数据
     *
     * @param filter 查询条件等
     * @return 数据
     */
    Map<String, Object> queryObj(@Param("filter") DynamicQueryFilter filter);

    /**
     * 向表中插入数据
     *
     * @param inserter 插入器
     */
    void insert(@Param("inserter") DynamicDataInserter inserter);

    /**
     * 修改
     */
    void update(@Param("updater") DynamicDataUpdater updater, @Param("filter") DynamicQueryFilter filter);

    /**
     * 删除文档
     *
     * @param filter DynamicQueryFilter
     */
    void delete(@Param("filter") DynamicQueryFilter filter);

}
