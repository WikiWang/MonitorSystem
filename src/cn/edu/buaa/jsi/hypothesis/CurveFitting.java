package cn.edu.buaa.jsi.hypothesis;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.optimization.fitting.GaussianFitter;
import org.apache.commons.math3.optimization.general.LevenbergMarquardtOptimizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by super.d on 2014/7/26.
 */
public class CurveFitting {
    public static double[] useGaussian(Map<Double, Double> points) {
        GaussianFitter gaussianFitter = new GaussianFitter(new LevenbergMarquardtOptimizer());
        for (Double xDouble : points.keySet()) {
            gaussianFitter.addObservedPoint(xDouble, points.get(xDouble));
        }
        return gaussianFitter.fit();
    }

    public static double[] usePolynomial(Map<Double, Double> points, int levels) {
        PolynomialCurveFitter curveFitter = PolynomialCurveFitter.create(levels);
        List<WeightedObservedPoint> weightedObservedPointList = new LinkedList<WeightedObservedPoint>();
        for (Double xDouble : points.keySet()) {
            WeightedObservedPoint weightedObservedPoint = new WeightedObservedPoint(1.0, xDouble, points.get(xDouble));
            weightedObservedPointList.add(weightedObservedPoint);
        }
        double[] fit = curveFitter.fit(weightedObservedPointList);
        return fit;
    }
}
