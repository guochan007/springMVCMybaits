package com.wg.test;

import javax.servlet.http.HttpServletRequest;
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

//	ע�� save
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
	
	
//	���� �޸�ҳ��
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
	 * �û���½��ֻ����POST�ύ���÷���
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(User user) {
		// ��֤�ʺ������Ƿ���ȷ�����򷵻ص���½ҳ�档
		if (this.checkOK(user)) {
			// ָ��Ҫ���ص�ҳ��Ϊsucc.jsp
			ModelAndView mav = new ModelAndView("succ");
			// ���������ظ�ҳ��
			mav.addObject("user", user);
			System.out.println(user.toString());
			return mav;
		}else{
			return new ModelAndView("error");
		}
	}
	
	/***
	 * �û���½������session
	 */
	@RequestMapping(value = "login2", method = RequestMethod.POST)
	public ModelAndView login2(User user,Model model) {
		// ��֤�ʺ������Ƿ���ȷ�����򷵻ص���½ҳ�档
		if (this.checkOK(user)) {
			model.addAttribute("sessionUser",user);
			// ָ��Ҫ���ص�ҳ��Ϊsucc.jsp
			ModelAndView mav = new ModelAndView("succ");
			// ���������ظ�ҳ��
			mav.addObject("user", user);
			System.out.println(user.toString());
			return mav;
		}else{
			return new ModelAndView("error");
		}
	}

	/***
	 * ��֤�ʺ������Ƿ���ȷ
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
	
//	��ʾ�б�1
	@RequestMapping(value="/list")
//	@RequestMapping(value="list")
//	@RequestMapping(value="list", method = RequestMethod.GET)
	public String list(Model model) {
		try {
			model.addAttribute("list", userService.getUsers());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return "/list.jsp";  // ����
		return "list";
	}
	
//	��ʾ�б�2
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
	
//	��ʾ�б�3 ��session��ȡuser����  ���ַ�����δ��¼�ʹ򿪸�ҳ��ʱ�������Session attribute 'sessionUser' required - not found in session
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
	
//	ɾ��
	@RequestMapping("del")
	public String del(User user) {
		try {
			userService.deleteUser(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return this.list2();//����Ҳ��
		return "redirect:/list";
	}
	
	
}