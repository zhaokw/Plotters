# Repository: Plotters
<p><i>By Kevin Zhao</i></p>
My own easy-to-use plotters based on <a href="https://github.com/jzy3d">JZY3D</a>. Requires <a href = "https://howtodoinjava.com/maven/how-to-install-maven-on-windows/">Mave</a> to run. 
<br/><br/>By using my specialized plotters, you don't need to set up the environment for JZY3D. You can just create a Plotter, add your components, and invoke the <i>plot()</i> function.

# Contents:
1, <b>[Surface Plotter](https://github.com/zhaokw/Plotters/blob/master/src/SurfacePlotter.java)</b><br/>To plot a parametrized surface
<br/><br/>
2, <b>[Scatter Plotter](https://github.com/zhaokw/Plotters/blob/master/src/ScatterPlotter.java)</b><br/>To plot a 3D scatterplot<br/>
<br/><br/>
# A Demo:
This demo produces a surface plot with two parameterized surfaces and a scatterplot with two distinctly colored clusters.
```java
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
```
<br/>
<img class="centered" src="https://github.com/zhaokw/Plotters/blob/master/img/Result.png"/>
