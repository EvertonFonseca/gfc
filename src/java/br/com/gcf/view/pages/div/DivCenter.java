/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gcf.view.pages.div;

import eu.webtoolkit.jwt.Side;
import eu.webtoolkit.jwt.WColor;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WLength;
import eu.webtoolkit.jwt.WShadow;
import eu.webtoolkit.jwt.WStandardItemModel;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.chart.Axis;
import eu.webtoolkit.jwt.chart.AxisValue;
import eu.webtoolkit.jwt.chart.ChartType;
import eu.webtoolkit.jwt.chart.SeriesType;
import eu.webtoolkit.jwt.chart.WCartesianChart;
import eu.webtoolkit.jwt.chart.WDataSeries;
import java.util.EnumSet;

/**
 *
 * @author Windows
 */
public class DivCenter extends WContainerWidget {

    public DivCenter() {

        //ScatterPlotCurve();

    }

    void ScatterPlotCurve() {
        
        
        WContainerWidget container = new WContainerWidget(this);
        WStandardItemModel model = new WStandardItemModel(40, 2, container);
        model.setHeaderData(0, new WString("X"));
        model.setHeaderData(1, new WString("Y = sin(X)"));
        for (int i = 0; i < 40; ++i) {
            double x = ((double) i - 20) / 4;
            model.setData(i, 0, x);
            model.setData(i, 1, Math.sin(x));
        }
        WCartesianChart chart = new WCartesianChart(container);
        chart.setModel(model);
        chart.setXSeriesColumn(0);
        chart.setLegendEnabled(true);
        chart.setType(ChartType.ScatterPlot);
        chart.getAxis(Axis.XAxis).setLocation(AxisValue.ZeroValue);
        chart.getAxis(Axis.YAxis).setLocation(AxisValue.ZeroValue);
        chart.setPlotAreaPadding(120, EnumSet.of(Side.Right));
        chart.setPlotAreaPadding(40, EnumSet.of(Side.Top, Side.Bottom));
        WDataSeries s = new WDataSeries(1, SeriesType.CurveSeries);
        s.setShadow(new WShadow(3, 3, new WColor(0, 0, 0, 127), 3));
        chart.addSeries(s);
        chart.resize(new WLength(800), new WLength(300));
        chart.setMargin(new WLength(10), EnumSet.of(Side.Top, Side.Bottom));
        chart.setMargin(WLength.Auto, EnumSet.of(Side.Left, Side.Right));
    }
}
