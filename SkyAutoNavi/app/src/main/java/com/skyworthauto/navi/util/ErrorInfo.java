package com.skyworthauto.navi.util;

import com.amap.api.services.core.AMapException;

import java.util.HashMap;
import java.util.Map;

/**
 * -1	路径计算失败。	在导航过程中调用calculateDriveRoute方法导致的失败，导航过程中只能用reCalculate方法进行路径计算。
 * 1	路径计算成功。
 * 2	网络超时或网络失败,请检查网络是否通畅，稍候再试。
 * 3	路径规划起点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。
 * 4	协议解析错误,请稍后再试。
 * 6	路径规划终点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。
 * 7	算路服务端编码失败.
 * 10	起点附近没有找到可行道路,请对起点进行调整。
 * 11	终点附近没有找到可行道路,请对终点进行调整。
 * 12	途经点附近没有找到可行道路,请对途经点进行调整。
 * 13	key鉴权失败。	请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过;高频问题查找相关解决办法。
 * 14	请求的服务不存在,	请稍后再试。
 * 15	请求服务响应错误,请检查网络状况，稍后再试。
 * 16	无权限访问此服务,请稍后再试。
 * 17	请求超出配额。
 * 18	请求参数非法,请检查传入参数是否符合要求。
 * 19	未知错误。
 **/
public class ErrorInfo {
    private static Map<Integer, String> list = new HashMap();

    static {
        list.put(-1, "路径计算失败，在导航过程中调用calculateDriveRoute方法导致的失败，导航过程中只能用reCalculate方法进行路径计算。");
        list.put(1, "路径计算成功。");
        list.put(2, "网络超时或网络失败,请检查网络是否通畅，如网络没问题,查看Logcat输出是否出现鉴权错误信息，如有，说明SHA1与KEY不对应导致。");
        list.put(3, "路径规划起点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。");
        list.put(4, "协议解析错误,请稍后再试。");
        list.put(6, "路径规划终点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。");
        list.put(7, "算路服务端编码失败.");
        list.put(10, "起点附近没有找到可行道路,请对起点进行调整。");
        list.put(11, "终点附近没有找到可行道路,请对终点进行调整。");
        list.put(12, "途经点附近没有找到可行道路,请对途经点进行调整。");
        list.put(13, "key鉴权失败，请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过;高频问题查找相关解决办法。");
        list.put(14, "请求的服务不存在,请稍后再试。");
        list.put(15, "请求服务响应错误,请检查网络状况，稍后再试。");
        list.put(16, "无权限访问此服务,请稍后再试。");
        list.put(17, "请求超出配额。");
        list.put(18, "请求参数非法,请检查传入参数是否符合要求。");
        list.put(19, "未知错误。");

    }

    public static String getError(int id) {
        return list.get(id);
    }

    public static String getSearchError(int rCode) {

        switch (rCode) {
            //服务错误码
            case 1001:
                return AMapException.AMAP_SIGNATURE_ERROR;
            case 1002:
                return AMapException.AMAP_INVALID_USER_KEY;
            case 1003:
                return AMapException.AMAP_SERVICE_NOT_AVAILBALE;
            case 1004:
                return AMapException.AMAP_DAILY_QUERY_OVER_LIMIT;
            case 1005:
                return AMapException.AMAP_ACCESS_TOO_FREQUENT;
            case 1006:
                return AMapException.AMAP_INVALID_USER_IP;
            case 1007:
                return AMapException.AMAP_INVALID_USER_DOMAIN;
            case 1008:
                return AMapException.AMAP_INVALID_USER_SCODE;
            case 1009:
                return AMapException.AMAP_USERKEY_PLAT_NOMATCH;
            case 1010:
                return AMapException.AMAP_IP_QUERY_OVER_LIMIT;
            case 1011:
                return AMapException.AMAP_NOT_SUPPORT_HTTPS;
            case 1012:
                return AMapException.AMAP_INSUFFICIENT_PRIVILEGES;
            case 1013:
                return AMapException.AMAP_USER_KEY_RECYCLED;
            case 1100:
                return AMapException.AMAP_ENGINE_RESPONSE_ERROR;
            case 1101:
                return AMapException.AMAP_ENGINE_RESPONSE_DATA_ERROR;
            case 1102:
                return AMapException.AMAP_ENGINE_CONNECT_TIMEOUT;
            case 1103:
                return AMapException.AMAP_ENGINE_RETURN_TIMEOUT;
            case 1200:
                return AMapException.AMAP_SERVICE_INVALID_PARAMS;
            case 1201:
                return AMapException.AMAP_SERVICE_MISSING_REQUIRED_PARAMS;
            case 1202:
                return AMapException.AMAP_SERVICE_ILLEGAL_REQUEST;
            case 1203:
                return AMapException.AMAP_SERVICE_UNKNOWN_ERROR;
            //sdk返回错误
            case 1800:
                return AMapException.AMAP_CLIENT_ERRORCODE_MISSSING;
            case 1801:
                return AMapException.AMAP_CLIENT_ERROR_PROTOCOL;
            case 1802:
                return AMapException.AMAP_CLIENT_SOCKET_TIMEOUT_EXCEPTION;
            case 1803:
                return AMapException.AMAP_CLIENT_URL_EXCEPTION;
            case 1804:
                return AMapException.AMAP_CLIENT_UNKNOWHOST_EXCEPTION;
            case 1806:
                return AMapException.AMAP_CLIENT_NETWORK_EXCEPTION;
            case 1900:
                return AMapException.AMAP_CLIENT_UNKNOWN_ERROR;
            case 1901:
                return AMapException.AMAP_CLIENT_INVALID_PARAMETER;
            case 1902:
                return AMapException.AMAP_CLIENT_IO_EXCEPTION;
            case 1903:
                return AMapException.AMAP_CLIENT_NULLPOINT_EXCEPTION;
            //云图和附近错误码
            case 2000:
                return AMapException.AMAP_SERVICE_TABLEID_NOT_EXIST;
            case 2001:
                return AMapException.AMAP_ID_NOT_EXIST;
            case 2002:
                return AMapException.AMAP_SERVICE_MAINTENANCE;
            case 2003:
                return AMapException.AMAP_ENGINE_TABLEID_NOT_EXIST;
            case 2100:
                return AMapException.AMAP_NEARBY_INVALID_USERID;
            case 2101:
                return AMapException.AMAP_NEARBY_KEY_NOT_BIND;
            case 2200:
                return AMapException.AMAP_CLIENT_UPLOADAUTO_STARTED_ERROR;
            case 2201:
                return AMapException.AMAP_CLIENT_USERID_ILLEGAL;
            case 2202:
                return AMapException.AMAP_CLIENT_NEARBY_NULL_RESULT;
            case 2203:
                return AMapException.AMAP_CLIENT_UPLOAD_TOO_FREQUENT;
            case 2204:
                return AMapException.AMAP_CLIENT_UPLOAD_LOCATION_ERROR;
            //路径规划
            case 3000:
                return AMapException.AMAP_ROUTE_OUT_OF_SERVICE;
            case 3001:
                return AMapException.AMAP_ROUTE_NO_ROADS_NEARBY;
            case 3002:
                return AMapException.AMAP_ROUTE_FAIL;
            case 3003:
                return AMapException.AMAP_OVER_DIRECTION_RANGE;
            //短传分享
            case 4000:
                return AMapException.AMAP_SHARE_LICENSE_IS_EXPIRED;
            case 4001:
                return AMapException.AMAP_SHARE_FAILURE;
            default:
                return "查询失败: " + rCode;
        }
    }

}
