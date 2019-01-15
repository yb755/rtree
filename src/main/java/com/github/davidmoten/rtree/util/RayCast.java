package com.github.davidmoten.rtree.util;

import java.util.ArrayList;
import java.util.List;

class Point2d {
	public double x;
	public double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Point2d(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
}

public class RayCast {

	/** 
		* 判断一个点是否在一个多边形里面 * 
		* @author cgx 
		*/
	public static void main(String[] args) {
		Point2d p = new Point2d(1.0, 2.0);
		List<Point2d> list = new ArrayList<Point2d>();
		list.add(new Point2d(1.0, 1.0));
		list.add(new Point2d(1.0, 2.0));
		list.add(new Point2d(2.0, 3.0));
		list.add(new Point2d(3.0, 5.0));
		list.add(new Point2d(3.0, 2.0));
		// list为多边形边界,p为一个待测点
		String flag = rayCasting(p, list);
		System.out.println(flag);
		System.out.println(linesIntersect(1, 1, 2, 2, 2, 2, 4, 4));
	}

	public static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		return ((relativeCCW(x1, y1, x2, y2, x3, y3) * relativeCCW(x1, y1, x2, y2, x4, y4) <= 0) && (relativeCCW(x3, y3, x4, y4, x1, y1) * relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
	}

	public static int relativeCCW(double x1, double y1, double x2, double y2, double px, double py) {
		x2 -= x1;
		y2 -= y1;
		px -= x1;
		py -= y1;
		double ccw = px * y2 - py * x2;
		if (ccw == 0.0) {
			ccw = px * x2 + py * y2;
			if (ccw > 0.0) {
				px -= x2;
				py -= y2;
				ccw = px * x2 + py * y2;
				if (ccw < 0.0) {
					ccw = 0.0;
				}
			}
		}
		return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
	}

	/*
	 * p为待测点  list为多边形边界
	 */
	private static String rayCasting(Point2d p, List<Point2d> list) {
		double px = p.x, py = p.y;
		boolean flag = false;
		//
		for (int i = 0, l = list.size(), j = l - 1; i < l; j = i, i++) {
			// 取出边界的相邻两个点
			double sx = list.get(i).x, sy = list.get(i).y, tx = list.get(j).x, ty = list.get(j).y;
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
