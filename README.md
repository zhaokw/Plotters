# Repository: Plotters
<p><i>By Kevin Zhao</i></p>
My own easy-to-use plotters based on <a href="https://github.com/jzy3d">JZY3D</a>. Requires <a href = "https://howtodoinjava.com/maven/how-to-install-maven-on-windows/">Mave</a> to run. 
<br/><br/>By using my specialized plotters, you don't need to set up the environment for JZY3D. You can just create a Plotter, add your components, and invoke the <i>plot()</i> function.

# A Demo for Parameterized Surfaces, Scatterpoints, and Curves:
This demo produces a surface plot with 6 parameterized surfaces and a scatterplot with two distinctly colored clusters.
It also includes a rudimentary parameterized curve. I will enhance the curve plotting in the future.
```java
// Create a plotter and add a surface
Plotter plt = new Plotter(Quality.Nicest).setSize(5).plot();
plt.addSurface(plt.createSurface(new Range(-10f,10f), new Range(-10f,10f), new Mapper() {
	public double f(double x, double y) {return -Math.pow(x * x + y * y, 1.2);}
}));

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
// TODO: Simplify plottings for parameterized curves
int n = 100000; Coord3d[] curve = new Coord3d[n];
for (int i = 0; i < n; i++) {
	double t = ((double)i) / n;
	curve[i] = new Coord3d(10*Math.sin(50*t), 10*Math.cos(50*t), -200-100*t);
}
plt.addScatter(curve, 1, new Color(.5f,.5f,.5f));
```
<br/>
<img src="https://github.com/zhaokw/Plotters/blob/master/img/geoplot.png">

# A Demo for Bar Chart
```java
Plotter barplot = new Plotter().plot();
// Demo for bar plot
for (int i = 1 ; i <= 3; i ++) {
	barplot.addBar(new Coord3d(2+2*i, 2+2*i,-150*i), 150*i, .5f, new Color(.1f, .8f, .3f));
	barplot.addBar(new Coord3d(2-2*i, 2-2*i,-150*i), 150*i, .5f, new Color(.1f, .8f, .3f));
}
```
<br/>
<img src="https://github.com/zhaokw/Plotters/blob/master/img/barplot.png">
