package com.xps.rest.service.user;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import com.xps.rest.bean.PageBean;
import com.xps.rest.dao.user.ISysStaffDao;
import com.xps.rest.entity.user.SysStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffInfoMgrServiceImpl implements IStaffInfoMgrService {

	@Autowired
	private ISysStaffDao sysStaffDao;


	@Override
	public SysStaff querySysStaffByAccount(String loginAccount) {
		if(loginAccount == null || "".equals(loginAccount)) {
			return null;
		}
		SysStaff staff = new SysStaff();
		staff.setLoginAccount(loginAccount);
		staff = sysStaffDao.querySysStaffInfo(staff);
		return staff;
	}

	@Override
	public PageList querySysStaffInfoAsPage(PageBean pageBean) {
		PageBounds pb = pageBean.getPageBounds();
		PageList list = sysStaffDao.querySysStaffInfoAsPage(pageBean.getConditions(),pageBean.getPageBounds());
		return list;
	}

}
