package com.ruoyi.common.utils.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义类型转换器
 * 作用：
 * 1、数据存储时，自动将List集合，转为字符串(格式自定义)
 * 2、数据查询时，将查到的字符串再转为List集合
 *
 * @author wyt
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class List2VarcharHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        if (strings != null && !strings.isEmpty()) {
            String s = strings.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(";"));
            ps.setString(i, s);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public List<String> getResult(ResultSet rs, String s) throws SQLException {
        String str = rs.getString(s);
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(str.split(";"));
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet rs, int i) throws SQLException {
        String str = rs.getString(i);
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(str.split(";"));
        }
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement cs, int i) throws SQLException {
        String str = cs.getString(i);
        if (StringUtils.isNotBlank(str)) {
            return Arrays.asList(str.split(";"));
        }
        return null;
    }
}
