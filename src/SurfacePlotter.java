
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class SurfacePlotter extends AbstractAnalysis {
	private List<Shape> shapes = new LinkedList<>();
	public Quality quality = Quality.Advanced;
	 
	public SurfacePlotter() {
	}

	public SurfacePlotter(Shape surface) {
		super();
		this.shapes = new LinkedList<Shape>(Arrays.asList(surface));
	}

	public SurfacePlotter(List<Shape> shapes) {
		super();
		this.shapes = shapes;
	}
	
	public void plot() throws Exception {
		AnalysisLauncher.open(new SurfacePlotter(shapes));
	}


	@Override
	public void init() throws Exception {
		addSurfaces();
	}
	
	public void addSurface(Shape surface) {
		shapes.add(surface);
	}
	
	private void addSurfaces() throws Exception {
		chart = AWTChartComponentFactory.chart(quality, getCanvasType());		
		for (Shape shape : shapes) 
				setUpSurfaceDisp(shape);	
	}
	
	private void setUpSurfaceDisp(Shape surface) {
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
				surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(false);
		chart.getScene().getGraph().add(surface);
	}
}
