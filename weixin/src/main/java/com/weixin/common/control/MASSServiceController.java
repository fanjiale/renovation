package com.weixin.common.control;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.andy.common.domains.Page;
import com.andy.common.domains.RspResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.core.GroupMsgService;
import com.weixin.common.core.WxService;
import com.weixin.common.model.mass.MassEvent;
import com.weixin.common.model.mass.MassParams;
import com.weixin.common.model.mass.MaterialContent;
import com.weixin.common.model.mass.gson.MpMassSendResult;
import com.weixin.common.util.DataUtil;

/**
 * 群发消息 
 */
@Controller
@RequestMapping("/mass")
public class MASSServiceController {
   
	@Autowired
	private GroupMsgService groupMsgService;
	@Autowired
	private WxService wxService;
	@Autowired
	private ConfigLoader configLoader;
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return
	 */
	@RequestMapping("queryMassEventList")
	@ResponseBody
	public RspResult queryMassEventList(Map<String,Object> map){
		List<MassEvent> list = groupMsgService.queryMassEventList(map);
		return RspResult.getSuccessResult().setData(list);
	}
	
	/**
	 * 新增群发消息事件
	 * @param massEvent
	 * @return
	 */
	@RequestMapping("insertMassEvent")
	@ResponseBody
	public RspResult insertMassEvent(MassEvent massEvent){
		String res = groupMsgService.insertMassEvent(massEvent);
		return RspResult.getSuccessResult().setData(res);
	}
	
	/**
	 * 编辑群发消息事件
	 * @param massEvent
	 * @return
	 */
	@RequestMapping("updateMassEvent")
	@ResponseBody
	public RspResult updateMassEvent(MassEvent massEvent){
		String res = groupMsgService.updateMassEvent(massEvent);
		return RspResult.getSuccessResult().setData(res);
	}
	
	/**
	 * 新增群发消息内容
	 * @param massEvent
	 * @return
	 */
	@RequestMapping("insertMaterialContent")
	@ResponseBody
	public RspResult insertMaterialContent(MaterialContent materialContent){
		String res = groupMsgService.insertMaterialContent(materialContent);
		return RspResult.getSuccessResult().setData(res);
	}
	
	/**
	 * 编辑群发消息内容
	 * @param massEvent
	 * @return
	 */
	@RequestMapping("updateMaterialContent")
	@ResponseBody
	public RspResult updateMaterialContent(MaterialContent materialContent){
		String res = groupMsgService.updateMaterialContent(materialContent);
		return RspResult.getSuccessResult().setData(res);
	}
	
	/**
	 * 删除群发消息
	 * @param massContentRel
	 * @return
	 */
	@RequestMapping("deleteMassEvent")
	@ResponseBody
	public RspResult deleteMassEvent(MassParams massParams){
		Map<String,String> res = groupMsgService.deleteMassEvent(massParams.getMassIdsStr(),massParams.getMaterialId());
		return RspResult.getSuccessResult().setData(res);
	}
	
	/**
	 * 按组发送群发消息
	 * @param massId
	 * @param groupId
	 * @return
	 */
	@RequestMapping("sendByGroup")
	@ResponseBody
	public RspResult sendByGroup(Long massId,Long groupId){
		MpMassSendResult result =groupMsgService.sendByGroupId(massId, groupId);
		return RspResult.getSuccessResult().setData(result.getErrorCode());
	}
	
	/**
	 * 按用户发送群发消息（暂不用）
	 * @param massId
	 * @param 
	 * @return
	 */
	@RequestMapping("sendByOpenids")
	@ResponseBody
	public RspResult sendByOpenids(String sendThing, String msgType){
		List<String> openids = new ArrayList<String>();
		MpMassSendResult result = groupMsgService.sendByOpenids(sendThing, msgType,openids);
		return RspResult.getSuccessResult().setData(result.getErrorCode());
	}
	
	/**
	 * 全部发送群发消息
	 * @param massId
	 * @param groupId
	 * @return
	 */
	@RequestMapping("sendMassToFans")
	@ResponseBody
	public RspResult sendMassToFans(Long massId){
		MpMassSendResult result =groupMsgService.sendAllUser(massId);
		return RspResult.getSuccessResult().setData(result.getErrorCode());
	}
	
	/**
	 * 通过群发消息事件获取消息内容 
	 * @param massId
	 * @return
	 */
	@RequestMapping("getMaterialContentByMassId")
	@ResponseBody
	public RspResult getMaterialContentByMassId(Long massId){
		List<MaterialContent> list = groupMsgService.getMaterialContentByMassId(massId);
		return RspResult.getSuccessResult().setData(list);
	}
	
