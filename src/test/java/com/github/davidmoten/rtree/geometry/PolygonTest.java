package com.github.davidmoten.rtree.geometry;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.internal.CircleDouble;
import com.github.davidmoten.rtree.geometry.internal.PointDouble;
import com.github.davidmoten.rtree.geometry.internal.PolygonDouble;
import com.github.davidmoten.rtree.geometry.internal.RectangleDouble;
import com.github.davidmoten.rtree.util.PolygonUtils;
import com.github.davidmoten.rtree.util.PolygonUtils;

public class PolygonTest {

	public static void main(String[] args) {
		RTree<String, Geometry> rtree = RTree.create();
		// 圆形的半径，需要大概的算出它的MBR[0.001约等于157米]
		rtree = rtree.add("1", CircleDouble.create(113.54215d, 22.25412d, 1000 / 100000d));
		rtree = rtree.add("2", CircleDouble.create(113.54215d, 22.25412d, 2000 / 100000d));
		rtree = rtree.add("3", RectangleDouble.create(113.54215d, 22.25412d, 114.54215d, 23.25412d));
		rtree = rtree.add("5", PointDouble.create(113.54215d, 22.25412d));
		rtree = rtree.add("4", RectangleDouble.create(113.64215d, 22.26412d, 114.53215d, 23.24412d));
		List<Double> xList = new ArrayList<Double>();
//		xList.add(1d);
//		xList.add(3d);
//		xList.add(3d);
//		xList.add(1d);
		xList.add(1d);
		xList.add(1d);
		xList.add(3d);
		xList.add(3d);
		List<Double> yList = new ArrayList<Double>();
//		yList.add(1d);
//		yList.add(1d);
//		yList.add(3d);
//		yList.add(1d);
		yList.add(1d);
		yList.add(3d);
		yList.add(3d);
		yList.add(1d);
		rtree = rtree.add("6", PolygonDouble.create(xList, yList));
		System.out.println(rtree.asString());
		final Point point = Geometries.point(2.8, 1);
		Observable<Entry<String, Geometry>> observable = rtree.search(point);
		Iterable<Entry<String, Geometry>> it = observable.filter(new Func1<Entry<String, Geometry>, Boolean>() {

			@Override
			public Boolean call(Entry<String, Geometry> t) {
				if (t.geometry() instanceof PolygonDouble) {
					PolygonDouble polygon = (PolygonDouble) t.geometry();
					System.out.println(point.x() + "," + point.y());
					System.out.println(polygon.getxList() + "---" + polygon.getyList());
					boolean result = PolygonUtils.pointInPolygon(point.x(), point.y(), polygon.getxList(), polygon.getyList());
					System.out.println(result);
					return result;
				} else if (t.geometry() instanceof CircleDouble) {
					System.out.println("circle....");
					CircleDouble circle = (CircleDouble) t.geometry();
					return circle.intersects(point);
				} else {
					return true;
				}
			}

		}).toBlocking().toIterable();
		for (Entry<String, Geometry> entry : it) {
			System.out.println("id=" + entry.value());
		}
	}
}
