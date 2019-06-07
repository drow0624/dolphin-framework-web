package com.lsl.nature.common.db.mapper;

import com.lsl.nature.common.db.mapper.condition.QueryFilter;
import com.lsl.nature.common.db.mapper.pojo.DBRow;
import com.lsl.nature.common.db.mapper.pojo.DBUpdater;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonMapper {

    /**
     * 插入一条记录
     *
     * @param row
     */
    void insert(DBRow row);

    /**
     * 插入多条记录
     *
     * @param rows
     */
    void insertRows(@Param("list") List<DBRow> rows);

    /**
     * 查询数据
     *
     * @param filter
     * @return
     */
    List<Map<String,Object>> select(@Param("filter") QueryFilter filter);

    /**
     * 更新数据库数据
     *
     * @param updater
     * @param filter
     */
    void update(@Param("updater") DBUpdater updater, @Param("filter") QueryFilter filter);

    /**
     * 删除记录
     *
     * @param filter
     */
    void delete(@Param("filter") QueryFilter filter);

    /**
     * 统计记录数
     *
     * @param filter
     * @return
     */
    Long count(@Param("filter") QueryFilter filter);

}
