import java.util.LinkedList;
import java.util.List;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class ScatterPlotter extends AbstractAnalysis {
	public List<Coord3d[]> clusters = new LinkedList<>();
	public int size = 5;
	public Quality quality = Quality.Advanced;

	public ScatterPlotter() {
		
	}
	
	public ScatterPlotter(Coord3d[] points) {
		super();
		clusters.add(points);
	}
	
	public ScatterPlotter(List<Coord3d[]> clusters) {
		super();
		this.clusters = clusters;
	}


	public void plot() throws Exception {
		AnalysisLauncher.open(new ScatterPlotter(clusters));
	}
	
	public void addPoints(Coord3d[] points) {
		clusters.add(points);
	}

	@Override
	public void init() {
		chart = AWTChartComponentFactory.chart(quality, "newt");
		int i = 1;
		for (Coord3d[] points: clusters) { 
			float p = i / clusters.size();
			chart.getScene().add(new Scatter(points, new Color(p,.5f-p/2 ,1-p, 1.5f),size));
			i ++;
		}
	}
}