package com.wg.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.wg.bean.User;
import com.wg.service.UserService;

@Controller
@SessionAttributes("sessionUser")
public class UserController {

	@Autowired
	private UserService userService;

//	注册 save
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, User user) {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user.toString());
		return new ModelAndView("succ");
	}
	
	
//	新增 修改页面
	@RequestMapping(value="addUpdate")
	public String addUpdate(Model model,User user) {
		if(user.getId()!=null){
			try {
				model.addAttribute("user", userService.getUserInfo(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "addUpdate";
	}
	

	/***
	 * 用户登陆，只允许POST提交到该方法
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(User user) {
		// 验证帐号密码是否正确，否则返回到登陆页面。
		if (this.checkOK(user)) {
			// 指定要返回的页面为succ.jsp
			ModelAndView mav = new ModelAndView("succ");
			// 将参数返回给页面
			mav.addObject("user", user);
			System.out.println(user.toString());
			return mav;
		}else{
			return new ModelAndView("error");
		}
	}
	
	/***
	 * 用户登陆，放入session
	 */
	@RequestMapping(value = "login2", method = RequestMethod.POST)
	public ModelAndView login2(User user,Model model) {
		// 验证帐号密码是否正确，否则返回到登陆页面。
		if (this.checkOK(user)) {
			model.addAttribute("sessionUser",user);
			// 指定要返回的页面为succ.jsp
			ModelAndView mav = new ModelAndView("succ");
			// 将参数返回给页面
			mav.addObject("user", user);
			System.out.println(user.toString());
			return mav;
		}else{
			return new ModelAndView("error");
		}
	}
	
	/***
	 * 从session中获取user数据   验证是否成功放入session  方法一
	 */
	/*
	@RequestMapping(value = "userDetail", method = RequestMethod.GET)
	public ModelAndView userDetail(@ModelAttribute("sessionUser")User sessionUser) {
		// 指定要返回的页面为succ.jsp
		ModelAndView mav = new ModelAndView("succ");
		// 将参数返回给页面
		mav.addObject("user", sessionUser);
		System.out.println(sessionUser.toString());
		return mav;
	}
	*/
	
	/***
	 * 从session中获取user数据   验证是否成功放入session  方法二
	 */
	@RequestMapping(value = "userDetail", method = RequestMethod.GET)
	public ModelAndView userDetail(HttpSession session) {
		User sessionUser=(User)session.getAttribute("sessionUser");
		if(sessionUser!=null){
			// 指定要返回的页面为succ.jsp
			ModelAndView mav = new ModelAndView("succ");
			// 将参数返回给页面
			mav.addObject("user", sessionUser);
			System.out.println(sessionUser.toString());
			return mav;
		}else{
			return new ModelAndView("error");
		}
	}

	/***
	 * 验证帐号密码是否正确
	 */
	private boolean checkOK(User user) {
		User userDB=null;
		try {
			userDB = userService.getUserInfo(user);
			if(userDB!=null){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	显示列表1
	@RequestMapping(value="/list")
//	@RequestMapping(value="list")
//	@RequestMapping(value="list", method = RequestMethod.GET)
	public String list(Model model) {
		try {
			model.addAttribute("list", userService.getUsers());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return "/list.jsp";  // 不对
		return "list";
	}
	
//	显示列表2
	@RequestMapping(value = "list2", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView mav = new ModelAndView("list");
		try {
			mav.addObject("list", userService.getUsers());
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("home");
	}
	
//	显示列表3 从session中取user数据  这种方法在未登录就打开该页面时，会出错Session attribute 'sessionUser' required - not found in session
	@RequestMapping(value = "list3", method = RequestMethod.GET)
	public ModelAndView list3(@ModelAttribute("sessionUser")User sessionUser) {
		ModelAndView mav = new ModelAndView("list3");
		try {
			mav.addObject("list", userService.getUsers());
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("home");
	}
	
//	删除
	@RequestMapping("del")
	public String del(User user) {
		try {
			userService.deleteUser(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return this.list2();//这样也能
		return "redirect:/list";
	}
	
	
}