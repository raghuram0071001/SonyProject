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

/*
 * 1) Controller that handles the requests from the WEB UI. The request mapping starts with /sony
 */
@Controller
@RequestMapping(value="/sony")
public class FeatureFlagsWebController {

	@Autowired
	private FeatureFlagsService featureFlagsService;
	
	/**
	 * This controller returns a FeatureFlag Home Page on starting the project
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
	 * This controller returns the ManagerFeaturesForm
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
			System.out.println("Error Occurred "+e);
		}
		
		return mv;
	}

	/**
	 * 
	 * This Controller method makes a request to the FeatureFlag Service for avaialable current Identity_Information and returns IdentityInfoPresentationModel model
	 * This return model contains BitMap that maps bits to the region
	 * This return model also contains the Status Code 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
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
	 * This Controller method makes a request to the FeatureFlag Service for avaialable current Identity_Information and returns IdentityInfoPresentationModel model
	 * This return model contains BitMap that maps bits to the region
	 * This return model also contains the Status Code 
	 * 
	 * This Controller method accepts InputCode model that contains BitMap and returns the Saved current Identity_Information
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

