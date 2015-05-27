package org.springside.examples.quickstart.web.account;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Runner;
import org.springside.examples.quickstart.service.account.AccountService;
import org.springside.modules.mapper.JsonMapper;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AccountService accountService;
	
	protected JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	protected HashMap<String, Object> map = new HashMap<String, Object>();

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid Runner user, RedirectAttributes redirectAttributes) {
		try{
			accountService.registerUser(user);
			map.put("result", "success");
		}catch(RuntimeException e){
			e.printStackTrace();
			map.put("result", e.getMessage());
		}
		return jsonMapper.toJson(map);
/*		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";*/
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
