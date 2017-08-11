package com.skyworthauto.navi.protocol;

public class ProtocolConstant {

    public static final String ACTION_SKY_NAVI_BROADCAST_RECV = "SKYWORTH_AUTONAVI_STANDARD_BROADCAST_RECV";
    public static final String ACTION_SKY_NAVI_BROADCAST_SEND = "SKYWORTH_AUTONAVI_STANDARD_BROADCAST_SEND";

    public static final String KEY_TYPE = "KEY_TYPE";


    public static final int KEY_TYPE_SEND_STATE = 10019;
    public static final String EXTRA_STATE = "EXTRA_STATE";
    public static final int STATE_ACTIVITY_FOREGROUND = 3;
    public static final int STATE_ACTIVITY_BACKGROUND = 4;

    /* 1. Auto的启动与退出
     1.1 进入Auto主图
     说明：通过第三方启动auto，并进入auto的主图页面。
     版本信息：auto1.4.2以上适配渠道版本支持。
     参数说明：
     Action："AUTONAVI_STANDARD_BROADCAST_RECV"
     KEY_TYPE:10034
     SOURCE_APP:第三方应用名称(String)
         1.2 退出Auto
     说明：第三方系统通知Auto，auto接到通知后退出。
     版本信息：auto1.4.2以上适配渠道版本支持。
     参数说明：
     Action："AUTONAVI_STANDARD_BROADCAST_RECV"
     KEY_TYPE:10021
     示例代码：
     Intent intent = new Intent();
     intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
     intent.putExtra("KEY_TYPE", 10021);
     sendBroadcast(intent);
    1.3 Auto最小化（切后台）
     说明：auto启动后，第三方发送相关消息将auto切换至后台。
     版本信息：auto1.4.2以上适配渠道版本支持。
     参数说明：
     Action："AUTONAVI_STANDARD_BROADCAST_RECV"
     KEY_TYPE:10031
     示例代码：
     Intent intent = new Intent();
    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
    intent.putExtra("KEY_TYPE", 10031);
     sendBroadcast(intent);*/
    public static final int KEY_TYPE_OPEN_APP = 10034;
    public static final int KEY_TYPE_CLOSE_APP = 10021;
    public static final int KEY_TYPE_MIN_APP = 10031;


   /* 退出导航（结束引导）
    1) Auto启动；
            2) 车机系统向Auto发送【退出导航】通知，
            3) Auto接收到通知后，退出导航，回到主图。
    具体接口协议：
    说明：第三方通知auto结束引导，退出导航状态，回到主图界面。
    版本信息：auto1.4.3以上适配渠道版本支持。
    参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10010
    示例代码：
    Intent intent = new Intent();
    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
    intent.putExtra("KEY_TYPE", 10010);
    sendBroadcast(intent);*/
    public static final int KEY_TYPE_EXIT_NAVI = 10010;


    /*4. （目的地）直接导航

    说明：auto启动/未启动时，第三方传入终点，auto以车标为起点规划路径并直接进入导航界面。
    版本信息：auto1.4.2以上适配渠道版本支持。
    参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10038
    SOURCE_APP:第三方应用名称(String)
    POINAME:POI 名称(String)
    LAT:（必填）(double)纬度
    LON:（必填）(double)经度
    DEV:（必填）(int)是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
    STYLE：（必填）(int)导航方式
            EXTRA_M = 1（避免收费） =2（多策略算路）=3 （不走高速） =4（躲避拥堵） =5（不走高速且避免收费） =6（不走高速且躲避拥堵）
            =7（躲避收费且躲避拥堵） =8（不走高速躲避收费和拥堵） =20 （高速优先） =24（高速优先且躲避拥堵） =-1（地图内部设置默认规则）
    示例代码：
    Intent intent = new Intent();
    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
    intent.putExtra("KEY_TYPE", 10038);
    intent.putExtra("POINAME","厦门大学");
    intent.putExtra("LAT",24.444593);
    intent.putExtra("LON",118.101011);
    intent.putExtra("DEV",0);
    intent.putExtra("STYLE",0);
    intent.putExtra("SOURCE_APP","Third App");
    sendBroadcast(intent);*/
    public static final int KEY_TYPE_NAVI_WITH_DEST = 10038;
    public static final String SOURCE_APP = "SOURCE_APP";
    public static final String POINAME = "POINAME";
    public static final String LAT = "LAT";
    public static final String LON = "LON";
    public static final String DEV = "DEV";
    public static final String STYLE = "STYLE";


