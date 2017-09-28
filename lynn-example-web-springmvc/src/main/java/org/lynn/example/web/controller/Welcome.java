package org.lynn.example.web.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.lynn.example.web.param.Content;
import org.lynn.example.web.param.User;
import org.lynn.example.web.param.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "welcome")
public class Welcome {

	/**
	 * json 方式： 客户端传递json类型参数，服务端返回json类型结果
	 * 
	 * 这种方式：HTTP 请求中的 Content-Type 必须是 application/json 同时传递的 body
	 * 中必须要有数据，如果是空对象则传：{}
	 * 
	 * @responseBody 注解的作用是将 controller
	 *               的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML
	 *               数据，需要注意的呢，在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
	 * @param content
	 */
	@PostMapping(path = "hello")
	@ResponseBody
	public Content hello(@RequestBody Content content) {
		System.out.println(content.getId() + " " + content.getValue());
		content.setValue("hello world");
		return content;
	}

	/**
	 * 这种方式不管HTTPMethod是什么类型，只要参数是以&符号链接挂在url后面的都可以接收到参数； 但是如果要放在body里面，则必须指定
	 * Content-Type为x-www-form-urlencoded
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("hello/form")
	@ResponseBody
	public Content formHello(Integer id, String name) {
		System.out.println(id + " " + name);
		Content content = new Content();
		content.setValue("hello world, form");
		return content;
	}

	@PostMapping("user/validator")
	@ResponseBody
	public User userValidator(@RequestBody @Valid User user) {
		System.out.println(user.getName() + " " + user.getPwd());
		user.setPwd("haha");
		return user;
	}
	
	/**
	 * 这种方式和上面的方式的唯一区别是一个使用的是 application/json；
	 * 而这种方式非常通用，一般使用两种情况：x-www-form-urlencoded 和 multipart/form-data
	 * 其中要使用  multipart/form-data 必须要在 xml 中配置 CommonsMultipartResolver
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("user/validator1")
	@ResponseBody
	public UserInfo userValidator1(@Valid User user) {
		System.out.println(user.getName() + " " + user.getPwd() + " " + user.getDate());
		user.setPwd("haha1");
		UserInfo userInfo = new UserInfo();
		userInfo.setName(user.getName());
		userInfo.setPwd("haha1");
		MultipartFile avatar = user.getAvatar();
		if (null != avatar) {
			System.out.println(avatar.getName());
			System.out.println(avatar.getOriginalFilename());
			System.out.println(avatar.getSize());
			File file = new File("F://a.png");
			try {
				avatar.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		MultipartFile identity = user.getIdentity();
		if (null != identity) {
			System.out.println(identity.getName());
			System.out.println(identity.getOriginalFilename());
			System.out.println(identity.getSize());
			File file = new File("F://b.png");
			try {
				identity.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}
}
