package com.weixin.common.mapper;

import com.weixin.common.model.token.AccessToken;

public interface AccessTokenMapper {

	/**
	 * 获取AccessToken
	 * @return
	 */
	AccessToken getAccessToken();
	
	/**
	 * 更新AccessToken
	 * @param accessToken
	 */
	void updateAccessToken(AccessToken accessToken);

	void insertAccessToken(AccessToken accessToken);
}