	/**
	 * 获取具体消息事件
	 * @param massId
	 * @return
	 */
	@RequestMapping("getMassEvent")
	@ResponseBody
	public RspResult getMassEvent(Long massId){
		MassEvent massEvent = groupMsgService.getMassEvent(massId);
		List<MaterialContent> matList = groupMsgService.getMaterialContentByMassId(massId);
		List<MassParams> list = new ArrayList<MassParams>();
		for (MaterialContent mat : matList) {
			MassParams mp = new MassParams();
			mp.setAuthor(mat.getAuthor());
			mp.setContent(mat.getContent());
			mp.setContentSourceUrl(mat.getContentSourceUrl());
			mp.setCreateFileDate(mat.getCreateFileDate());
			mp.setHtmlFilePath(mat.getHtmlFilePath());
			mp.setLocalMediaPath(mat.getLocalMediaPath());
			mp.setMassId(massEvent.getMassId());
			mp.setMassStyle(mat.getMaterialType());
			mp.setMaterialId(mat.getMaterialId());
			mp.setMassDesc(massEvent.getMassDesc());
			mp.setMassStyle(massEvent.getMassStyle());
			mp.setTitle(mat.getTitle());
			list.add(mp);
		}
		return RspResult.getSuccessResult().setData(list);
	}

	
	/**
	 * 上传文件
	 * @param req
	 * @param res
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("uploadFile")
	@ResponseBody
	public RspResult uploadFile(HttpServletRequest request,HttpServletResponse response)
			throws UnsupportedEncodingException{
		String result = "0"; //成功
		request.setCharacterEncoding("utf-8");
		ServletFileUpload sfu = new ServletFileUpload();
		boolean ismultipart = ServletFileUpload.isMultipartContent(request);
		if(ismultipart){
			try{
				FileItemIterator fii = sfu.getItemIterator(request);
				
				//对表单域的处理,大文件上传会分段
				int chunk = 0;//上传的当前段
				String createFileDate = "";
				
				int msgType =0;
				while(fii.hasNext()){
					FileItemStream fis = fii.next();
					String fieldName = fis.getFieldName();
					if(fis.isFormField()){//对表单域的处理
						if("chunk".equals(fieldName)){
							BufferedReader br = new BufferedReader(new InputStreamReader(fis.openStream()));
							String fieldValue = br.readLine();
							br.close();
							chunk = Integer.valueOf(fieldValue);
						}
						if("createDate".equals(fieldName)){
							BufferedReader br = new BufferedReader(new InputStreamReader(fis.openStream()));
							String fieldValue = br.readLine();
							br.close();
							createFileDate = String.valueOf(fieldValue);
							
						}
						if("msgType".equals(fieldName)){
							BufferedReader br = new BufferedReader(new InputStreamReader(fis.openStream()));
							String fieldValue = br.readLine();
							br.close();
							msgType = Integer.valueOf(fieldValue);
						}
					}else{//对上传的文件的处理
						if(!fis.isFormField()){
							String fname = fis.getName();
							String filePath = DataUtil.getFilePathByType(fname,msgType,createFileDate);
							BufferedInputStream bis = new BufferedInputStream(fis.openStream());
							File f = new File(filePath);
							if (!f.getParentFile().exists()) {  
				                f.getParentFile().mkdirs();  
				            }  
							//判断同名文件有没有存在,如存在，则删除,重新创建
							if(chunk == 0 && f.exists()){
								f.delete();
								f = new File(filePath);
							}
							BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f,f.exists()));
							byte b[] = new byte[1024];
							int len = 0;
							while((len = bis.read(b)) != -1){
								bos.write(b, 0, len);
							}
							bis.close();
							bos.close();
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				result = "1";//失败
			}
		}
		return RspResult.getSuccessResult().setData(result);
	}
	
//	/**
//	 * 保存配置（单条图文消息、视频、语音、文本）
//	 * @param req 包含参数
//	 * @param res
//	 * @return
//	 */
//	@RequestMapping("save")
//	@ResponseBody
//	public RspResult save(HttpServletRequest req,HttpServletResponse res){
//		String result = "0";
//		return RspResult.getSuccessResult().setData(result);
//	}
	
	
//	@SuppressWarnings("unchecked")
//	protected boolean upload(HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		request.setCharacterEncoding("utf-8");
//		ServletFileUpload sfu = new ServletFileUpload();
//		boolean ismultipart = ServletFileUpload.isMultipartContent(request);
//		if(ismultipart){
//			try{
//				FileItemIterator fii = sfu.getItemIterator(request);
//				
//				//对表单域的处理,大文件上传会分段
//				int chunk = 0;//上传的当前段
//				while(fii.hasNext()){
//					FileItemStream fis = fii.next();
//					String fieldName = fis.getFieldName();
//					if(fis.isFormField()){//对表单域的处理
//						if("chunk".equals(fieldName)){
//							BufferedReader br = new BufferedReader(new InputStreamReader(fis.openStream()));
//							String fieldValue = br.readLine();
//							br.close();
//							chunk = Integer.valueOf(fieldValue);
//						}
//					}else{//对上传的文件的处理
//						if(!fis.isFormField()){
//							String fname = fis.getName();
//							BufferedInputStream bis = new BufferedInputStream(fis.openStream());
//							File f = new File(LOCAL_PATH+fname);
//							//判断同名文件有没有存在,如存在，则删除,重新创建
//							if(chunk == 0 && f.exists()){
//								f.delete();
//								f = new File(LOCAL_PATH+fname);
//							}
//							BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f,f.exists()));
//							byte b[] = new byte[1024];
//							int len = 0;
//							while((len = bis.read(b)) != -1){
//								bos.write(b, 0, len);
//							}
//							bis.close();
//							bos.close();
//						}
//					}
//				}
//				return true;
//			}catch(Exception e){
//				e.printStackTrace();
//				return false;
//			}
//		}
//		return false;
//	}

	

	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return
	 */
	@RequestMapping("queryMassMessage")
	@ResponseBody
	public RspResult queryMassEvent(Page page,int type){
		PageInfo<MassEvent> pageInfo = groupMsgService.queryMassEvent(page.getCurPage(), page.getPageSize(), type);
		return RspResult.getSuccessResult().setData(pageInfo);
	}
	
