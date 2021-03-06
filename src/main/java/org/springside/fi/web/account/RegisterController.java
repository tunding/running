package org.springside.fi.web.account;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.fi.entity.Runner;
import org.springside.fi.rest.RestErrorCode;
import org.springside.fi.service.account.AccountService;
import org.springside.fi.web.params.account.CheckLoginNameParam;
import org.springside.fi.web.running.BaseController;
import org.springside.fi.web.vo.account.CheckLoginNameVo;
import org.springside.modules.mapper.JsonMapper;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController extends BaseController{

	@Autowired
	private AccountService accountService;
	
	protected JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	protected HashMap<String, Object> map = new HashMap<String, Object>();

/*	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}*/

	@ResponseBody	
	//@RequestMapping(method = RequestMethod.POST)
	@RequestMapping()
	public String register(@Valid Runner user) {
		try{
			accountService.registerUser(user);
			map.put("result", "success");
			map.put("data", user.getLoginName()+" register success");
		}catch(RuntimeException e){
			e.printStackTrace();
			map.put("result", "failed");
			map.put("data", e.getMessage());
		}
		return jsonMapper.toJson(map);
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "/checkLoginName")
	@ResponseBody
	public String checkLoginName(@Valid CheckLoginNameParam checkLoginNameParam, BindingResult bindResult) {
		CheckLoginNameVo checkLoginNameVo = new CheckLoginNameVo();
		if(bindResult.hasErrors()){
			bindErrorRes(bindResult, checkLoginNameVo);//参数绑定异常，loginName为空
		}else{
			String loginName = checkLoginNameParam.getLoginName();
			if (accountService.findUserByLoginName(loginName) == null) {
				checkLoginNameVo.setData("true");
			} else {
				checkLoginNameVo.setData("false");
			}
		}
		return jsonMapper.toJson(checkLoginNameVo);
	}
}
