package com.htcs.kehaoduo.bs.websocket;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityResult;
import com.htcs.kehaoduo.bs.service.TPublicityResultService;
import com.htcs.kehaoduo.bs.service.TPublicityService;
import com.htcs.kehaoduo.bs.wechatapi.WechatApiMap;
import com.htcs.kehaoduo.bs.wechatapi.api.WechatApi;
import com.htcs.kehaoduo.bs.wechatapi.model.Const;
import com.htcs.kehaoduo.common.util.JsonUtil;
import com.htcs.kehaoduo.wechat.conf.WxMpServices;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaokaiyuan
 * @create 2017-10-31 16:50
 **/
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static CanStopObservable observable = new CanStopObservable();
	private Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
	@Autowired
	private TPublicityService tPublicityService;
	@Autowired
	private TPublicityResultService tPublicityResultService;
	@Value("${upload.path}")
	private String uploadPath;
	@Value("${site}")
	private String site;

	@Autowired
	private WxMpService wxMpService;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		WechatSend wechatSend = new WechatSend(
				message.getPayload(),
				session,
				tPublicityService,
				tPublicityResultService,
				uploadPath,
				wxMpService,
				site);
		observable.addObserver(wechatSend);
		new Thread(wechatSend).start();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("建立连接");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.debug("传输错误，关闭连接");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		observable.setCanStop(session.getId());
		logger.debug("关闭连接");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	static class WechatSend implements Runnable, Observer {

		private Logger logger = LoggerFactory.getLogger(WechatSend.class);

		private String message; //接收信息
		private WebSocketSession session;
		private TPublicityService tPublicityService;
		private TPublicityResultService tPublicityResultService;
		private String uploadPath;
		private WxMpService wxMpService;
		private String site;

		private boolean canStop;

		public WechatSend(String message, WebSocketSession session,
						  TPublicityService tPublicityService,
						  TPublicityResultService tPublicityResultService,
						  String uploadPath,
						  WxMpService wxMpService,
						  String site) {
			this.message = message;
			this.session = session;
			this.tPublicityService = tPublicityService;
			this.tPublicityResultService = tPublicityResultService;
			this.canStop = false;
			this.uploadPath = uploadPath;
			this.wxMpService = wxMpService;
			this.site = site;
		}

		@Override
		public void update(Observable o, Object arg) {
			if (session.getId().equals(arg)) {
				canStop = true;
			}
		}

		@Override
		public void run() {
			Map<String, Object> map = JsonUtil.json2Map(message);
			if (map != null) {
				// 广告id
				String id = (String) map.get("id");
				TPublicity publicity = tPublicityService.queryPublicity(Integer.valueOf(id));
				// 缓存WechatApi的key
				String uuid = (String) map.get("uuid");
				String belongAppId = (String) map.get("belongAppId");

				WechatApi wechatApi = WechatApiMap.get(uuid);
				int i = 0;
				boolean ok = false;
				while (true && !canStop && i++ < Integer.MAX_VALUE) {
					if (!wechatApi.waitforlogin(1)) {
						continue;
					}
					logger.info(Const.LOG_MSG_CONFIRM_LOGIN);
					if (!wechatApi.waitforlogin(0)) {
						continue;
					}
					ok = true;
					break;
				}
				if (canStop) {

				} else if (ok) {
					if (!wechatApi.login()) {
						logger.warn("登录失败");
						sendMessageBack(session, 1, "登录失败，请重新扫码");
						return;
					}
					if (!wechatApi.webwxinit()) {
						logger.warn("初始化失败");
						sendMessageBack(session, 2, "初始化失败，请重新扫码");
						return;
					}
					if (!wechatApi.getContact()) {
						logger.warn("获取用户列表失败");
						sendMessageBack(session, 3, "获取用户列表失败，请重新扫码");
						return;
					}

					try {
						sendMsg(wechatApi, publicity, belongAppId);
					} catch (InterruptedException | UnsupportedEncodingException | WxErrorException e) {
						e.printStackTrace();
					}
				} else {
					sendMessageBack(session, 9, "二维码超时，请重新刷新扫码");
					return;
				}

			}
		}

		private void sendMessageBack(WebSocketSession session, int type, String message) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", type);
			jsonObject.put("message", message);
			TextMessage textMessage = new TextMessage(jsonObject.toJSONString());
			try {
				session.sendMessage(textMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		/**
		 * 发送消息
		 *
		 * @param wechatApi
		 * @param publicity
		 */
		private void sendMsg(WechatApi wechatApi, TPublicity publicity, String belongAppId) throws InterruptedException, WxErrorException, UnsupportedEncodingException {
			// 通讯录集合
			JsonArray contactList = wechatApi.getContactList();
			JsonArray groupList = wechatApi.getGroupList();
			Iterator<JsonElement> iterator = contactList.iterator();
			Map<String, Object> user = wechatApi.getUser();
			Long userWechatId = (Long) user.get("Uin");
			String headImgUrl = (String) user.get("HeadImgUrl");
			headImgUrl = ""; //wechatApi.getHeadImg(headImgUrl, this.uploadPath);
			String nickName = (String) user.get("NickName");
			logger.debug("user nickName {} {}", userWechatId, nickName);
			logger.debug("user count {}", contactList.size());
			logger.debug("group count {}", groupList.size());

			String content = "";
			if (publicity.getContentType() == 1) {
				content = publicity.getContent();
			} else {
				content += publicity.getPublicityIntro();
				content += "\n";
				content += "\n";
//                Preconditions.checkArgument(StringUtils.isNotEmpty(publicity.getStaticUrl()), "请在公众号创建推广信息！");

				String appId = wxMpService.getWxMpConfigStorage().getAppId();
				if (appId != null) {
					WxMpServices instance = WxMpServices.getInstance(appId);
					WxMpService wxMpService = instance.getWxMpService();
					String oAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
					String redirectUrl = "http://" + site + "/web/host-user/img_page/img_show_send.html?id=" + publicity.getId();
					redirectUrl = oAuthUrl.replace("REDIRECTURL", URLEncoder.encode(redirectUrl, "UTF-8"));
					String s = wxMpService.shortUrl(redirectUrl);
					content += s;
				}
			}
			String toUserType = publicity.getToUserType();
			//发送给用户类型1全部用户2男性朋友3女性朋友4群
			List<Integer> collect = new ArrayList<>();
			if (toUserType.contains(",")) {
				String[] strings = toUserType.split(",");
				collect = Arrays.stream(strings).map(Integer::valueOf).collect(Collectors.toList());
			} else {
				collect.add(Integer.valueOf(toUserType));
			}
			int toUserCount = 0;
			for (Integer aCollect : collect) {
				switch (aCollect) {
					case 1:
						while (iterator.hasNext()) {
							JsonElement element = iterator.next();
							String uid = ((JsonObject) element).get("UserName").getAsString();
							wechatApi.sendText(content, uid);
							toUserCount++;
							Thread.sleep(1000);
						}
						break;
					case 2:
						while (iterator.hasNext()) {
							JsonElement element = iterator.next();
							Integer sex = ((JsonObject) element).get("Sex").getAsInt();
							if (sex == 1) {
								String uid = ((JsonObject) element).get("UserName").getAsString();
								wechatApi.sendText(content, uid);
								toUserCount++;
								Thread.sleep(1000);
							}
						}
						break;
					case 3:
						while (iterator.hasNext()) {
							JsonElement element = iterator.next();
							Integer sex = ((JsonObject) element).get("Sex").getAsInt();
							if (sex == 2) {
								String uid = ((JsonObject) element).get("UserName").getAsString();
								wechatApi.sendText(content, uid);
								toUserCount++;
								Thread.sleep(1000);
							}
						}
						break;
					case 4: //群聊
						for (JsonElement element : groupList) {
							String uid = ((JsonObject) element).get("UserName").getAsString();
							wechatApi.sendText(content, uid);
							toUserCount++;
							Thread.sleep(1000);
						}
						break;
					default:
				}
			}
			Date nowDate = new Date();
			TPublicityResult tPublicityResult = new TPublicityResult();
			tPublicityResult.setPublicityId(publicity.getId());
			tPublicityResult.setTotalReadCount(publicity.getTotalReadCount() == null ? 0 : publicity.getTotalReadCount());
			tPublicityResult.setUserWechatId(String.valueOf(userWechatId));
			tPublicityResult.setUserNickname(nickName);
			tPublicityResult.setUserHeadImage(headImgUrl);
			tPublicityResult.setOperateTime(nowDate);
			tPublicityResult.setToUserCount(toUserCount);
			tPublicityResult.setPublicityType((byte) 1);
			tPublicityResult.setCreateTime(nowDate);
			int i = tPublicityResultService.addPublicityResult(tPublicityResult);
			if (i > 0) {
				sendMessageBack(session, 0, "发送成功，请刷新以继续使用！");
			}
		}
	}
}
