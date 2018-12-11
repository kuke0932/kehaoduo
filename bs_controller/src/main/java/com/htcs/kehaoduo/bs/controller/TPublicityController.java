package com.htcs.kehaoduo.bs.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.bs.dto.PublicityDto;
import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityPhone;
import com.htcs.kehaoduo.bs.po.TPublicityReadLog;
import com.htcs.kehaoduo.bs.service.TPublicityPhoneSevice;
import com.htcs.kehaoduo.bs.service.TPublicityReadLogService;
import com.htcs.kehaoduo.bs.service.TPublicityService;
import com.htcs.kehaoduo.bs.wechatapi.WechatApiMap;
import com.htcs.kehaoduo.bs.wechatapi.api.WechatApi;
import com.htcs.kehaoduo.bs.wechatapi.model.Environment;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import com.htcs.kehaoduo.common.page.PageBean;
import com.htcs.kehaoduo.common.util.HttpClientUtil;
import com.htcs.kehaoduo.common.util.UUIDUtil;
import com.htcs.kehaoduo.common.util.UploadUtil;
import com.htcs.kehaoduo.common.util.WebUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 广告控制层
 *
 * @author Dragon
 * @create 2017-10-20 15:06
 **/
@RestController
@RequestMapping("/web/publicity")
public class TPublicityController {

    @Autowired
    private TPublicityService tPublicityService;

    @Autowired
    private TPublicityReadLogService tPublicityReadLogService;

    @Autowired
    private TPublicityPhoneSevice tPublicityPhoneSevice;

    @Autowired
    private WxMpService wxMpService;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${gaode.api.key}")
    private String ak;
    /**
     * 新增广告信息
     *
     * @param publicity TPublicity实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/addPublicity", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ReturnStatus<Object> addPublicity(TPublicity publicity, @RequestParam(value = "appId", required = false) String appId) throws UnsupportedEncodingException, WxErrorException {
        tPublicityService.addPublicity(publicity);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "添加成功");
    }

    @PostMapping(value = "/addPublicity", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnStatus<Object> addPublicityJSON(@RequestBody TPublicity publicity, @RequestHeader(value = "appId", required = false) String appId) throws WxErrorException, UnsupportedEncodingException {
        tPublicityService.addPublicityAndSetStaticUrl(publicity, appId);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "添加成功");
    }

    /**
     * 修改广告信息
     *
     * @param publicity TPublicity实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/updatePublicity", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ReturnStatus updatePublicity(TPublicity publicity) {
        tPublicityService.updatePublicity(publicity);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功");
    }

    /**
     * 修改广告信息
     *
     * @param publicity TPublicity实体
     * @return JSON数据状态
     */
    @PostMapping(value = "/updatePublicity", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnStatus updatePublicityJSON(@RequestBody TPublicity publicity) {
        tPublicityService.updatePublicity(publicity);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "修改成功");
    }

    /**
     * 根据id查询广告信息
     *
     * @param id 主键id
     * @return JSON数据
     */
    @GetMapping("/{id}/queryPublicity")
    public ReturnStatus<TPublicity> queryPublicity(@PathVariable("id") Integer id) {
        TPublicity publicity = tPublicityService.queryPublicityAndExt(id);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", publicity);
    }

    /**
     * 开始推广后台方法
     *
     * @param id 主键id
     * @return JSON数据
     */
    @GetMapping("/{id}/beginExtend")
    public ReturnStatus<Map<String, Object>> beginExtend(@PathVariable("id") Integer id) {
        Map<String, Object> map = tPublicityService.beginExtend(id);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", map);
    }


    /**
     * 开始推广后台方法
     *
     * @param id 主键id
     * @return JSON数据
     */
    @GetMapping("/{id}/generateQrCodeUrl")
    public ReturnStatus<Map<String, Object>> generateQrCodeUrl(@PathVariable("id") Integer id, String oldUuid) {
        WechatApiMap.remove(oldUuid);

        Environment environment = Environment.of("classpath:config.properties");
        WechatApi wechatApi = new WechatApi(environment);
        wechatApi.getUUID();
        // 生成key
        String uuid = UUIDUtil.getUUID();
        // 将WechatApi实例存入redis，key为uuid
        WechatApiMap.put(uuid, wechatApi);
        // 二维码url
        String qrCodeUrl = wechatApi.genqrcodeurl();
        Map<String, Object> map = new HashMap<>();
        map.put("qrCodeUrl", qrCodeUrl);
        map.put("uuid", uuid);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", map);
    }

    /**
     * 查看全文
     *
     * @param id 广告id
     * @return JSON数据
     */
    @GetMapping("/{id}/queryContent")
    public ReturnStatus<Map<String, Object>> queryContent(@PathVariable("id") Integer id) {
        Map<String, Object> stringObjectMap = tPublicityService.queryContent(id);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", stringObjectMap);
    }

