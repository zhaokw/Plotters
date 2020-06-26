import java.awt.BorderLayout;

import java.util.*;
import java.util.function.Function;

import javax.swing.*;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.maths.parameq.ParametricEquation;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.primitives.parameq.ParametricDrawable;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import org.knowm.xchart.*;

class Cluster {
	public Coord3d[] pts;
	public int size = 1;
	public Color color = Color.random();
	
	public Cluster(Coord3d[] pts, int size, Color color) {
		this.pts = pts;
		this.size = size;
		this.color = color;
	}

	public Cluster(Coord3d[] pts, int size) {
		this.pts = pts;
		this.size = size;
	}

	public Cluster(Coord3d[] pts, Color color) {
		this.pts = pts;
		this.color = color;
	}

	public Cluster(Coord3d[] pts) {
		this.pts = pts;
	}
	
	public Cluster() {}
} 

public class Plotter extends AbstractAnalysis {
	public int defaultSize = 5; // Default Size for Scatterplot
	public final Quality quality; // Plotting Quality
	private XYChart xychart = null;
	
	public Plotter() {
		quality = Quality.Advanced;
		chart = AWTChartComponentFactory.chart(quality);
	}
	
	public Plotter(Quality _quality) {
		quality = _quality;
		chart = AWTChartComponentFactory.chart(quality);	
	}
	
	@Override public void init() {}
	
