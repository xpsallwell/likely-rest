package com.xps.rest.dao.user;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xps.rest.entity.user.SysStaff;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ISysStaffDao {

    /**
     * 通过ID,或登录名查询用户的信息
     * @param conditions
     * @return
     */
    public PageList querySysStaffInfoAsPage(Map<String, Object> conditions, PageBounds pageBounds );


    SysStaff querySysStaffInfo(SysStaff staff);

    int deleteById(Long staffId);

    int insert(SysStaff staff);

    int updateById(SysStaff staff);
}