    /**
     * 根据审核状态和推广类型查询广告信息
     *
     * @param checkStatus 如果传-1，则查询提交审核的   审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过
     * @param publicityType 推广类型(1扫码，2转发)
     * @param contentType   正文类型(1文本2图文)
     * @return JSON数据
     */
    @GetMapping({"/{checkStatus}/{publicityType}/{contentType}/listPublicityByUserId", "/{checkStatus}/listPublicityByUserId", "/listPublicityByUserId"})
    public ReturnStatus<PageBean<TPublicity>> listPublicityByUserId(@PathVariable("checkStatus") Byte checkStatus,
                                                                    @PathVariable(value = "publicityType", required = false) Byte publicityType,
                                                                    @PathVariable(value = "contentType", required = false) Byte contentType, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<TPublicity> publicityPage = PageHelper.startPage(pageNum, pageSize);
        List<TPublicity> list = tPublicityService.listPublicityByUserId(checkStatus, publicityType, contentType);
        PageBean<TPublicity> pageBean = new PageBean<>(list);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "查询成功", pageBean);
    }

    /**
     * 根据id逻辑删除广告信息
     *
     * @param id 主键id
     * @return JSON数据状态
     */
    @DeleteMapping("/{id}/logicalDel")
    public ReturnStatus logicalDelById(@PathVariable("id") Integer id) {
        tPublicityService.logicalDelById(id);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }

    /**
     * 根据id删除广告信息
     *
     * @param id 主键id
     * @return JSON数据状态
     */
    @DeleteMapping("/{id}/delete")
    public ReturnStatus deletePublicityById(@PathVariable("id") Integer id) {
        tPublicityService.deletePublicityById(id);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "删除成功");
    }

    @GetMapping("/queryContent4Click")
    public ReturnStatus<PublicityDto> queryContent4Click(Integer id, HttpServletRequest request) throws WxErrorException, IOException {

        String readerOpenid = "";
        String code = request.getParameter("code");
        if (StringUtils.isNotEmpty(code)) {
            //使用恒天公众号获取 openid
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            readerOpenid = wxMpOAuth2AccessToken.getOpenId();
        }
        String ipAddr = WebUtils.getIpAddr(request);
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String byIp = request.getParameter("byIP");
        String localAddress = getLocalAddress(longitude, latitude, Boolean.valueOf(byIp), ipAddr);
        PublicityDto publicityDto = tPublicityService.queryContent4Click(id, readerOpenid, localAddress);
        return new ReturnStatus<>(publicityDto);
    }

    /**
     * 生成点击日志
     *
     * @param tPublicityReadLog TPublicityReadLog实体
     * @return JSON数据状态
     */
    @PostMapping("/insertPublicityReadLog")
    public ReturnStatus insertPublicityReadLog(TPublicityReadLog tPublicityReadLog) {
        if (Objects.equals(tPublicityReadLog.getReaderOpenId(), tPublicityReadLog.getFromOpenId())) {
            return new ReturnStatus();
        } else {
            tPublicityReadLogService.insertPublicityReadLog(tPublicityReadLog);
            return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
        }
    }

    /**
     * 上传图片
     *
     * @param file
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadImg")
    public ReturnStatus<String> uploadImg(@RequestParam("file") MultipartFile file,
                                          HttpServletResponse response) throws IOException {

        String fileName = UploadUtil.doUploadFile(file, uploadPath, response);
        return new ReturnStatus<>(ReturnStatus.StatusCode.OK, "上传成功", fileName);
    }

    /**
     * 客户电话
     *
     * @param tPublicityPhone
     * @return
     */
    @PostMapping("/insertPhone")
    public ReturnStatus insertPhone(TPublicityPhone tPublicityPhone, HttpServletRequest request) throws IOException {

        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");

        String byIp = request.getParameter("byIP");
        String ipAddr = WebUtils.getIpAddr(request);
        String address = getLocalAddress(longitude, latitude, Boolean.valueOf(byIp), ipAddr);
        tPublicityPhone.setValidAreaCode(address);
        tPublicityPhoneSevice.insertPhone(tPublicityPhone);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "添加成功");
    }


    private String getLocalAddress(String longitude, String latitude, boolean byIp, String ipAddr) throws IOException {
        String address = "";
        if (byIp) {
            String resp = HttpClientUtil.get("http://restapi.amap.com/v3/ip?key=" + ak + "&ip=" + ipAddr, "utf-8");
            JSONObject jsonObject = JSONObject.parseObject(resp);
            Integer status = jsonObject.getInteger("status");
            if (status == 1) {
                String province = jsonObject.getString("province");
                String city = jsonObject.getString("city");
                if (Objects.equals(province, city)) {
                    address = province;
                } else {
                    address = province + city;
                }
            } else {
                address = "IP未知城市";
            }
        } else {
            String resp = HttpClientUtil.get("http://restapi.amap.com/v3/geocode/regeo?key=" + ak + "&location=" + longitude + "," + latitude, "utf-8");
            JSONObject jsonObject = JSONObject.parseObject(resp);
            Integer status = jsonObject.getInteger("status");
            if (status == 1) {
                JSONObject result = jsonObject.getJSONObject("regeocode");
                JSONObject addressComponent = result.getJSONObject("addressComponent");
                address = addressComponent.getString("province") + addressComponent.getString("city") + addressComponent.getString("district") + addressComponent.getString("township");
            } else {
                address = "未知城市";
            }
        }
        return address;
    }

    @PostMapping("/cancelCheck")
    public ReturnStatus cancelCheck(Integer id) {
        TPublicity tPublicity = tPublicityService.queryPublicity(id);
        Byte checkStatus = tPublicity.getCheckStatus();
        //只有审核不通过的情况可以取消
        Preconditions.checkArgument(checkStatus != 4 , "当前状态不能取消！");

        TPublicity publicity = new TPublicity();
        publicity.setId(id);
        publicity.setCheckStatus((byte)5);
        publicity.setCheckResult("用户取消审核");

        tPublicityService.updatePublicityCheckStatus(publicity);
        return new ReturnStatus();
    }
}
