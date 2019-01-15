package com.github.davidmoten.rtree.geometry.internal;

import java.util.List;

import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.github.davidmoten.rtree.util.CollectionsUtil;
import com.github.davidmoten.rtree.util.PolygonUtils;

/**
 * 多边形[自己画的围栏，边数在100以内]
 * @author Administrator
 *
 */
public final class PolygonDouble implements Geometry {

	// MBR的对角点
	private double x1, y1, x2, y2;

	private final List<Double> xList;
	private final List<Double> yList;
	private final Rectangle mbr;

	private PolygonDouble() {
		throw new IllegalArgumentException();
	}

	private PolygonDouble(List<Double> xList, List<Double> yList) {
		if (CollectionsUtil.isEmpty(xList)) {
			throw new IllegalArgumentException("xList参数不能为空");
		}
		if (CollectionsUtil.isEmpty(yList)) {
			throw new IllegalArgumentException("yList参数不能为空");
		}
		if (xList.size() != yList.size()) {
			throw new IllegalArgumentException("两个参数集合的数量不一致");
		}
		this.xList = xList;
		this.yList = yList;
		this.x1 = CollectionsUtil.min(xList);
		this.y1 = CollectionsUtil.min(yList);
		this.x2 = CollectionsUtil.max(xList);
		this.y2 = CollectionsUtil.max(yList);
		this.mbr = RectangleDouble.create(x1, y1, x2, y2);

	}

	public static PolygonDouble create(List<Double> xList, List<Double> yList) {
		return new PolygonDouble(xList, yList);
	}

	@Override
	public double distance(Rectangle r) {
		double x1 = this.mbr.x1();
		double y1 = this.mbr.y1();
		double x2 = this.mbr.x2();
		double y2 = this.mbr.y2();
		if (intersects(r)) {
			return 0d;
		} else {
			// 计算两个mbr区域的最小距离
			return 1d;
		}
	}

	@Override
	public Rectangle mbr() {
		return mbr;
	}

	@Override
	public boolean intersects(Rectangle r) {
		return GeometryUtil.intersects(x1, y1, x2, y2, r.x1(), r.y1(), r.x2(), r.y2());
	}

	@Override
	public boolean isDoublePrecision() {
		return true;
	}

	public boolean intersects(Point point) {
		return contains(point.x(), point.y());
	}

	private boolean contains(double px, double py) {
		return PolygonUtils.isPointInPolygon(px, py, xList, yList);
	}
}
