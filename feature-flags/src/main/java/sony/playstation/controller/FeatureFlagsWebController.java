package sony.playstation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sony.playstation.service.FeatureFlagsService;
import sony.playstation.web.model.IdentityInfoPresentationModel;
import sony.playstation.web.model.InputCode;

@Controller
@RequestMapping(value="/sony")
public class FeatureFlagsWebController {

	@Autowired
	private FeatureFlagsService featureFlagsService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getFlagHomePage.do")
	public ModelAndView getFlagHomePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("FeatureHomePage");
		return mv;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getManageFeaturesForm.do")
	public ModelAndView getFlagManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		try {
			mv = new ModelAndView("ManageFeatures");
		}catch(Exception e) {
			
		}
		
		return mv;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method returns the object with object[0] contains status code of the HttpRequest and object[1] contains either the bitmap of current identity info or null
	 */
	@RequestMapping(value="/getCurrentIdentityInfo.do")
	public @ResponseBody IdentityInfoPresentationModel getCurrentIdentityInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		IdentityInfoPresentationModel returnModel = new IdentityInfoPresentationModel();
		try {
			returnModel = featureFlagsService.getCurrentInfo();
		}catch(Exception e) {
			returnModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			returnModel.setBitMap(null);
		}
		System.out.println("Total time for getCurrentIdentityInfo request is "+(System.currentTimeMillis() - startTime));
		return returnModel;
	}
	
	
	/**
	 * 
	 * @param inputCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveIdentityInfo.do")
	public @ResponseBody IdentityInfoPresentationModel saveIdentityInfo(@RequestBody InputCode inputCode, HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		IdentityInfoPresentationModel returnModel = new IdentityInfoPresentationModel();
		try {
			returnModel = featureFlagsService.saveIdentityInfo(inputCode);
		}catch(Exception e) {
			returnModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			returnModel.setBitMap(null);
		}
		System.out.println("Total time for saveIdentityInfo request is "+(System.currentTimeMillis() - startTime));
		return returnModel;
	}
}

