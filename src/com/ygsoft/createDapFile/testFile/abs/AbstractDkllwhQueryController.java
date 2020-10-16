package cn.csg.gmp.fm.electricity.controller.abs;

import org.springframework.beans.factory.annotation.Autowired;

import com.comtop.cap.runtime.base.controller.CapBaseController;

/**
 * 
 * @author tuzewen
 *
 */
public abstract class AbstractDkllwhQueryController extends CapBaseController {

	/**
	 * 注入facade层代码
	 */
	@Autowired
	private DkllwhQueryFacade facadeService;

	/**
	 * facade实例
	 */
	@Override
	protected DkllwhQueryFacade getCapBaseFacade() {
		return facadeService;
	}
}
