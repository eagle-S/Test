package com.skyworthauto.navi.util;

import android.text.Html;
import android.text.Spanned;
import android.widget.EditText;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.enums.IconType;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapUtils {

    public static final int SECOND_ONE_MINUTE = 60;
    public static final int SECOND_ONE_HOUR = SECOND_ONE_MINUTE * 60;
    public static final int SECOND_ONE_DAY = SECOND_ONE_HOUR * 24;

    /**
     * 判断edittext是否null
     */
    public static String checkEditText(EditText editText) {
        if (editText != null && editText.getText() != null && !(editText.getText().toString().trim()
                .equals(""))) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

    public static Spanned stringToSpan(String src) {
        return src == null ? null : Html.fromHtml(src.replace("\n", "<br />"));
    }

    public static String colorFont(String src, String color) {
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("<font color=").append(color).append(">").append(src).append("</font>");
        return strBuf.toString();
    }

    public static String makeHtmlNewLine() {
        return "<br />";
    }

    public static String makeHtmlSpace(int number) {
        final String space = "&nbsp;";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < number; i++) {
            result.append(space);
        }
        return result.toString();
    }

    public static String getFriendlyDistance(int lenMeter) {
        if (lenMeter > 10000) {
            int dis = lenMeter / 1000;
            return dis + ChString.Kilometer;
        }

        if (lenMeter > 1000) {
            float dis = (float) lenMeter / 1000;
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dstr = fnum.format(dis);
            return dstr + ChString.Kilometer;
        }

        if (lenMeter > 100) {
            int dis = lenMeter / 50 * 50;
            return dis + ChString.Meter;
        }

        int dis = lenMeter / 10 * 10;
        if (dis == 0) {
            dis = 10;
        }

        return dis + ChString.Meter;
    }

    public static String getFriendlyTime(int second) {
        if (second > SECOND_ONE_DAY) {
            int day = second / SECOND_ONE_DAY;
            int hour = (second % SECOND_ONE_DAY) / SECOND_ONE_HOUR;
            return day + "天" + hour + "小时";
        }
        if (second > SECOND_ONE_HOUR) {
            int hour = second / SECOND_ONE_HOUR;
            int minute = (second % SECOND_ONE_HOUR) / SECOND_ONE_MINUTE;
            return hour + "小时" + minute + "分钟";
        }
        if (second >= SECOND_ONE_MINUTE) {
            int minute = second / SECOND_ONE_MINUTE;
            return minute + "分钟";
        }
        return second + "秒";
    }

    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**
     * 把集合体的LatLonPoint转化为集合体的LatLng
     */
    public static ArrayList<LatLng> convertArrList(List<LatLonPoint> shapes) {
        ArrayList<LatLng> lineShapes = new ArrayList<LatLng>();
        for (LatLonPoint point : shapes) {
            LatLng latLngTemp = MapUtils.convertToLatLng(point);
            lineShapes.add(latLngTemp);
        }
        return lineShapes;
    }

    /**
     * long类型时间格式化
     */
    public static String convertToTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return df.format(date);
    }

    public static final String HtmlBlack = "#000000";
    public static final String HtmlGray = "#808080";

    //    public static String getFriendlyTime(int second) {
    //        if (second > 3600) {
    //            int hour = second / 3600;
    //            int miniate = (second % 3600) / 60;
    //            return hour + "小时" + miniate + "分钟";
    //        }
    //        if (second >= 60) {
    //            int miniate = second / 60;
    //            return miniate + "分钟";
    //        }
    //        return second + "秒";
    //    }

    //路径规划方向指示和图片对应
    //    		public static int getDriveActionID(String actionName) {
    //    			if (actionName == null || actionName.equals("")) {
    //    				return R.drawable.dir3;
    //    			}
    //    			if ("左转".equals(actionName)) {
    //    				return R.drawable.dir2;
    //    			}
    //    			if ("右转".equals(actionName)) {
    //    				return R.drawable.dir1;
    //    			}
    //    			if ("向左前方行驶".equals(actionName) || "靠左".equals(actionName)) {
    //    				return R.drawable.dir6;
    //    			}
    //    			if ("向右前方行驶".equals(actionName) || "靠右".equals(actionName)) {
    //    				return R.drawable.dir5;
    //    			}
    //    			if ("向左后方行驶".equals(actionName) || "左转调头".equals(actionName)) {
    //    				return R.drawable.dir7;
    //    			}
    //    			if ("向右后方行驶".equals(actionName)) {
    //    				return R.drawable.dir8;
    //    			}
    //    			if ("直行".equals(actionName)) {
    //    				return R.drawable.dir3;
    //    			}
    //    			if ("减速行驶".equals(actionName)) {
    //    				return R.drawable.dir4;
    //    			}
    //    			return R.drawable.dir3;
    //    		}
    //
    //		public static int getWalkActionID(String actionName) {
    //			if (actionName == null || actionName.equals("")) {
    //				return R.drawable.dir13;
    //			}
    //			if ("左转".equals(actionName)) {
    //				return R.drawable.dir2;
    //			}
    //			if ("右转".equals(actionName)) {
    //				return R.drawable.dir1;
    //			}
    //			if ("向左前方".equals(actionName) || "靠左".equals(actionName) || actionName.contains
    // ("向左前方")) {
    //				return R.drawable.dir6;
    //			}
    //			if ("向右前方".equals(actionName) || "靠右".equals(actionName) || actionName.contains
    // ("向右前方")) {
    //				return R.drawable.dir5;
    //			}
    //			if ("向左后方".equals(actionName)|| actionName.contains("向左后方")) {
    //				return R.drawable.dir7;
    //			}
    //			if ("向右后方".equals(actionName)|| actionName.contains("向右后方")) {
    //				return R.drawable.dir8;
    //			}
    //			if ("直行".equals(actionName)) {
    //				return R.drawable.dir3;
    //			}
    //			if ("通过人行横道".equals(actionName)) {
    //				return R.drawable.dir9;
    //			}
    //			if ("通过过街天桥".equals(actionName)) {
    //				return R.drawable.dir11;
    //			}
    //			if ("通过地下通道".equals(actionName)) {
    //				return R.drawable.dir10;
    //			}
    //
    //			return R.drawable.dir13;
    //		}


    private static DecimalFormat fnum = new DecimalFormat("##0.0");
    public static final int AVOID_CONGESTION = 4;  // 躲避拥堵
    public static final int AVOID_COST = 5;  // 避免收费
    public static final int AVOID_HIGHSPEED = 6; //不走高速
    public static final int PRIORITY_HIGHSPEED = 7; //高速优先

    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    public static final int ACTIVITY_RESULT_CODE = 2;

    public static final String INTENT_NAME_AVOID_CONGESTION = "AVOID_CONGESTION";
    public static final String INTENT_NAME_AVOID_COST = "AVOID_COST";
    public static final String INTENT_NAME_AVOID_HIGHSPEED = "AVOID_HIGHSPEED";
    public static final String INTENT_NAME_PRIORITY_HIGHSPEED = "PRIORITY_HIGHSPEED";


    public static Spanned getRouteOverView(AMapNaviPath path) {
        String routeOverView = "";
        if (path == null) {
            Html.fromHtml(routeOverView);
        }

        int cost = path.getTollCost();
        if (cost > 0) {
            routeOverView += "过路费约<font color=\"red\" >" + cost + "</font>元";
        }
        int trafficLightNumber = getTrafficNumber(path);
        if (trafficLightNumber > 0) {
            routeOverView += "红绿灯" + trafficLightNumber + "个";
        }
        return Html.fromHtml(routeOverView);
    }

    public static int getTrafficNumber(AMapNaviPath path) {
        int trafficLightNumber = 0;
        if (path == null) {
            return trafficLightNumber;
        }
        List<AMapNaviStep> steps = path.getSteps();
        for (AMapNaviStep step : steps) {
            trafficLightNumber += step.getTrafficLightNumber();
        }
        return trafficLightNumber;
    }

    public static JSONObject getJSONObject(LatLonPoint point) {
        JSONObject latlon = new JSONObject();
        try {
            latlon.put(JsonConstants.Key.LATITUDE, point.getLatitude());
            latlon.put(JsonConstants.Key.LONGITUDE, point.getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latlon;
    }

    public static LatLonPoint createPointFromJson(String address) {
        try {
            JSONObject object = new JSONObject(address);
            double latitude = object.getDouble(JsonConstants.Key.LATITUDE);
            double longitude = object.getDouble(JsonConstants.Key.LONGITUDE);
            return new LatLonPoint(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDriveActionID(int iconType) {
        switch (iconType) {
            case IconType.DEFAULT:
                return 0;
            case IconType.LEFT:
                return R.drawable.action_group2;
            case IconType.RIGHT:
                return R.drawable.action_group3;
            case IconType.LEFT_FRONT:
                return R.drawable.action_group4;
            case IconType.RIGHT_FRONT:
                return R.drawable.action_group5;
            case IconType.LEFT_BACK:
                return R.drawable.action_group6;
            case IconType.RIGHT_BACK:
                return R.drawable.action_group7;
            case IconType.LEFT_TURN_AROUND:
                return R.drawable.action_group8;
            case IconType.STRAIGHT:
                return R.drawable.action_group9;
            case IconType.ENTER_ROUNDABOUT:
                return R.drawable.action_group11;
            case IconType.OUT_ROUNDABOUT:
                return R.drawable.action_group12;
            case IconType.ARRIVED_SERVICE_AREA:
                return R.drawable.action_group13;
            case IconType.ARRIVED_TOLLGATE:
                return R.drawable.action_group14;
            case IconType.ARRIVED_DESTINATION:
                return R.drawable.action_group15;
            case IconType.ARRIVED_TUNNEL:
                return R.drawable.action_group16;
            default:
                return 0;
        }
    }
}