    /*9. 全览状态

    说明：在且仅在导航场景下，通过第三方控制进入或退出全览状态。
    版本信息：auto1.4.2以上适配渠道版本支持。
    参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10006
    EXTRA_IS_SHOW:isShow(int)
    isShow
    进入全览 0
    退出全览 1*/
    public static final int KEY_TYPE_SHOW_PREVIEW = 10006;
    public static final String EXTRA_IS_SHOW = "EXTRA_IS_SHOW";
    public static final int SHOW = 0;
    public static final int NOT_SHOW = 1;


    /*参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10027
    EXTRA_TYPE:类型（int）type
    EXTRA_OPERA:操作（int）opera
    \\地图操作
            * @param type 0 实时路况; 1 缩放地图; 2 视图模式（必填）
            * @param opera type为0：0 实时路况开；1实时路况关
    type为1：0 放大地图； 1缩小地图
    type为2：0切换2d车上； 1切换2d北上；2切换3d车上支持*/
    public static final int KEY_TYPE_CTRL_MAP = 10027;
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_OPERA = "EXTRA_OPERA";

    public static final int EXTRA_TYPE_ROAD_CONDITION = 0;
    public static final int EXTRA_TYPE_ZOOM = 1;
    public static final int EXTRA_TYPE_visual = 2;
    public static final int OPEN_ROAD_CONDITION = 0;
    public static final int CLOSE_ROAD_CONDITION = 1;
    public static final int ZOOM_IN = 0;
    public static final int ZOOM_OUT = 1;
    public static final int CAR_HEAD_UPWARDS = 0;
    public static final int NORTHWARD = 1;
    public static final int THREE_DIMEN_MODE = 2;


    /*4.1 设置昼夜模式
    说明：Auto启动后，第三方发送相应广播信息可设置auto昼夜模式类型：自动、白天、黑夜。
    备注：若用户设置为白天或黑夜后，地图将强制为该模式，不会因为大灯或者时间而改变。
    版本信息：auto1.4.2以上适配渠道版本支持。
    参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10048
    EXTRA_DAY_NIGHT_MODE:昼夜模式类型（int）type
    type 0:自动; 1：白天; 2：黑夜
    示例代码：
    Intent intent = new Intent();
    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
    intent.putExtra("KEY_TYPE", 10048);
    intent.putExtra("EXTRA_DAY_NIGHT_MODE", 0);
    sendBroadcast(intent);*/
    public static final String EXTRA_DAY_NIGHT_MODE = "EXTRA_DAY_NIGHT_MODE";
    public static final int KEY_TYPE_DAY_NIGHT_MODE = 10048;
    public static final int AUTO_MODE = 0;
    public static final int DAY_MODE = 1;
    public static final int NIGHT_MODE = 2;

    /*8. 路线偏好设置（导航场景下）

    说明：仅在导航场景下，支持第三方进行路线偏好的重新选择。
    版本信息：auto1.4.2以上适配渠道版本支持。
    参数说明：
    Action："AUTONAVI_STANDARD_BROADCAST_RECV"
    KEY_TYPE:10005
    NAVI_ROUTE_PREFER:type(int)
    路线偏好对照表
    type                   值
    避免收费                1
    多策略算路              2
    不走高速                3
    躲避拥堵                4
    不走高速且避免收费       5
    不走高速且躲避拥堵       6
    收费和拥堵              7
    不走高速躲避收费和拥堵    8
    高速优先                20
    躲避拥堵且高速优先       24
    地图内部设置默认规则     -1

    示例代码：
    Intent intent = new Intent();
    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
    intent.putExtra("KEY_TYPE", 10005);
    intent.putExtra("NAVI_ROUTE_PREFER", 0);
    sendBroadcast(intent);*/
    public static final int KEY_TYPE_NAVI_ROUTE_PREFER = 10005;

    /*5.10 比例尺放大缩小通知
    说明：Auto启动后，导航与非导航状态下将地图放大缩小状态消息发送给车机系统。
    版本信息：
    参数说明：
    Action: "AUTONAVI_STANDARD_BROADCAST_SEND"
    KEY_TYPE: 10074
    EXTRA_ZOOM_TYPE：(int) 0 放大 1 缩小
    EXTRA_CAN_ZOOM：(boolean) 是否能继续缩放
    示例代码：
    Intent intent = new Intent(StandardProtocol.ACTION_BROADCAST_SEND);
    intent.putExtra(StandardProtocol.KEY_TYPE,10074);
    intent.putExtra(StandardProtocol.EXTRA_AUTO_ZOOM_TYPE,0);
    intent.putExtra(StandardProtocol.EXTRA_AUTO_CAN_ZOOM,false);
    mContext.sendBroadcast(intent);*/
    public static final String EXTRA_ZOOM_TYPE = "EXTRA_ZOOM_TYPE";
    public static final String EXTRA_CAN_ZOOM = "EXTRA_CAN_ZOOM";
    public static final int KEY_TYPE_ZOOM = 10074;
}
