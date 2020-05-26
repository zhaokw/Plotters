/*
 * Copyright (c) 2010-2011, Martin Pernollet
 * All rights reserved. 
 *
 * Redistribution in binary form, with or without modification, is permitted.
 * Edition of source files is allowed.
 * Redistribution of original or modified source files is FORBIDDEN.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jzy3d.demos.parametric;

import java.io.IOException;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.parameq.ParametricAbcd;
import org.jzy3d.maths.parameq.ParametricEquation;
import org.jzy3d.plot3d.primitives.parameq.ParametricDrawable;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

public class ParamEq2dDemo extends AbstractAnalysis {
	public static void main(String[] args) throws Exception {
		AnalysisLauncher.open(new ParamEq2dDemo());
	}

	@Override
	public void init() throws IOException {
		Color color = new Color(0, 64 / 255f, 84 / 255f);
		int j = 3;
		int k = 3;
		ParametricEquation[] equations = new ParametricEquation[] { 
			new ParametricAbcd(80, 1, 1, 80, j, k), new ParametricAbcd(80, 1, 80, 1, j, k), 
			new ParametricAbcd(1, 80, 1, 80, j, k), new ParametricAbcd(1, 100, 1, 50, j, k)
		};
		// ParametricCircle

		chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
		for (ParametricEquation eq : equations)
			chart.getScene().getGraph().add(new ParametricDrawable(eq, 0, Math.PI, 50000, Color.random()));
		chart.getAxeLayout().setMainColor(color);
		chart.getView().setViewPositionMode(ViewPositionMode.TOP);

	}
}
