package com.ruimin.xjd.user.JPUSH.server;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
import com.ruimin.xjd.user.JPUSH.JpushRequest;
 
 @Slf4j
public class JpushClientUtil {
 
    private String appKey;
    private String masterSecret ;
	private JPushClient jPushClient;
	
	public JpushClientUtil(String masterSecret,String appKey) {
		jPushClient = new JPushClient (masterSecret, appKey );
	}
    /**
     * 推送给设备标识参数的用户
     * @param registrationId 设备标识
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public  int sendToRegistrationId(JpushRequest req,String andrAndIos)throws Exception {
        int result = 0;
        try {
        	PushPayload pushPayload = null;
        	if(!"".equals(andrAndIos)&&andrAndIos!=null&&!"null".equalsIgnoreCase(andrAndIos)){
        		log.info("andros------------------");
        		pushPayload = JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle
                		(req.getRegistrationId(),req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam(),req.getStatusType(),req.getAlTitle(),req.getAlContent(),req.getBadge(),req.getWebUrl());
        	}else{
        		log.info("IOS------------------");
        		pushPayload = JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle_IOS
                		(req.getRegistrationId(),req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam(),req.getStatusType(),req.getAlTitle(),req.getAlContent(),req.getBadge(),req.getWebUrl());
        	}
           /* pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle
            		(req.getRegistrationId(),req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam());
            log.info("JPUSH发送请求："+pushPayload);*/
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            log.info("JPUSH返回请求："+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
            log.error("JPUSH 异常",e);
            e.printStackTrace();
            throw e;
        }
 
         return result;
    }
 
    /**
     * 发送给所有安卓用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public   int sendToAllAndroid(JpushRequest req) throws Exception{
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_all_alertWithTitle
            		(req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam());
            log.info("JPUSH发送请求："+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            log.info("JPUSH返回请求："+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
            log.error("JPUSH 异常",e);
            e.printStackTrace();
            throw e;
        }
 
         return result;
    }
 
    /**
     * 发送给所有IOS用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public   int sendToAllIos(JpushRequest req)throws Exception {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_ios_all_alertWithTitle
            		(req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam());
            log.info("JPUSH发送请求："+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            log.info("JPUSH返回请求："+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
            log.error("JPUSH 异常",e);
            e.printStackTrace();
            throw e;
        }
 
         return result;
    }
 
    /**
     * 发送给所有用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public   int sendToAll( JpushRequest req) throws Exception {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios
            		(req.getNotification_title(),req.getMsg_title(),req.getMsg_content(),req.getExtrasparam());
            log.info("JPUSH发送请求："+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            log.info("JPUSH返回请求："+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
            log.error("JPUSH 异常",e);
            e.printStackTrace();
            throw e;
        }
 
        return result;
    }
 
 
 
    public static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)
 
                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }
 
    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(
    		String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam,String statusType,String alTitle,String alContent,String badge,String webUrl) {
 
        System.out.println("----------buildPushObject_all_all_alert");
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
        Map<String,String> jpushMap=new HashMap<String, String>();
        jpushMap.put("statusType", statusType);
        jpushMap.put("alTitle", alTitle);
        jpushMap.put("alContent", alContent);
        jpushMap.put("webUrl", webUrl);
        jpushMap.put("badge", badge);
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                        		 .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                .addExtras(jpushMap)
                               
                                .build())
                        //指定当前推送的iOS通知
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                //传一个IosAlert对象，指定apns title、title、subtitle等
//                                .setAlert(notification_title)
//                                //直接传alert
//                                //此项是指定此推送的badge自动加1
//                                .incrBadge(1)
//                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
//                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
//                                .setSound("sound.caf")
//                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
//                                .addExtra("iosNotification extras key",extrasparam)
//                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
//                                //取消此注释，消息推送时ios将无法在锁屏情况接收
//                                // .setContentAvailable(true)
// 
//                                .build())
 
 
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                		
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
//                        .addExtra("message extras key",extrasparam)
 
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
 
                        .build())
 
                .build();
 
    }
 
    /**
     * IOS
     * @param registrationId
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private static PushPayload buildPushObject_all_registrationId_alertWithTitle_IOS(
    		String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam,String statusType,String alTitle,String alContent,String badge,String webUrl) {
    	
        log.info("IOS---------------//////////----------2222222222222");
        Map<String,String> jpushMap=new HashMap<String, String>();
        jpushMap.put("statusType", statusType);
        jpushMap.put("alTitle", alTitle);
        jpushMap.put("alContent", alContent);
        jpushMap.put("webUrl", webUrl);
        //jpushMap.put("badge", badge);
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                        		
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .setBadge(Integer.parseInt(badge))
                                //.incrBadge(Integer.parseInt(badge))
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                .addExtras(jpushMap)
                                
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
 
                                .build())
 
 
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                		
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
//                        .addExtra("message extras key",extrasparam)
 
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
 
                        .build())
 
                .build();
 
    }
    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
        System.out.println("----------buildPushObject_android_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
 
    private static PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
        System.out.println("----------buildPushObject_ios_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                               // .setContentAvailable(true)
 
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
 
//    public static void main(String[] args){
//        if(JpushClientUtil.sendToAllIos("testIos","testIos","this is a ios Dev test","")==1){
//            System.out.println("success");
//        }
//    }
}