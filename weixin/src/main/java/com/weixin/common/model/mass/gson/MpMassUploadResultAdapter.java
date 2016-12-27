package com.weixin.common.model.mass.gson;

/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * 
 * @author Daniel Qian
 *
 */
public class MpMassUploadResultAdapter implements JsonDeserializer<MpMassUploadResult> {

  public MpMassUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    MpMassUploadResult uploadResult = new MpMassUploadResult();
    JsonObject uploadResultJsonObject = json.getAsJsonObject();

    if (uploadResultJsonObject.get("type") != null && !uploadResultJsonObject.get("type").isJsonNull()) {
      uploadResult.setType(GsonHelper.getAsString(uploadResultJsonObject.get("type")));
    }
    if (uploadResultJsonObject.get("media_id") != null && !uploadResultJsonObject.get("media_id").isJsonNull()) {
      uploadResult.setMediaId(GsonHelper.getAsString(uploadResultJsonObject.get("media_id")));
    }
    if (uploadResultJsonObject.get("created_at") != null && !uploadResultJsonObject.get("created_at").isJsonNull()) {
      uploadResult.setCreatedAt(GsonHelper.getAsPrimitiveLong(uploadResultJsonObject.get("created_at")));
    }
    if (uploadResultJsonObject.get("errcode") != null && !uploadResultJsonObject.get("errcode").isJsonNull()) {
        uploadResult.setErrCode(GsonHelper.getAsString(uploadResultJsonObject.get("errcode")));
      }
    if (uploadResultJsonObject.get("errmsg") != null && !uploadResultJsonObject.get("errmsg").isJsonNull()) {
        uploadResult.setErrMsg(GsonHelper.getAsString(uploadResultJsonObject.get("errmsg")));
      }
    return uploadResult;
  }
  
}
