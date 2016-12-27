package com.weixin.common.model.mass.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();
  
  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(MpMassNews.class, new MpMassNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(MpMassGroupMessage.class, new MpMassGroupMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(MpMassOpenIdsMessage.class, new MpMassOpenIdsMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(MpGroup.class, new MpGroupGsonAdapter());
    INSTANCE.registerTypeAdapter(MpUserList.class, new UserListGsonAdapter());
    INSTANCE.registerTypeAdapter(MpMassVideo.class, new MpMassVideoAdapter());
    INSTANCE.registerTypeAdapter(MpMassSendResult.class, new MpMassSendResultAdapter());
    INSTANCE.registerTypeAdapter(MpMassUploadResult.class, new MpMassUploadResultAdapter());
  }
  
  public static Gson create() {
    return INSTANCE.create();
  }
  
}
