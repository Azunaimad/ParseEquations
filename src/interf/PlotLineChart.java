package interf;

import com.sun.glass.ui.Application;
import namedstruct.Array;
import namedstruct.ArrayStructure;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Locale;


public class PlotLineChart extends ApplicationFrame {

    private int width = 500;
    private int height = 270;
    private int nOfLines = 0;

    public PlotLineChart(String title, Array array, int startIter, int endIter, String xName, String yName) {
        super(title);
        XYDataset dataset = createDataset(array, startIter, endIter);
        JFreeChart jFreeChart = createChart(dataset, title, xName, yName);
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));

        setContentPane(chartPanel);

    }

    @Override
    public void windowClosing(WindowEvent event) {
        if(event.getWindow() == this) {
            this.dispose();
        }
    }

    private XYDataset createDataset(Array array,
                                    int startIter,
                                    int endIter)
            throws NullPointerException, ArrayIndexOutOfBoundsException{

        if(startIter == endIter)
            endIter = endIter+1;
        XYSeries[] serieses = new XYSeries[endIter-startIter];
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int i=0; i<serieses.length; i++){
            serieses[i] = new XYSeries("Iteration "+i);
            for(int j = 0; j<array.getNOfElements(); j++)
                serieses[i].add(j,array.getValue(j,startIter+i));

            nOfLines++;
            dataset.addSeries(serieses[i]);
        }



        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, String chartName,  String xName, String yName){
        JFreeChart chart = ChartFactory.createXYLineChart(chartName,
                                                            xName,
                                                            yName,
                                                            dataset,
                                                            PlotOrientation.VERTICAL,
                                                            false,
                                                            true,
                                                            false);
        chart.setBackgroundPaint(Color.WHITE);
        XYPlot plot = chart.getXYPlot();
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        for(int i=0; i<nOfLines; i++)
            plot.getRenderer().setSeriesPaint(i,Color.RED);

        return chart;
    }
}
