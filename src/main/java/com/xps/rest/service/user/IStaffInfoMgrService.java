package com.xps.rest.service.user;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xps.rest.bean.PageBean;
import com.xps.rest.entity.user.SysStaff;

public interface IStaffInfoMgrService {

	/**
	 * 通过登录名称查询对应的用户信息
	 * @param loginAccount
	 * @return SysStaff 系统用户信息
	 */
	public SysStaff querySysStaffByAccount(String loginAccount);

	public PageList querySysStaffInfoAsPage(PageBean pageBean);


}
