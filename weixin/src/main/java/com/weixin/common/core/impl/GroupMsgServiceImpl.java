package com.weixin.common.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.weixin.common.core.GroupMsgService;
import com.weixin.common.core.WxService;
import com.weixin.common.mapper.MassMapper;
import com.weixin.common.mapper.UserBoundMapper;
import com.weixin.common.model.mass.MassParams;
import com.weixin.common.model.mass.MaterialContent;
import com.weixin.common.model.mass.MassContentRel;
import com.weixin.common.model.mass.MassEvent;
import com.weixin.common.model.mass.gson.MpGsonBuilder;
import com.weixin.common.model.mass.gson.MpMassGroupMessage;
import com.weixin.common.model.mass.gson.MpMassNews;
import com.weixin.common.model.mass.gson.MpMassOpenIdsMessage;
import com.weixin.common.model.mass.gson.MpMassSendResult;
import com.weixin.common.model.mass.gson.MpMassUploadResult;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.util.DataUtil;
import com.weixin.common.util.FileLoadUtil;
import com.weixin.common.util.MessageUtil;

@Component("gourpMsgSend")
public class GroupMsgServiceImpl implements GroupMsgService {
	@Autowired
	private WxService wxService;
	@Autowired
	private MassMapper massMapper;
	@Autowired
	private UserBoundMapper userBoundMapper;
	@Override
	public MpMassSendResult sendByGroupId(String sendThing, String msgType, Long groupId) {
		if("news".equals(msgType)){
			msgType = "mpnews";
		}
		String json = "{\"filter\":{\"is_to_all\":" + (groupId == null?"true":"false") +",\"gourp_id\":\""+(groupId == null?"":groupId)
				+"\"},\"" +msgType +"\":{\"media_id\":\"" + sendThing +"\"},\"msgtype\":\""+msgType +"\"}";
		MpMassGroupMessage groupMessage = new MpMassGroupMessage();
		groupMessage.setMsgtype(msgType);
		if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){
			groupMessage.setContent(sendThing);
		}else{
			groupMessage.setMediaId(sendThing);
		}
		if(groupId != null){
			groupMessage.setGroupId(groupId);
		}
		String resultJson = wxService.sendMsgByGroupId(json).toJSONString();
		Gson gson = MpGsonBuilder.create();
		MpMassSendResult result = gson.fromJson(resultJson, MpMassSendResult.class);
		return result;
	}

	@Override
	public MpMassSendResult sendByOpenids(String sendThing, String msgType,
			List<String> openids) {
		if(openids.size() > 10000){
			return null;
		}
		MpMassOpenIdsMessage openidsMessage = new MpMassOpenIdsMessage();
		openidsMessage.setToUsers(openids);
		if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){
			openidsMessage.setContent(sendThing);
		}else{
			openidsMessage.setMediaId(sendThing);
		}
		openidsMessage.setMsgType(msgType);
		String  json = openidsMessage.toJson(); 
		String resultJson = wxService.sendMsgByOpenids(json).toJSONString();
		Gson gson = MpGsonBuilder.create();
		MpMassSendResult result = gson.fromJson(resultJson, MpMassSendResult.class);
		return result;
	}

	private Map<String,String> uploadMediaMsg(Long massId){
		Map<String,String> retMap = new HashMap<String, String>();
		List<MaterialContent> mcList = null;
		try{
			mcList = this.getMaterialContentByMassId(massId);
			if(mcList == null || mcList.size() != 1){
				retMap.put(CommonConstants.ERR_CODE, "-99");
				retMap.put(CommonConstants.ERR_MSG, "媒体素材错误");
				return retMap;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		MaterialContent materialContent = mcList.get(0);
		String mediaFileName = materialContent.getLocalMediaPath();
		String type = materialContent.getMaterialTypeDesc();
		return uploadOtherMedia(new File(mediaFileName), type, massId);
			
	}
	
	private Map<String,String> uploadNewMsg(Long massId) {
		Map<String,String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "0");
		List<MaterialContent> mcList = null;
		try{
			mcList = this.getMaterialContentByMassId(massId);
		}catch(Exception e){
			e.printStackTrace();
		}
		MpMassNews massNews = new MpMassNews();
		for (MaterialContent materialContent : mcList) {
			Long contentId = materialContent.getMaterialId();
			String mediaFileName = materialContent.getLocalMediaPath();
			String oldMediaId = materialContent.getMediaId();
			if(oldMediaId != null && !"".equals(oldMediaId)){
				wxService.deleteMaterial(oldMediaId);
			}
			Map<String,String> tempresult = this.uploadNewsMedia(new File(mediaFileName), "thumb", contentId);
			if(!"0".equals(tempresult.get(CommonConstants.ERR_CODE))){
				return tempresult;
			}
			String newsImageMediaId = massMapper.selectMaterialContent(contentId).getMediaId();
			String htmlFileName = materialContent.getHtmlFilePath();
			String title = materialContent.getTitle();
			String author = materialContent.getAuthor();
			String contentSourceUrl = materialContent.getContentSourceUrl();
			MpMassNews.WxMpMassNewsArticle article = new MpMassNews.WxMpMassNewsArticle();
			article.setAuthor(author);
			article.setThumbMediaId(newsImageMediaId);
			article.setContentSourceUrl(contentSourceUrl);
			article.setShowCoverPic(false);
			article.setTitle(title);
			try {
				article.setContent(FileLoadUtil.loadAFileToString(new File(htmlFileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			massNews.addArticle(article);
		}
		String json = massNews.toJson();
		Gson gson = MpGsonBuilder.create();
		String resultJson = wxService.uploadNews(json).toJSONString();
		MpMassUploadResult result = gson.fromJson(resultJson, MpMassUploadResult.class);
		if(result.getErrCode() != null && !"0".equals(result.getErrCode())){
			retMap.put(CommonConstants.ERR_CODE, result.getErrCode());
			retMap.put(CommonConstants.ERR_MSG, result.getErrMsg());
		}
		if(result.getMediaId() != null  && !"".equals(result.getMediaId())){
			String mediaId = result.getMediaId();
			MassEvent me = this.getMassEvent(massId);
			me.setMediaId(mediaId);
			me.setStatusCd(CommonConstants.MASS_STATUS_UPLOAD);
			try{
				massMapper.updateMassEvent(me);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return retMap;
	}

	@Override
	public MassEvent getMassEvent(Long massId) {
		MassEvent me = null;
		try{
			me = massMapper.selectMassEventById(massId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return me;
	}

	@Override
	public List<MaterialContent> getMaterialContentByMassId(Long massId) {
		return massMapper.selectMaterialContentByMassId(massId);
	}

	@Override
	public MpMassSendResult sendByGroupId(Long massId, Long groupId) {
		MassEvent me = this.getMassEvent(massId);
		MpMassSendResult sr = null;
		sr = this.sendByGroupId(me.getMediaId(), me.getTypeDesc(), null);
		if(sr.getMsgId() != null && sr.getMsgId().length() >0){
			me.setStatusCd(CommonConstants.MASS_STATUS_SEND);
			me.setMsgId(sr.getMsgId());
			massMapper.updateMassEvent(me);
		}
		return sr;
	}
	
	/**
	 * 全部发送,提供给servlet调用
	 * @param massId
	 * @return
	 */
	public MpMassSendResult sendAllUser(Long massId){
		return sendByGroupId(massId, null);
	}

	private Map<String,String> uploadOtherMedia(File file, String type,Long massId) {
		Map<String,String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "0");
		JSONObject json = wxService.uploadMedia(file, type);
		Gson gson = MpGsonBuilder.create();
		MpMassUploadResult result = gson.fromJson(json.toJSONString(), MpMassUploadResult.class);
		if(result.getMediaId() != null  && !"".equals(result.getMediaId())){
			MassEvent me = massMapper.selectMassEventById(massId);
			List<MaterialContent> mc = massMapper.selectMaterialContentByMassId(massId);
			for (MaterialContent materialContent : mc) {
				materialContent.setMediaId(result.getMediaId());
				massMapper.updateMaterialContent(materialContent);
			}
			if(!MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)){
				me.setMediaId(result.getMediaId());
				me.setStatusCd(CommonConstants.MASS_STATUS_UPLOAD);
				massMapper.updateMassEvent(me);
			}
		}
		if(result.getErrCode() != null && !"0".equals(result.getErrCode())){
			retMap.put(CommonConstants.ERR_CODE, result.getErrCode());
			retMap.put(CommonConstants.ERR_MSG, result.getErrMsg());
		}
		return retMap;
	}
	
	private Map<String,String> uploadNewsMedia(File file, String type,Long meterialId) {
		Map<String,String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "0");
		JSONObject json = wxService.uploadMedia(file, type);
		Gson gson = MpGsonBuilder.create();
		MpMassUploadResult result = gson.fromJson(json.toJSONString(), MpMassUploadResult.class);
		if(result.getMediaId() != null  && !"".equals(result.getMediaId())){
			MaterialContent mc = massMapper.selectMaterialContent(meterialId);
			mc.setMediaId(result.getMediaId());
			massMapper.updateMaterialContent(mc);
		}
		if(result.getErrCode() != null && !"0".equals(result.getErrCode())){
			retMap.put(CommonConstants.ERR_CODE, result.getErrCode());
			retMap.put(CommonConstants.ERR_MSG, result.getErrMsg());
		}
		return retMap;
	}
	
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public List<MassEvent> queryMassEventList(Map<String,Object> map){
		List<MassEvent> list = null;
		try{
			list = massMapper.queryMassEventList(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取群发消息内容
	 * @param contentId ： 内容ID
	 * @return MaterialContent
	 */
	public MaterialContent queryMaterialContent(Long contentId){
		return massMapper.selectMaterialContent(contentId);
	}
	
	/**
	 * 新增群发消息事件
	 * @param massEvent
	 * @return String
	 */
	public String insertMassEvent(MassEvent massEvent){
		String result = "0";//成功
		try{
			massMapper.insertMassEvent(massEvent);
		}catch(Exception e){
			e.printStackTrace();
			result = "1";//失败
		}
		return result;
	}
	
	/**
	 * 新增群发消息内容
	 * @param materialContent
	 * @return String
	 */
	public String insertMaterialContent(MaterialContent materialContent){
		String result = "0";//成功
		try{
			massMapper.insertMaterialContent(materialContent);
		}catch(Exception e){
			e.printStackTrace();
			result = "1";//失败
		}
		return result;
	}
	
	/**
	 * 编辑群发消息事件
	 * @param massEvent
	 * @return String
	 */
	public String updateMassEvent(MassEvent massEvent){
		String result = "0";//成功
		try{
			massMapper.updateMassEvent(massEvent);
		}catch(Exception e){
			e.printStackTrace();
			result = "1";//失败
		}
		return result;
	}
	
	/**
	 * 编辑群发消息内容
	 * @param materialContent
	 * @return String
	 */
	public String updateMaterialContent(MaterialContent materialContent){
		String result = "0";//成功
		try{
			massMapper.updateMaterialContent(materialContent);
		}catch(Exception e){
			e.printStackTrace();
			result = "1";//失败
		}
		return result;
	}
	
	
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public PageInfo<MassEvent> queryMassEvent(int curPage, int pageSize, int type){
		PageHelper.startPage(curPage, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		List<MassEvent> list = massMapper.queryMassEvent(type);
		for(MassEvent massEvent : list){
			massEvent.setStatusDates(sdf.format(massEvent.getStatusDate()));
		}
		PageInfo<MassEvent> page = new PageInfo<MassEvent>(list);
		return page;
	}

	@Override
	public Map<String, String> uploadMassMaterial(Long massId) {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "-1");
		MassEvent me = massMapper.selectMassEventById(massId);
		if(me != null){
			String type = me.getTypeDesc();
			if(MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)){
				return uploadNewMsg(massId);
			}else if(MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(type)){
				retMap.put(CommonConstants.ERR_CODE, "0");
			}else{
				return uploadMediaMsg(massId);
			}
		}
		return retMap;
	}

	@Override
	public Map<String, String> previewMass(Long massId,String accNbr) {
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "0");
		retMap.put(CommonConstants.ERR_MSG, "预览发送成功");
		String openid = userBoundMapper.queryOpenid(accNbr);
		if(openid != null && !"".equals(openid)){
			String content = null;
			MassEvent me = massMapper.selectMassEventById(massId);
			if(MessageUtil.RESP_MESSAGE_TYPE_TEXT.equalsIgnoreCase(me.getTypeDesc())){
				List<MaterialContent> mc = massMapper.selectMaterialContentByMassId(massId);
				if(mc != null && mc.size() == 1){
					content = mc.get(0).getContent();
				}
			}
			JSONObject jo = wxService.previewMass(openid, me.getMediaId(), me.getTypeDesc(), content);
			if(jo.getString("errcode") != null && !"0".equals(jo.getString("errcode"))){
				retMap.put(CommonConstants.ERR_CODE, jo.getString("errcode"));
				retMap.put(CommonConstants.ERR_MSG, "发送失败");
			}else{
				me.setStatusCd(CommonConstants.MASS_STATUS_PREVIEW);
				massMapper.updateMassEvent(me);
			}
		}else{
			retMap.put(CommonConstants.ERR_CODE, "-1");
			retMap.put(CommonConstants.ERR_MSG, "号码未关注或未绑定");
		}
		return retMap;
	}

	@Override
	public Map<String, String> saveMass(MassParams massParams) {
		Map<String, String> retMap = DataUtil.getSuccessRetMap();
		 try{	
			MassEvent me = massMapper.selectMassEventById(massParams.getMassId());
			boolean meNew = (me == null);
			if(me == null){
				me = new MassEvent();
				me.setMassId(massParams.getMassId());
				me.setMassStyle(massParams.getMassStyle());
				me.setMassDesc(massParams.getMassDesc());
				if(massParams.getMaterialType() == CommonConstants.MSG_TYPE_TEXT){
					me.setStatusCd(CommonConstants.MASS_STATUS_UPLOAD);
				}else{
					me.setStatusCd(CommonConstants.MASS_STATUS_GEN);
				}
				massMapper.insertMassEvent(me);
			}
			MaterialContent mc = new MaterialContent();
			mc.setMaterialId(massParams.getMaterialId());
			mc.setMaterialType(massParams.getMaterialType());
			if(massParams.getLocalMediaPath() != null && massParams.getLocalMediaPath().length() > 0){
				mc.setLocalMediaPath(DataUtil.getFilePathByType(massParams.getLocalMediaPath(), 
						massParams.getMaterialType(), massParams.getCreateFileDate()));
			}
			if(massParams.getHtmlFilePath() != null && massParams.getHtmlFilePath().length() > 0){
				mc.setHtmlFilePath(DataUtil.getFilePathByType(massParams.getHtmlFilePath(), 
						massParams.getMaterialType(), massParams.getCreateFileDate()));
			}
			mc.setAuthor(massParams.getAuthor());
			mc.setContentSourceUrl(massParams.getContentSourceUrl());
			mc.setTitle(massParams.getTitle());
			mc.setContent(massParams.getContent());
			massMapper.insertMaterialContent(mc);
			MassContentRel mcr = new MassContentRel();
			mcr.setMassId(me.getMassId());
			mcr.setMaterialId(mc.getMaterialId());
			massMapper.insertMassContentRel(mcr);
			if(!meNew && me.getStatusCd() != CommonConstants.MASS_STATUS_SEND
					&& me.getStatusCd() != CommonConstants.MASS_STATUS_C
					&& me.getStatusCd() != CommonConstants.MASS_STATUS_GEN){
				if(me.getType() == CommonConstants.MSG_TYPE_TEXT){
					me.setStatusCd(CommonConstants.MASS_STATUS_UPLOAD);
				}else{
					me.setStatusCd(CommonConstants.MASS_STATUS_GEN);
				}
				massMapper.updateMassEvent(me);
			}
		 }catch(Exception e){
			 e.printStackTrace();
			 retMap.put(CommonConstants.ERR_CODE, "-1");
			 retMap.put(CommonConstants.ERR_MSG, "保存失败");
		 }
		return retMap;
	}

	@Override
	public Map<String, String> updateMass(MassParams massParams) {
		Map<String, String> retMap = DataUtil.getSuccessRetMap();
		MassEvent me = massMapper.selectMassEventById(massParams.getMassId());
		MaterialContent mc = massMapper.selectMaterialContent(massParams.getMaterialId());
		if(me == null || mc == null){
			retMap.put(CommonConstants.ERR_CODE, "-1");
			retMap.put(CommonConstants.ERR_MSG, "找不到需要修改的消息");
		}else{
			try{
				if(massParams.getLocalMediaPath()!=null&&massParams.getLocalMediaPath().length()>0 &&
						mc.getLocalMediaPath()!= null && !mc.getLocalMediaPath().equals(massParams.getLocalMediaPath())){
					File mediaFile = new File(mc.getLocalMediaPath());
					if(mediaFile.exists()){
						mediaFile.delete();
					}
				}
				if(massParams.getHtmlFilePath()!= null && massParams.getHtmlFilePath().length()>0 &&
					mc.getHtmlFilePath()!= null && !mc.getHtmlFilePath().equals(massParams.getHtmlFilePath())){
					File htmlFile = new File(mc.getHtmlFilePath());
					if(htmlFile.exists()){
						htmlFile.delete();
					}
				}
				me.setMassDesc(massParams.getMassDesc());
				mc.setAuthor(massParams.getAuthor());
				mc.setContent(massParams.getContent());
				if(massParams.getLocalMediaPath()!=null&&massParams.getLocalMediaPath().length()>0 ){
					mc.setLocalMediaPath(DataUtil.getFilePathByType(massParams.getLocalMediaPath(), 
							massParams.getMaterialType(), massParams.getCreateFileDate()));
				}
				if(massParams.getHtmlFilePath()!= null && massParams.getHtmlFilePath().length()>0 ){
					mc.setHtmlFilePath(DataUtil.getFilePathByType(massParams.getHtmlFilePath(), 
							massParams.getMaterialType(), massParams.getCreateFileDate()));
				}
				mc.setTitle(massParams.getTitle());
				mc.setContentSourceUrl(massParams.getContentSourceUrl());
				massMapper.updateMaterialContent(mc);
				if(me.getType() == CommonConstants.MSG_TYPE_TEXT){
					me.setStatusCd(CommonConstants.MASS_STATUS_UPLOAD);
				}else{
					me.setStatusCd(CommonConstants.MASS_STATUS_GEN);
				}
				me.setMassStyle(massParams.getMassStyle());
				massMapper.updateMassEvent(me);
			}catch(Exception e){
				e.printStackTrace();
				retMap.put(CommonConstants.ERR_CODE, "-1");
				retMap.put(CommonConstants.ERR_MSG, "修改消息发生异常");
			}
		}
		return retMap;
	}

	@Override
	public Long getMassIdBySeq() {
		Long massId = massMapper.getMassIdBySeq();
		return massId;
	}

	@Override
	public Long getMaterialIdBySeq() {
		Long materialId = massMapper.getMaterialIdBySeq();
		return materialId;
	}

	@Override
	public Map<String,String> deleteMassEvent(String massIds, Long materialId) {
		Map<String,String> retMap = DataUtil.getSuccessRetMap();
		String[] massIdList = massIds.split(",", -1);
		for (String massIdString : massIdList) {
			try{
				Long massId = Long.parseLong(massIdString);
				MassEvent me = massMapper.selectMassEventById(massId);
				List<MaterialContent> mcList = massMapper.selectMaterialContentByMassId(massId);
				if(materialId == null){
					for (MaterialContent materialContent : mcList) {
						MassContentRel mcr = new MassContentRel();
						mcr.setMassId(massId);
						mcr.setMaterialId(materialContent.getMaterialId());
						massMapper.deleteMassContentRel(mcr);
						massMapper.deleteMaterialContent(materialContent);
						deleteMedia(materialContent.getMediaId());
					}
					massMapper.deleteMassEvent(me);
					if(me.getType() == CommonConstants.MSG_TYPE_NEWS){
						deleteMedia(me.getMediaId());
					}
				}else{
					MassContentRel mcr = new MassContentRel();
					mcr.setMassId(massId);
					mcr.setMaterialId(materialId);
					massMapper.deleteMassContentRel(mcr);
					MaterialContent mc = new MaterialContent();
					mc.setMaterialId(materialId);
					massMapper.deleteMaterialContent(mc);
					deleteMedia(mc.getMediaId());
					if(massMapper.selectMaterialContentByMassId(massId).size()==0){
						massMapper.deleteMassEvent(me);
						if(me.getType() == CommonConstants.MSG_TYPE_NEWS){
							deleteMedia(me.getMediaId());
						}
					}
				}
			}catch(Exception e){
				retMap.put(CommonConstants.ERR_CODE, "-1");
				retMap.put(CommonConstants.ERR_MSG, "删除消息发生异常");
			}
		}
		
		return retMap;
	}
	
	private void deleteMedia(String mediaId){
		if(mediaId != null && !"".equals(mediaId)){
			try{
				wxService.deleteMaterial(mediaId);
			}catch(Exception e){
				
			}
		}
	}

	@Override
	public void getNewsMaterialImg(Long materialId, HttpServletResponse response) {
		MaterialContent mc = massMapper.selectMaterialContent(materialId);
		if(mc.getLocalMediaPath().length() > 0 && 
				mc.getMaterialType() == CommonConstants.MSG_TYPE_NEWS){
			String imgFilePath = mc.getLocalMediaPath();
			try {
				FileInputStream fis = new FileInputStream(imgFilePath);
				 int size =fis.available(); //得到文件大小   
			     byte data[]=new byte[size];   
			     fis.read(data);  //读数据   
			     fis.close();   
		         response.setContentType("image/gif"); //设置返回的文件类型   
		         OutputStream os = response.getOutputStream();  
		         os.write(data);  
		         os.flush();  
		         os.close();          
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch(Exception e){
				
			}
		}
	}

	@Override
	public void getMediaFile(Long materialId, HttpServletResponse response) {
		FileInputStream fis = null;
		OutputStream os = null;
		try{
			MaterialContent mc = massMapper.selectMaterialContent(materialId);
			String fileName = "";
			if(mc.getHtmlFilePath().length() > 0 &&
					mc.getMaterialType() == CommonConstants.MSG_TYPE_NEWS){
				fis =  new FileInputStream(mc.getHtmlFilePath());
				String[] x = mc.getHtmlFilePath().replaceAll("\\\\", "/").split("/", -1);
				fileName = x[x.length-1];
			}else if(mc.getLocalMediaPath().length() > 0){
				fis = new FileInputStream(mc.getLocalMediaPath());
				String[] x = mc.getLocalMediaPath().replaceAll("\\\\", "/").split("/", -1);
				fileName = x[x.length-1];
			}
			byte[] bs = new byte[1024];
		    // 读取到的数据长度
		    int len;
		    // 输出的文件流
		    os = response.getOutputStream();
		    // 开始读取
		    response.addHeader("Content-Disposition","attachment;filename=\"" +
		    		new String((fileName).getBytes("GB2312"),"iso8859-1")
		    		 +"\"");
		    while ((len = fis.read(bs)) != -1) {
		      os.write(bs, 0, len);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    // 完毕，关闭所有链接
		    try {
		    	if(os != null){
		    		os.flush();
		    		os.close();
		    	}
		    	if(fis != null) {
		    		fis.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
