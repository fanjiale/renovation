package com.weixin.common.core;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.weixin.common.model.mass.MassParams;
import com.weixin.common.model.mass.MaterialContent;
import com.weixin.common.model.mass.MassEvent;
import com.weixin.common.model.mass.gson.MpMassSendResult;

public interface GroupMsgService {

	/**
	 * 按用户分组发送
	 * @param sendThing 发送的内容图文视频 mediaId或文本content
	 * @param msgType 发送消息类型
	 * @param gourpId 要发送的用户分组(=NULL时全部发送)
	 * @return
	 */
	public MpMassSendResult sendByGroupId(String sendThing,String msgType,Long groupId);
	
	/**
	 * 按照部分关注者OPENID列表发送
	 * @param sendThing 发送的内容图文视频 mediaId或文本content
	 * @param msgType 发送消息类型
	 * @param openids 关注着列表
	 * @return
	 */
	public MpMassSendResult sendByOpenids(String sendThing,String msgType,List<String> openids);
	
	
	/**
	 * 获取具体消息事件
	 * @param massId
	 * @return
	 */
	public MassEvent getMassEvent(Long massId);
	
	
	/**
	 * 通过群发消息事件获取消息内容
	 * @param massId
	 * @return
	 */
	public List<MaterialContent> getMaterialContentByMassId(Long massId);

	/**
	 * 按用户分组发送，提供给servlet调用
	 * @param massId
	 * @param groupId
	 */
	public MpMassSendResult sendByGroupId(Long massId, Long groupId);
	
	/**
	 * 全部发送,提供给servlet调用
	 * @param massId
	 * @return
	 */
	public MpMassSendResult sendAllUser(Long massId);

	
	
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public List<MassEvent> queryMassEventList(Map<String,Object> map);
	
	
	/**
	 * 获取群发消息内容
	 * @param contentId ： 内容ID
	 * @return MaterialContent
	 */
	public MaterialContent queryMaterialContent(Long contentId);
	
	/**
	 * 新增群发消息事件
	 * @param massEvent
	 * @return String
	 */
	public String insertMassEvent(MassEvent massEvent);
	
	/**
	 * 新增群发消息内容
	 * @param massContent
	 * @return String
	 */
	public String insertMaterialContent(MaterialContent massContent);
	
	/**
	 * 编辑群发消息事件
	 * @param massEvent
	 * @return String
	 */
	public String updateMassEvent(MassEvent massEvent);
	
	/**
	 * 编辑群发消息内容
	 * @param massContent
	 * @return String
	 */
	public String updateMaterialContent(MaterialContent massContent);
	
	/**
	 * 删除群发消息
	 * @param massContentRel
	 * @return String
	 */
	public Map<String,String> deleteMassEvent(String massId,Long materialId);
	
	
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public PageInfo<MassEvent> queryMassEvent(int curPage, int pageSize, int type);
	
	/**
	 * 上传消息事件素材
	 * @param massId
	 * @return
	 */
	public Map<String,String> uploadMassMaterial(Long massId);

	/**
	 * 预览消息
	 * @param massId
	 * @param accNbr 号码，必须已经关注并绑定
	 * @return
	 */
	public Map<String, String> previewMass(Long massId, String accNbr);

	/**
	 * 保存消息
	 * @param massParams
	 * @return
	 */
	public Map<String, String> saveMass(MassParams massParams);

	/**
	 * 修改消息
	 * @param massParams
	 * @return
	 */
	public Map<String, String> updateMass(MassParams massParams);

	/**
	 * 获取序列
	 * @return
	 */
	public Long getMassIdBySeq();

	/**
	 * 获取序列
	 * @return
	 */
	public Long getMaterialIdBySeq();

	public void getNewsMaterialImg(Long materialId, HttpServletResponse response);

	public void getMediaFile(Long materialId, HttpServletResponse response);

}
