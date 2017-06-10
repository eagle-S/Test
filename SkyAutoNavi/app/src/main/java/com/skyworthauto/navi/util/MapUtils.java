package com.skyworthauto.navi.util;

import android.text.Html;
import android.text.Spanned;
import android.widget.EditText;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteRailwayItem;
import com.amap.api.services.route.RouteSearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapUtils {
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

    public static String getFriendlyLength(int lenMeter) {
        if (lenMeter > 10000) // 10 km
        {
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

    public static String getFriendlyTime(int s) {
        String timeDes = "";
        int h = s / 3600;
        if (h > 0) {
            timeDes += h + "小时";
        }
        int min = (int) (s % 3600) / 60;
        if (min > 0) {
            timeDes += min + "分";
        }
        return timeDes;
    }

    public static String getFriendlyDistance(int m) {
        float dis = m / 1000f;
        String disDes = fnum.format(dis) + "公里";
        return disDes;
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

    public static String getBusPathTitle(BusPath busPath) {
        if (busPath == null) {
            return String.valueOf("");
        }
        List<BusStep> busSetps = busPath.getSteps();
        if (busSetps == null) {
            return String.valueOf("");
        }
        StringBuffer sb = new StringBuffer();
        for (BusStep busStep : busSetps) {
            StringBuffer title = new StringBuffer();
            if (busStep.getBusLines().size() > 0) {
                for (RouteBusLineItem busline : busStep.getBusLines()) {
                    if (busline == null) {
                        continue;
                    }

                    String buslineName = getSimpleBusLineName(busline.getBusLineName());
                    title.append(buslineName);
                    title.append(" / ");
                }
                //					RouteBusLineItem busline = busStep.getBusLines().get(0);

                sb.append(title.substring(0, title.length() - 3));
                sb.append(" > ");
            }
            if (busStep.getRailway() != null) {
                RouteRailwayItem railway = busStep.getRailway();
                sb.append(railway.getTrip() + "(" + railway.getDeparturestop().getName() + " - "
                        + railway.getArrivalstop().getName() + ")");
                sb.append(" > ");
            }
        }
        return sb.substring(0, sb.length() - 3);
    }

    public static String getBusPathDes(BusPath busPath) {
        if (busPath == null) {
            return String.valueOf("");
        }
        long second = busPath.getDuration();
        String time = getFriendlyTime((int) second);
        float subDistance = busPath.getDistance();
        String subDis = getFriendlyLength((int) subDistance);
        float walkDistance = busPath.getWalkDistance();
        String walkDis = getFriendlyLength((int) walkDistance);
        return String.valueOf(time + " | " + subDis + " | 步行" + walkDis);
    }

    public static String getSimpleBusLineName(String busLineName) {
        if (busLineName == null) {
            return String.valueOf("");
        }
        return busLineName.replaceAll("\\(.*?\\)", "");
    }


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


    //    /**
    //     * 计算path对应的标签
    //     *
    //     * @param paths        多路径规划回调的所有路径
    //     * @param ints         多路径线路ID
    //     * @param pathIndex    当前路径索引
    //     * @param strategyBean 封装策略bean
    //     * @return path对应标签描述
    //     */
    //    public static String getStrategyDes(HashMap<Integer, AMapNaviPath> paths, int[] ints,
    // int pathIndex, StrategyBean strategyBean) {
    //        int StrategyTAGIndex = pathIndex + 1;
    //        String StrategyTAG = "方案" + StrategyTAGIndex;
    //
    //        int minTime = Integer.MAX_VALUE;
    //        int minDistance = Integer.MAX_VALUE;
    //        int minTrafficLightNumber = Integer.MAX_VALUE;
    //        int minCost = Integer.MAX_VALUE;
    //        for (int i = 0; i < ints.length; i++) {
    //            if (pathIndex == i) {
    //                continue;
    //            }
    //            AMapNaviPath path = paths.get(ints[i]);
    //            if (path == null) {
    //                continue;
    //            }
    //            int trafficListNumber = getTrafficNumber(path);
    //            if (trafficListNumber < minTrafficLightNumber) {
    //                minTrafficLightNumber = trafficListNumber;
    //            }
    //
    //            if (path.getTollCost() < minCost) {
    //                minCost = path.getTollCost();
    //            }
    //
    //            if (path.getAllTime() < minTime) {
    //                minTime = path.getAllTime();
    //            }
    //            if (path.getAllLength() < minDistance) {
    //                minDistance = path.getAllLength();
    //            }
    //        }
    //        AMapNaviPath indexPath = paths.get(ints[pathIndex]);
    //        int indexTrafficLightNumber = getTrafficNumber(indexPath);
    //        int indexCost = indexPath.getTollCost();
    //        int indexTime = indexPath.getAllTime();
    //        int indexDistance = indexPath.getAllLength();
    //        if (indexTrafficLightNumber < minTrafficLightNumber) {
    //            StrategyTAG = "红绿灯少";
    //        }
    //        if (indexCost < minCost) {
    //            StrategyTAG = "收费较少";
    //        }
    //
    //        if (Math.round(indexDistance / 100f) < Math.round(minDistance / 100f)) {   //
    // 展示距离精确到千米保留一位小数，比较时按照展示数据处理
    //            StrategyTAG = "距离最短";
    //        }
    //        if (indexTime / 60 < minTime / 60) {    //展示时间精确到分钟，比较时按照展示数据处理
    //            StrategyTAG = "时间最短";
    //        }
    //        boolean isMulti = isMultiStrategy(strategyBean);
    //        if (strategyBean.isCongestion() && pathIndex == 0 && !isMulti) {
    //            StrategyTAG = "躲避拥堵";
    //        }
    //        if (strategyBean.isAvoidhightspeed() && pathIndex == 0 && !isMulti) {
    //            StrategyTAG = "不走高速";
    //        }
    //        if (strategyBean.isCost() && pathIndex == 0 && !isMulti) {
    //            StrategyTAG = "避免收费";
    //        }
    //        if (pathIndex == 0 && StrategyTAG.startsWith("方案")) {
    //            StrategyTAG = "推荐";
    //        }
    //        return StrategyTAG;
    //    }


    //    public static boolean isMultiStrategy(StrategyBean strategyBean) {
    //        boolean isMultiStrategy = false;
    //        if (strategyBean.isCongestion()) {
    //            if (strategyBean.isAvoidhightspeed() || strategyBean.isCost() || strategyBean
    // .isHightspeed()) {
    //                isMultiStrategy = true;
    //            } else {
    //                isMultiStrategy = false;
    //            }
    //        }
    //        if (strategyBean.isCost()) {
    //            if (strategyBean.isCongestion() || strategyBean.isAvoidhightspeed()) {
    //                isMultiStrategy = true;
    //            } else {
    //                isMultiStrategy = false;
    //            }
    //        }
    //        if (strategyBean.isAvoidhightspeed()) {
    //            if (strategyBean.isCongestion() || strategyBean.isCost()) {
    //                isMultiStrategy = true;
    //            } else {
    //                isMultiStrategy = false;
    //            }
    //        }
    //        if (strategyBean.isHightspeed()) {
    //            if (strategyBean.isCongestion()) {
    //                isMultiStrategy = true;
    //            } else {
    //                isMultiStrategy = false;
    //            }
    //        }
    //        return isMultiStrategy;
    //    }

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
}