	/**
	 * 上传消息事件素材到微信服务器
	 * @param massId
	 * @return
	 */
	@RequestMapping("uploadMassMaterial")
	@ResponseBody
	public RspResult uploadMassMaterial(Long massId){
		Map<String,String> retMap = groupMsgService.uploadMassMaterial(massId);
		return RspResult.getSuccessResult().setData(retMap);
	}
	
	/**
	 * 预览消息
	 * @param massId
	 * @param accNbr 号码，必须已经关注并绑定
	 * @return
	 */
	@RequestMapping("previewMass")
	@ResponseBody
	public RspResult previewMass(MassParams massParams){
		Long massId = massParams.getMassId();
		String accNbr = massParams.getAccNbr();
		Map<String,String> retMap = groupMsgService.previewMass(massId,accNbr);
		return RspResult.getSuccessResult().setData(retMap);
	}
	
	/**
	 * 保存消息
	 * @param massParams
	 * @return
	 */
	@RequestMapping("saveMass")
	@ResponseBody
	public RspResult saveMass(MassParams massParams){
		Map<String,String> retMap = groupMsgService.saveMass(massParams);
		return RspResult.getSuccessResult().setData(retMap);
	}
	
	/**
	 * 修改消息
	 * @param massParams
	 * @return
	 */
	@RequestMapping("updateMass")
	@ResponseBody
	public RspResult updateMass(MassParams massParams){
		Map<String,String> retMap = groupMsgService.updateMass(massParams);
		return RspResult.getSuccessResult().setData(retMap);
	}
	
	/**
	 * 获得MassId
	 * @param 
	 * @return
	 */
	@RequestMapping("getMassIdBySeq")
	@ResponseBody
	public RspResult getMassIdBySeq(){
		Long massId = groupMsgService.getMassIdBySeq();
		return RspResult.getSuccessResult().setData(massId);
	}
	
	/**
	 * 获得MaterialId
	 * @param 
	 * @return
	 */
	@RequestMapping("getMaterialIdBySeq")
	@ResponseBody
	public RspResult getMaterialIdBySeq(){
		Long materialId = groupMsgService.getMaterialIdBySeq();
		return RspResult.getSuccessResult().setData(materialId);
	}
	
	/**
	 * 获取展示图文消息图片
	 * @param 
	 * @return
	 */
	@RequestMapping(value ="getNewsMaterialImg", method = RequestMethod.GET)
	@ResponseBody
	public void getNewsMaterialImg(Long materialId,HttpServletResponse response){
		groupMsgService.getNewsMaterialImg(materialId,response);
	}
	
	/**
	 * 获取媒体文件
	 * @param 
	 * @return
	 */
	@RequestMapping(value ="getMediaFile", method = RequestMethod.GET)
	@ResponseBody
	public void getMediaFile(Long materialId,HttpServletResponse response){
		groupMsgService.getMediaFile(materialId,response);
	}
}
