package cn.csg.gmp.fm.electricity.facade.abs;

import javax.annotation.Resource;

import com.comtop.cap.runtime.base.facade.CapBaseFacade;

/**
 * 
 * @author tuzewen
 *
 */
public abstract class AbstractDkllwhQueryFacade extends CapBaseFacade {

	/**
	 * 注入AppService层代码
	 */
	@Resource(name = "dkllwhQueryFacade")
	private DkllwhQueryAppService appService;

	/**
	 * 获取业务AppService
	 * 
	 * @return AppService实例
	 */
	@Override
	protected DkllwhQueryAppService getAppService() {
		return appService;
	}
}
