import java.util.*;
import java.util.function.Function;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.maths.parameq.ParametricEquation;
import org.jzy3d.maths.parameq.ParametricEquation2;
import org.jzy3d.maths.parameq.ParametricTorus;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.HistogramBar;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.parameq.ParametricDrawable;
import org.jzy3d.plot3d.primitives.parameq.ParametricDrawable2;
import org.jzy3d.plot3d.rendering.canvas.Quality;

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
	
	public Plotter() {
		quality = Quality.Advanced;
		chart = AWTChartComponentFactory.chart(quality);
	}
	
	public Plotter(Quality _quality) {
		quality = _quality;
		chart = AWTChartComponentFactory.chart(quality);	
	}
	
	@Override public void init() {}
	
	// TODO: Parametric curves
	private void paramDemo() {
		final double PI_2 = 2*Math.PI; 
        
		Color color = new Color(0, 64/255f, 84/255f);
        ParametricEquation2 e1 = new ParametricTorus(2, 0.5);
        ParametricEquation curve1 = new ParametricEquation() {
			
			@Override
			public Coord3d apply(double arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
        chart = new Chart(this.quality, getCanvasType());
        
        int tsteps = 200, usteps = 200, tloops = 1, uloops = 1;
        chart.getScene().getGraph().
        	add(new ParametricDrawable2(e1, 0, PI_2*tloops, tsteps, 0, PI_2*uloops, usteps, color));
        chart.getAxeLayout().setMainColor(color);
	}
	
	public Plotter plot() throws Exception {AnalysisLauncher.open(this); return this;}
	
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
	
	public Plotter addCurve(Function<Double, Coord3d> f, double tMin, double tMax, int stps, Color color) {
		ParametricEquation curve = new ParametricEquation() {
			public Coord3d apply(double t) {return f.apply(t);}
		};
		chart.getScene().getGraph().add(new ParametricDrawable(curve, tMin, tMax, stps, color));
		return this;
	}
}
