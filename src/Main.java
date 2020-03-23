import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.rendering.canvas.Quality;


public class Main {
	
	public static void main(String[] args) throws Exception {
		// Plot a surface
		Plotter plt = new Plotter(Quality.Nicest).setSize(5);
		plt.addSurface(plt.createSurface(new Range(-10f,10f), new Range(-10f,10f), new Mapper() {
			public double f(double x, double y) {return -Math.pow(x * x + y * y, 1.2);}
		}));
		plt.plot();
		
		// Add more surfaces
		for (double p = .1; p <= .5; p += .1) {
			final Double pf = new Double(p);
			plt.addSurface(plt.createSurface(new Range(-10f,10f), new Range(-10f,10f), new Mapper() {
				public double f(double x, double y) {return -Math.pow(x * x + y * y, 1.2 - pf);}
			}));
		}

		// Add some scatterplots
		Coord3d[] points1 = new Coord3d[100], points2 = new Coord3d[100];
		for (int i = 0; i < points1.length; i++)
			points1[i] = new Coord3d(
				(i+ 15*Math.random()) / 15, 
				(i + 30*Math.random()) / 15, 
				-Math.random()*500
			);
		for (int i = 0; i < points2.length; i++)
			points2[i] = new Coord3d( 
				(100*Math.sin(i) + 30*Math.random()) / 15, 
				(100*Math.cos(i) + 15*Math.random()) / 15, 
				-100*Math.random()
			);
		plt.addScatter(points1, new Color(1f, .0f, .0f)).addScatter(points2, new Color(.0f, .0f, 1f));
		
		// Add a parameterized curve a(t) = <10sin(50t), 10cos(50t), -200-100t> as a dense scatterplot
		int n = 100000; Coord3d[] curve = new Coord3d[n];
		for (int i = 0; i < n; i++) {
			double t = ((double)i) / n;
			curve[i] = new Coord3d(10*Math.sin(50*t), 10*Math.cos(50*t), -200-100*t);
		}
		plt.addScatter(curve, 1, new Color(.5f,.5f,.5f));
		
		Plotter barplot = new Plotter().plot();
		// Demo for bar plot
		for (int i = 1 ; i <= 3; i ++) {
			barplot.addBar(new Coord3d(2+2*i, 2+2*i,-150*i), 150*i, .5f, new Color(.1f, .8f, .3f));
			barplot.addBar(new Coord3d(2-2*i, 2-2*i,-150*i), 150*i, .5f, new Color(.1f, .8f, .3f));
		}
	}
}
