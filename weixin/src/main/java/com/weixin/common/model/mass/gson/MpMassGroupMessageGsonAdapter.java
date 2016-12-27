package com.weixin.common.model.mass.gson;

/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.weixin.common.util.MessageUtil;

import java.lang.reflect.Type;

public class MpMassGroupMessageGsonAdapter implements JsonSerializer<MpMassGroupMessage> {

  public JsonElement serialize(MpMassGroupMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    
    JsonObject filter = new JsonObject();
    if(null == message.getGroupId()) {
      filter.addProperty("is_to_all", true);
    } else {
      filter.addProperty("is_to_all", false);
      filter.addProperty("group_id", message.getGroupId());
    }
    messageJson.add("filter", filter);
    
    if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(MessageUtil.RESP_MESSAGE_TYPE_NEWS, sub);
    }
    if (MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("content", message.getContent());
      messageJson.add(MessageUtil.RESP_MESSAGE_TYPE_TEXT, sub);
    }
    if (MessageUtil.RESP_MESSAGE_TYPE_VOICE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(MessageUtil.RESP_MESSAGE_TYPE_VOICE, sub);
    }
    if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(MessageUtil.RESP_MESSAGE_TYPE_IMAGE, sub);
    }
    if (MessageUtil.RESP_MESSAGE_TYPE_VIDEO.equals(message.getMsgtype())) {
      JsonObject sub = new JsonObject();
      sub.addProperty("media_id", message.getMediaId());
      messageJson.add(MessageUtil.RESP_MESSAGE_TYPE_VIDEO, sub);
    }
    messageJson.addProperty("msgtype", message.getMsgtype());
    return messageJson;
  }

}
