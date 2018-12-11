package com.htcs.kehaoduo.bs.controller;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.bs.dto.AdvertiserAndPublicityDto;
import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityCheckLog;
import com.htcs.kehaoduo.bs.service.TPublicityService;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.page.PageBean;
import com.htcs.kehaoduo.common.util.DateTimeUtil;
import com.htcs.kehaoduo.common.util.GenerateQRCodeUtil;
import com.htcs.kehaoduo.common.util.RandomStrUtils;
import com.htcs.kehaoduo.system.bean.SysAgent;
import com.htcs.kehaoduo.system.bean.SysAgentAppid;
import com.htcs.kehaoduo.system.bean.WebUser;
import com.htcs.kehaoduo.system.bean.WebUserCheckLog;
import com.htcs.kehaoduo.system.dto.AdvertiserDto;
import com.htcs.kehaoduo.system.service.SysAgentService;
import com.htcs.kehaoduo.system.service.SysUserService;
import com.htcs.kehaoduo.system.service.WebUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理控制层
 *
 * @author Dragon
 * @create 2017-12-01 11:10
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SysAgentService sysAgentService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WebUserService webUserService;

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxPayService wxPayService;

    @Autowired
    private TPublicityService tPublicityService;

    @Value("${wechat.templateId}")
    private String templateId;

    @Value("${site}")
    private String site;

    @Value("${wechat.mp.appId}")
    private String appId;

    private String signKey = "J*N(Mn2v5n8y%^f4";

    @GetMapping("/generateBindQrCode")
    public void generateBindQrCode(HttpServletResponse response) throws IOException, WxErrorException {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
        String redirect = "http://%s/bs/admin/bindWechatOpenid?id=%d&name=%s&timestamp=%d&nonce=%s&sign=%s";
        long currentTimeMillis = System.currentTimeMillis();
        String nonce = RandomStrUtils.generateStr(6);

        LoginSysUser loginSysUser = RequestUserHolder.currentSysUser();
        Integer id = loginSysUser.getId();
        String sign = DigestUtils.md5Hex(id + "" + currentTimeMillis + nonce + signKey);


        String content = String.format(url, appId, URLEncoder.encode(String.format(redirect, site, id, loginSysUser.getLoginName(), currentTimeMillis, nonce, sign), "utf-8"), appId);

        InputStream logoInputStream = this.getClass().getClassLoader().getResourceAsStream("logo.png");
        content = wxMpService.shortUrl(content);

        GenerateQRCodeUtil.encodeQrcodeWithLogo(content, logoInputStream, response);
    }

    @RequestMapping("/bindWechatOpenid")
    public void bingWechatOpenid(String code, HttpServletRequest request, HttpServletResponse response) throws WxErrorException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String timestamp = request.getParameter("timestamp");
        long currentTimeMillis = System.currentTimeMillis();
        boolean valid = StringUtils.isNotEmpty(timestamp) && (currentTimeMillis - Long.valueOf(timestamp) < 60000);
        if (!valid) {
            response.getWriter().write("<script>alert('二维码已经超时，请刷新页面重新生成二维码！')</script>");
            return;
        }

        String idStr = request.getParameter("id");
        String loginName = request.getParameter("name");
        String nonce = request.getParameter("nonce");
        String sign = request.getParameter("sign");
        String md5Hex = DigestUtils.md5Hex(idStr + "" + timestamp + nonce + signKey);
        if (!md5Hex.equalsIgnoreCase(sign)) {
            response.getWriter().write("<script>alert('签名不正确，请刷新页面重新生成二维码！')</script>");
            return;
        }
        //绑定恒天客好多公众号 用于提现，充值
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        String openId = wxMpOAuth2AccessToken.getOpenId();
        SysAgent sysAgent = new SysAgent();
        Integer id = Integer.valueOf(idStr);
        sysAgent.setId(id);
        sysAgent.setSysUserId(id);
        sysAgent.setOpenid(openId);
        sysAgentService.updateAgentWithoutLogged(sysAgent);
        response.getWriter().write("<script>alert('绑定成功！')</script>");
    }

    @GetMapping("/queryAgentWeChatMenuClickUrl")
    public ReturnStatus queryAgentWeChatMenuClickUrl() throws UnsupportedEncodingException {
        LoginSysUser loginSysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(loginSysUser, "登录超时，请重新登录！");
        Map<String, Object> stringObjectMap = sysAgentService.queryAgentByUserId(loginSysUser.getId());
        String appId;
        if (stringObjectMap == null || StringUtils.isEmpty((String) stringObjectMap.get("appId"))) {
            appId = this.appId;
        } else {
            appId = (String) stringObjectMap.get("appId");
        }
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
        String redirect = "http://%s/web/chose_login.html";
        String s = String.format(url, appId, URLEncoder.encode(String.format(redirect, site), "utf-8"), appId);

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "", s);
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping(value = "/changePassword")
    public ReturnStatus changePassword(String oldPwd, String newPwd) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "登录超时，请重新登录！");
        boolean result = sysUserService.updatePassword(sysUser.getLoginName(), oldPwd, newPwd);
        if (!result) {
            return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "旧密码错误，修改失败！");
        }
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功，请重新登录！");
    }

    /**
     * 添加代理商账户
     *
     * @param sysAgent      代理商实体
     * @param sysAgentAppid 代理商公众号实体
     * @return JSON状态
     */
    @PostMapping(value = "/addAgent")
    public ReturnStatus addAgent(SysAgent sysAgent, SysAgentAppid sysAgentAppid) {
        boolean flag = sysAgentService.addAgent(sysAgent, sysAgentAppid);
        Preconditions.checkArgument(flag, "账号已存在，请重新输入！");
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功！");
    }

    /**
     * 代理商信息列表
     *
     * @param pageNum  当前页
     * @param pageSize 每页显示条数
     * @return
     */
    @GetMapping(value = "/listAgent")
    public ReturnStatus<PageBean<Map<String, Object>>> listAgent(String nameOrTel, Date beginTime, Date endTime,
                                                                 Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = sysAgentService.listAgent(nameOrTel, beginTime, endTime);
        PageBean<Map<String, Object>> pageBean = new PageBean<>(list);
        return new ReturnStatus<>(pageBean);
    }

    /**
     * 根据userId获取代理商信息
     *
     * @param id userId
     * @return
     */
    @GetMapping(value = "/queryAgentByUserId")
    public ReturnStatus<Map<String, Object>> queryAgentByUserId(Integer id) {
        Map<String, Object> map = sysAgentService.queryAgentByUserId(id);
        return new ReturnStatus<Map<String, Object>>(map);
    }

    /**
     * 修改代理商信息
     *
     * @param sysAgentAppid
     * @param mobile
     * @param telephone
     * @return
     */
    @PostMapping(value = "/updateAgent")
    public ReturnStatus updateAgent(SysAgentAppid sysAgentAppid, String mobile, String telephone) {
        sysAgentService.updateAgentAppid(sysAgentAppid, mobile, telephone);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功");
    }

    /**
     * 逻辑删除代理商信息
     *
     * @param sysUserId 代理商id
     * @return
     */
    @PostMapping(value = "/logicalDelAgent")
    public ReturnStatus logicalDelAgent(Integer sysUserId) {
        sysAgentService.logicalDelAgent(sysUserId);
        return new ReturnStatus("删除成功");
    }

    /**
     * 批量删除代理商信息
     *
     * @param ids 代理商id集合
     * @return
     */
    @PostMapping(value = "/batchRemoveAgent")
    public ReturnStatus batchRemoveAgent(String ids) {
        sysAgentService.batchRemoveAgent(ids);
        return new ReturnStatus("删除成功");
    }

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @return JSON状态
     */
    @PostMapping(value = "/updatePassword")
    public ReturnStatus updatePassword(String username, String oldPwd, String newPwd) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(username), "账号不能为空，请重新输入！");
        Preconditions.checkArgument(StringUtils.isNotEmpty(newPwd), "新密码不能为空，请重新输入！");

        boolean flag = sysUserService.updatePassword(username, oldPwd, newPwd);
        Preconditions.checkArgument(flag, "账号与密码不匹配，请重新输入！");

        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功！");
    }

    /**
     * 获取广告主资料列表
     *
     * @param advertiserDto
     * @param pageNum       当前页
     * @param pageSize      每页显示条数
     * @return JSON数据
     */
    @GetMapping(value = "/listAdvertiser")
    public ReturnStatus<PageBean<Map<String, Object>>> listAdvertiser(AdvertiserDto advertiserDto,
                                                                      Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageBean<Map<String, Object>> pageBean = new PageBean<>(webUserService.listAdvertiser(advertiserDto));
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "", pageBean);
    }

    /**
     * 查看广告主的资料 审核
     *
     * @param advertiserDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/listAdvertiser4Check")
    public ReturnStatus<PageBean<Map<String, Object>>> listAdvertiser4Check(AdvertiserDto advertiserDto,
                                                                            Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = webUserService.listAdvertiser4Check(advertiserDto);
        PageBean<Map<String, Object>> pageBean = new PageBean<>(list);
        return new ReturnStatus<>(pageBean);
    }

    /**
     * 审核广告主
     *
     * @param webUserCheckLog
     * @return
     */
    @PostMapping(value = "/checkAdvertiser")
    public ReturnStatus checkAdvertiser(WebUserCheckLog webUserCheckLog) {
        webUserService.checkAdvertiser(webUserCheckLog);
        return new ReturnStatus("审核成功");
    }

    /**
     * 查看广告的资料 审核
     *
     * @param advertiserAndPublicityDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/listPublicity4Check")
    public ReturnStatus<PageBean<Map<String, Object>>> listPublicity4Check(AdvertiserAndPublicityDto advertiserAndPublicityDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = tPublicityService.listPublicity4Check(advertiserAndPublicityDto);
        PageBean<Map<String, Object>> pageBean = new PageBean<>(list);
        return new ReturnStatus<>(pageBean);
    }

    /**
     * 审核广告
     *
     * @param tPublicityCheckLog
     * @return
     */
    @PostMapping(value = "/checkPublicity")
    public ReturnStatus checkPublicity(TPublicityCheckLog tPublicityCheckLog) throws WxErrorException {
        tPublicityService.checkPublicity(tPublicityCheckLog);

        Integer publicityId = tPublicityCheckLog.getPublicityId();
        Byte toStatus = tPublicityCheckLog.getToStatus();
        String checkResult = tPublicityCheckLog.getCheckResult();
        WxMpTemplateMessage templateMessage = getTemplateMessage(publicityId, toStatus, checkResult);
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        return new ReturnStatus("审核成功");
    }

    /**
     * 发送模板消息
     *
     * @param publicityId
     * @param checkStatus
     * @param checkResult
     * @return
     */
    public WxMpTemplateMessage getTemplateMessage(Integer publicityId, Byte checkStatus, String checkResult) {
        TPublicity publicity = tPublicityService.queryPublicity(publicityId);
        WebUser webUser = webUserService.getWebUserById(publicity.getWebUserId());
        String createTime = DateTimeUtil.getTime("yyyy-MM-dd HH:mm:ss", publicity.getCreateTime());

        String title = null;
        if (3 == checkStatus) {
            checkResult = "审核通过！";
            title = "恭喜，您发布的推广审核通过！";
        }
        if (4 == checkStatus) {
            title = "抱歉，您发布的推广审核未通过！";
        }

        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", title));
        data.add(new WxMpTemplateData("keyword1", webUser.getUserName()));
        data.add(new WxMpTemplateData("keyword2", publicity.getPublicityTitle()));
        data.add(new WxMpTemplateData("keyword3", createTime));
        data.add(new WxMpTemplateData("keyword4", checkResult));
        data.add(new WxMpTemplateData("remark", ""));
        return WxMpTemplateMessage.builder().toUser(webUser.getOpenidB()).templateId(templateId).data(data).build();
    }
}
