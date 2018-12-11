package com.htcs.kehaoduo.bs.controller;

import com.htcs.kehaoduo.common.Common;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.exception.ReturnStatusException;
import com.htcs.kehaoduo.common.util.GeetestLib;
import com.htcs.kehaoduo.common.util.SmsUtil;
import com.htcs.kehaoduo.system.service.WebUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author Dragon
 * @create 2017-12-18 15:28
 **/
@Controller
@RequestMapping("/gt")
public class GeetestController {

	@Value("${geetest.geetest_id}")
	private String geetest_id;

	@Value("${geetest.geetest_key}")
	private String geetest_key;

	@Value("${geetest.newfailback}")
	private boolean newfailback;

	@Autowired
	private WebUserService webUserService;

	@GetMapping(value = "/register")
	@ResponseBody
	public ReturnStatus<HashMap<String, Object>> startCaptcha() {
		GeetestLib gtSdk = new GeetestLib(geetest_id, geetest_key, newfailback);

		String resStr = "{}";

		String userid = "test";

		//自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("user_id", userid); //网站用户id
		param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(param);

		resStr = gtSdk.getResponseStr();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		map.put("userid", userid);
		map.put("resStr", resStr);

		return new ReturnStatus<>(map);
	}

	@PostMapping(value = "/verify")
	public void verify(int gt_server_status, String userId, String mobile, SmsUtil.SmsTemplate smsTemplate,
					   HttpServletRequest request, HttpServletResponse response) throws IOException {
		GeetestLib gtSdk = new GeetestLib(geetest_id, geetest_key, newfailback);

		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

		//自定义参数,可选择添加
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("user_id", userId); //网站用户id
		param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
		param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

		int gtResult = 0;

		if (gt_server_status == 1) {
			//gt-server正常，向gt-server进行二次验证

			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
			System.out.println(gtResult);
		} else {
			// gt-server非正常情况下，进行failback模式验证

			System.out.println("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
			System.out.println(gtResult);
		}

		if (gtResult == 1) {
			// 验证成功
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			JSONObject data = new JSONObject();
			try {
				data.put("status", "success");
				data.put("version", gtSdk.getVersionInfo());
				ReturnStatus returnStatus = sendSms(mobile, smsTemplate);
				if (returnStatus.getStatus() == 500) {
					data.put("message", returnStatus.getMessage());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.println(data.toString());
		}
		else {
			// 验证失败
			JSONObject data = new JSONObject();
			try {
				data.put("status", "fail");
				data.put("version", gtSdk.getVersionInfo());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.println(data.toString());
		}
	}

	/**
	 * 发送短信
	 *
	 * @param mobile
	 * @param smsTemplate
	 * @return
	 * @throws IOException
	 */
	private ReturnStatus sendSms(String mobile, SmsUtil.SmsTemplate smsTemplate) throws IOException {
		boolean isRegistered = webUserService.isRegistered(mobile);
		Common.VerifyCodeType verifyCodeType = smsTemplate.getVerifyCodeType();

		switch (verifyCodeType) {
			case REGISTER:
			case CHANGE_MOBILE:
				if (isRegistered) {
					return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "手机号已注册！");
				}
				break;
			case CHANGE_PASSWORD:
			case FORGET_PASSWORD:
				if (!isRegistered) {
					return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "手机号尚未注册！");
				}
				break;
			default:
				break;
		}

		String result = SmsUtil.send(mobile, smsTemplate);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result, com.alibaba.fastjson.JSONObject.class);

		Integer error_code = jsonObject.getInteger("error_code");
		if (error_code == 0) {
			return new ReturnStatus<>();
		} else {
			String message = jsonObject.getString("result");
			throw new ReturnStatusException(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, message);
		}
	}
}
