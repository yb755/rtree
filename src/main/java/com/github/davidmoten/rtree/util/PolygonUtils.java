package com.github.davidmoten.rtree.util;

import java.util.List;

public class PolygonUtils {

	public static boolean pointInPolygon(double px, double py, List<Double> pxList, List<Double> pyList) {
		String result = rayCasting(px, py, pxList, pyList);
		System.out.println(result);
		return ("in".equals(result) || "on".equals(result)) ? true : false;
	}

	private static String rayCasting(double px, double py, List<Double> pxList, List<Double> pyList) {
		boolean flag = false;
		//
		for (int i = 0, l = pxList.size(), j = l - 1; i < l; j = i, i++) {
			// 取出边界的相邻两个点
			double sx = pxList.get(i), sy = pyList.get(i), tx = pxList.get(j), ty = pyList.get(j);
			// 点与多边形顶点重合
			if ((sx == px && sy == py) || (tx == px && ty == py)) {
				return "on";
			}
			// 判断线段两端点是否在射线两侧
			// 思路:作p点平行于y轴的射线 作s,t的平行线直线 如果射线穿过线段，则py的值在sy和ty之间
			if ((sy < py && ty >= py) || (sy >= py && ty < py)) {
				// 线段上与射线 Y 坐标相同的点的 X 坐标 ,即求射线与线段的交点
				double x = sx + (py - sy) * (tx - sx) / (ty - sy);
				// 点在多边形的边上
				if (x == px) {
					return "on";
				}
				// 射线穿过多边形的边界
				if (x > px) {
					flag = !flag;
				}
			}
		}
		// 射线穿过多边形边界的次数为奇数时点在多边形内
		return flag ? "in" : "out";
	}
}
