package com.github.davidmoten.rtree.util;

import java.util.List;

public class CollectionsUtil {

	public static double max(List<Double> list) {
		double max = Double.MIN_VALUE;
		for (Double value : list) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public static double min(List<Double> list) {
		double min = Double.MAX_VALUE;
		for (Double value : list) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

	public static boolean isEmpty(List<Double> list) {
		return list == null || list.isEmpty();
	}
}