	public Plotter plot() throws Exception {
		if (xychart != null) {
			// Schedule a job for the event-dispatching thread:
			// creating and showing this application's GUI.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {

			    // Create and set up the window.
			    JFrame frame = new JFrame("");
			    frame.setLayout(new BorderLayout());
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			    // chart
			    JPanel chartPanel = new XChartPanel<XYChart>(xychart);
			    frame.add(chartPanel, BorderLayout.CENTER);

			    // Display the window.
			    frame.pack();
			    frame.setVisible(true);
			  }
			});
		}
		if (chart != null && !chart.getScene().getGraph().getAll().isEmpty())
			AnalysisLauncher.open(this); 
		return this;
	}
	
	public Plotter setSize(int _size) {
		this.defaultSize = _size;
		return this;
	}
	
	public Shape createSurface(Range xRange, Range yRange, Mapper mapper) {
		int maxPixel;
		if (quality.equals(Quality.Nicest))
			maxPixel = 3000;
		else if (quality.equals(Quality.Advanced))
			maxPixel = 1000;
		else if (quality.equals(Quality.Intermediate))
			maxPixel = 500;
		else if (quality.equals(Quality.Fastest))
			maxPixel = 100;
		else // Default
			maxPixel = 1000;
		int xPixel = (int)Math.min(10*xRange.getRange(), maxPixel), 
			yPixel = (int)Math.min(10*yRange.getRange(), maxPixel);
		return Builder.buildOrthonormal(new OrthonormalGrid(xRange,xPixel,yRange,yPixel), mapper);
	}
	
	public Shape createRingSurface(Range xRange, Range yRange, Mapper mapper, float minR, float maxR) {
		int maxPixel;
		if (quality.equals(Quality.Nicest))
			maxPixel = 3000;
		else if (quality.equals(Quality.Advanced))
			maxPixel = 1000;
		else if (quality.equals(Quality.Intermediate))
			maxPixel = 500;
		else if (quality.equals(Quality.Fastest))
			maxPixel = 100;
		else // Default
			maxPixel = 1000;
		int xPixel = (int)Math.min(10*xRange.getRange(), maxPixel), 
			yPixel = (int)Math.min(10*yRange.getRange(), maxPixel);
		return Builder.buildRing(new OrthonormalGrid(xRange,xPixel,yRange,yPixel), mapper, minR, maxR);
	}
	
	public Plotter addSurface(Shape sf) {
		setUpSurfaceDisp(sf);
		return this;
	}
	
	public Plotter addSurfaces(Collection<Shape> sfs) {
		for (Shape sf : sfs)
			addSurface(sf);
		return this;
	}
	
	private void setUpSurfaceDisp(Shape sf) {
		sf.setColorMapper(new ColorMapper(
			new ColorMapRainbow(), 
			sf.getBounds().getZmin(), sf.getBounds().getZmax(), 
			new Color(1, 1, 1, .5f)
		));
		sf.setFaceDisplayed(true); sf.setWireframeDisplayed(false);
		chart.getScene().getGraph().add(sf);
	}
	
	public Plotter addBar(Coord3d center, float h, float r, Color color) {
		color = color == null ? Color.random() : color;
		HistogramBar b = new HistogramBar();
		b.setData(center, h, r, color); b.setWireframeDisplayed(false);
		chart.getScene().add(b);
		return this;
	}
	
	
	public Plotter addScatter(Cluster cluster) {
		chart.getScene().add(new Scatter(cluster.pts, cluster.color, cluster.size));
		return this;
	}
	
	public Plotter addScatter(Collection<Cluster> clusters) {
		for (Cluster cluster : clusters)
			chart.getScene().add(new Scatter(cluster.pts, cluster.color, cluster.size));
		return this;
	}
	
	
	public Plotter addScatter(Coord3d[] pts) {
		float rand = (float) Math.random(); 
		chart.getScene().add(new Scatter(pts, new Color(rand, 1-rand ,1-rand, 1.5f),defaultSize));
		return this;
	}
	
	public Plotter addScatter(Coord3d[] pts, int size) {
		float rand = (float) Math.random(); 
		chart.getScene().add(new Scatter(pts, new Color(rand, 1-rand ,1-rand, 1.5f), size));
		return this;
	}
	
	public Plotter addScatter(Coord3d[] pts, int size, Color color) {
		chart.getScene().add(new Scatter(pts, color, size));
		return this;
	}
	
	public Plotter addScatter(Coord3d[] pts, Color color) {
		chart.getScene().add(new Scatter(pts, color, defaultSize));
		return this;
	}
	
	public Plotter setXLabel(String xlabel) {
		chart.getAxeLayout().setXAxeLabel(xlabel);
		return this;
	}
	public Plotter setYLabel(String ylabel) {
		chart.getAxeLayout().setYAxeLabel(ylabel);
		return this;
	}
	public Plotter setZLabel(String zlabel) {
		chart.getAxeLayout().setZAxeLabel(zlabel);
		return this;
	}
	
	// TODO: function information about a curve parameterized by arc length
	class ParamByArcLen {
		public Function<Double, Coord3d> oldf, lenf;
		public double tMin, tMax, sMin = 0, sMax = 0;
		public Color color;
		public int stps;
		public ParamByArcLen(Function<Double, Coord3d> oldf, double tMin, double tMax, Color color) {
			this.oldf = oldf; this.tMin = tMin; this.tMax = tMax; this.color = color;
			// TODO: compute lenf, sMin, sMax
			stps = (int) ((sMax - sMin) * 50);
		}
		public void drawCurve(double s) {
			ParametricEquation curve = new ParametricEquation() {
				public Coord3d apply(double t) {return lenf.apply(t);}
			};
			chart.getScene().getGraph().add(new ParametricDrawable(curve, tMin, tMax, stps, color));
		}
	}
	
	public Plotter addScatterplot(double[] pts, Color color) {
		for (int i = 0; i < pts.length - 2; i++) {
			final int iCpy = i;
			Function<Double, Coord3d> f = (t) -> {
				float x, y, x1 = iCpy, y1 = (float) pts[iCpy], x2 = iCpy + 1, y2 = (float) pts[iCpy + 1];
				float arcl = (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
				x = (float) ((x2-x1) * t); y = (float) ((y2-y1) * t);
				return new Coord3d(x1+x/arcl, y1+y/arcl, 0);
			};
			addCurve(f, 0, 1, 2000, color);
		}
		return this;
	}
	
	public Plotter addLineplot() {
		chart.getScene().getGraph().add(new LineStrip(Arrays.asList(new Coord3d(1,2,3), new Coord3d(4,5,6))));
		return this;
	}
	
	public Plotter addCurve(Function<Double, Coord3d> f, double tMin, double tMax, int stps, Color color) {
		ParametricEquation curve = new ParametricEquation() {
			public Coord3d apply(double t) {return f.apply(t);}
		};
		chart.getScene().getGraph().add(new ParametricDrawable(curve, tMin, tMax, stps, color));
		return this;
	}
	
	public Plotter setUp2DPlot(int width, int height) {
		xychart = new XYChartBuilder().width(width).height(height).xAxisTitle("x").yAxisTitle("y").build();
		return this;
	}
	
	public Plotter add2DLineplot(double[] xs, double[] ys, String legend) {
		xychart.addSeries(legend == null || legend.isEmpty() ? "line" : legend, xs, ys);
		return this;
	}
	
	public Plotter xyChartDemo() {
		// Create Chart
		xychart = new XYChartBuilder().width(600).height(400).xAxisTitle("X").yAxisTitle("Y").build();

		// Series
	
		xychart.addSeries("b", new double[] { 0, 2, 4, 6, 9 }, new double[] { -1, 6, 4, 0, 4 });
		xychart.addSeries("c", new double[] { 0, 1, 3, 8, 9 }, new double[] { -2, -1, 1, 0, 1 });

		return this;
	}
	
	public Plotter set2DChartTitle(String title) {
		xychart.setTitle(title);
		return this;
	}
	public Plotter set2DChartXLabel(String xLabel) {
		xychart.setXAxisTitle(xLabel);
		return this;
	}
	public Plotter set2DChartYLabel(String yLabel) {
		xychart.setYAxisTitle(yLabel);
		return this;
	}
}
