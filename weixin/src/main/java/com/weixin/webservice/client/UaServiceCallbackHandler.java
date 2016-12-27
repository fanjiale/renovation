
/**
 * UaServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package com.weixin.webservice.client;

    /**
     *  UaServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class UaServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public UaServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public UaServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getCheckResult method
            * override this method for handling normal response from getCheckResult operation
            */
           public void receiveResultgetCheckResult(
                    com.weixin.webservice.client.UaServiceStub.GetCheckResultResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCheckResult operation
           */
            public void receiveErrorgetCheckResult(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for userPWDunlock method
            * override this method for handling normal response from userPWDunlock operation
            */
           public void receiveResultuserPWDunlock(
                    com.weixin.webservice.client.UaServiceStub.UserPWDunlockResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from userPWDunlock operation
           */
            public void receiveErroruserPWDunlock(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for userStatusQuery method
            * override this method for handling normal response from userStatusQuery operation
            */
           public void receiveResultuserStatusQuery(
                    com.weixin.webservice.client.UaServiceStub.UserStatusQueryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from userStatusQuery operation
           */
            public void receiveErroruserStatusQuery(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for dynamicPWD method
            * override this method for handling normal response from dynamicPWD operation
            */
           public void receiveResultdynamicPWD(
                    com.weixin.webservice.client.UaServiceStub.DynamicPWDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from dynamicPWD operation
           */
            public void receiveErrordynamicPWD(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authReq method
            * override this method for handling normal response from authReq operation
            */
           public void receiveResultauthReq(
                    com.weixin.webservice.client.UaServiceStub.AuthReqResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authReq operation
           */
            public void receiveErrorauthReq(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for idCardCheck method
            * override this method for handling normal response from idCardCheck operation
            */
           public void receiveResultidCardCheck(
                    com.weixin.webservice.client.UaServiceStub.IdCardCheckResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from idCardCheck operation
           */
            public void receiveErroridCardCheck(java.lang.Exception e) {
            }
                


    }
    