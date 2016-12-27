package com.weixin.common.mapper;

import java.util.List;
import java.util.Map;

import com.weixin.common.model.mass.MaterialContent;
import com.weixin.common.model.mass.MassContentRel;
import com.weixin.common.model.mass.MassEvent;

public interface MassMapper {
	
	/**
	 * 新增群发消息事件
	 * @param massEvent
	 */
	public void insertMassEvent(MassEvent massEvent);
	
	/**
	 * 新增群发消息内容
	 * @param materialContent
	 */
	public void insertMaterialContent(MaterialContent materialContent);
	
	/**
	 * 新增群发消息关联关系
	 * @param massContentRel
	 */
	public void insertMassContentRel(MassContentRel massContentRel);
	
	/**
	 * 获取具体消息事件
	 * @param massId
	 * @return MassEvent
	 */
	public MassEvent selectMassEventById(Long massId);
	
	/**
	 * 通过群发消息事件获取消息内容 
	 * @param massId
	 * @return List<MaterialContent>
	 */
	public List<MaterialContent> selectMaterialContentByMassId(Long massId);

	/**
	 * 获取群发消息内容
	 * @param contentId
	 * @return MassContent
	 */
	public MaterialContent selectMaterialContent(Long contentId);
	
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public List<MassEvent> queryMassEventList(Map<String,Object> map);
	
	/**
	 * 编辑群发消息事件
	 * @param massEvent
	 */
	public void updateMassEvent(MassEvent massEvent);
	
	/**
	 * 编辑群发消息内容
	 * @param massContent
	 * @return String
	 */
	public void updateMaterialContent(MaterialContent materialContent);
	
	/**
	 * 删除群发消息
	 * @param massId
	 * @param contentId ：群发消息内容ID，该字段仅在图文消息时有效
	 */
	public void deleteMassEvent(MassEvent massEvent);
	public void deleteMassContentRel(MassContentRel massContentRel);
	public void deleteMaterialContent(MaterialContent materialContent);
	/**
	 * 获取群发事件列表
	 * @param type：事件类型
	 * @return List<MassEvent>
	 */
	public List<MassEvent> queryMassEvent(int type);

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
}
