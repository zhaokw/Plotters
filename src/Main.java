import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;

public class Main {
	public static void main(String[] args) throws Exception {
		SurfacePlotter surfacePlotter = new SurfacePlotter();
		OrthonormalGrid grid = new OrthonormalGrid(new Range(-10f, 10f), 100, new Range(-10f, 10f), 100);
		Shape surface1 = Builder.buildOrthonormal(grid,
			new Mapper() {
				public double f(double x, double y) {
					return -Math.pow(x * x + y * y, 1.2);
				}
			}
		);
		surfacePlotter.addSurface(surface1);
		Shape surface2 = Builder.buildOrthonormal(grid,
			new Mapper() {
				public double f(double x, double y) {
					return x < -3 && y < -3 ? 0 : -Math.pow(x * x + y * y, 1.1);
				}
			}
		);
		surfacePlotter.addSurface(surface2);
		surfacePlotter.plot();

		ScatterPlotter scp = new ScatterPlotter();
		Coord3d[] points1 = new Coord3d[100], points2 = new Coord3d[100];
		for (int i = 0; i < points1.length; i++)
			points1[i] = new Coord3d(i + Math.random()*15, i + Math.random()*30, Math.random()*5);
		scp.addPoints(points1);
		for (int i = 0; i < points2.length; i++)
			points2[i] = new Coord3d(100*Math.sin(i) + Math.random()*30, 100*Math.cos(i) + Math.random()*15, Math.random()*30);
		scp.addPoints(points2);
		scp.plot();
	}
}
