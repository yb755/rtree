package com.github.davidmoten.rtree.geometry;

import java.util.ArrayList;
import java.util.List;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.internal.CircleDouble;
import com.github.davidmoten.rtree.geometry.internal.PointDouble;
import com.github.davidmoten.rtree.geometry.internal.PolygonDouble;
import com.github.davidmoten.rtree.geometry.internal.RectangleDouble;

public class PolygonTest {

	public static void main(String[] args) {
		RTree<String, Geometry> rtree = RTree.create();
		rtree = rtree.add("1", CircleDouble.create(113.54215d, 22.25412d, 1000));
		rtree = rtree.add("2", CircleDouble.create(113.54215d, 22.25412d, 2000));
		rtree = rtree.add("3", RectangleDouble.create(113.54215d, 22.25412d, 114.54215d, 23.25412d));
		rtree = rtree.add("5", PointDouble.create(113.54215d, 22.25412d));
		rtree = rtree.add("4", RectangleDouble.create(113.64215d, 22.26412d, 114.53215d, 23.24412d));
		List<Double> xList = new ArrayList<Double>();
		xList.add(122.154265);
		xList.add(122.154465);
		xList.add(123.154265);
		List<Double> yList = new ArrayList<Double>();
		yList.add(22.154265);
		yList.add(22.154465);
		yList.add(23.154265);
		rtree = rtree.add("6", PolygonDouble.create(xList, yList));
		System.out.println(rtree.asString());
		Iterable<Entry<String, Geometry>> it = rtree.search(Geometries.point(122.155265, 22.754266)).toBlocking().toIterable();
		for (Entry<String, Geometry> entry : it) {
			System.out.println("id=" + entry.value());
		}
	}
}
