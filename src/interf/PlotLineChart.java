package interf;

import com.sun.glass.ui.Application;
import namedstruct.Array;
import namedstruct.ArrayStructure;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
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

        double[] real = {246.5,	253.175, 259.85, 266.525, 273.2, 265.225, 257.25, 249.275, 241.3,
                240.775, 240.25, 239.725, 239.2, 247.05, 254.9, 262.75, 270.6, 264.75, 258.9, 253.05};

        if(startIter == endIter)
            endIter = endIter+1;
        XYSeries[] serieses = new XYSeries[endIter-startIter+2];
        XYSeriesCollection dataset = new XYSeriesCollection();

        double[] mean = array.getMean();
        serieses[0] = new XYSeries("Mean");
        for(int j=0; j<array.getNOfElements(); j++){
            serieses[0].add(j, mean[j]);
        }
        dataset.addSeries(serieses[0]);

        serieses[1] = new XYSeries("Real");
        for(int j=0; j<array.getNOfElements(); j++){
            serieses[1].add(j,real[j]);
        }
        dataset.addSeries(serieses[1]);

        for(int i=2; i<=serieses.length-1; i++){
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
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getRenderer().setSeriesPaint(0,Color.BLACK);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
        plot.getRenderer().setSeriesPaint(1,Color.BLACK);
        float[] dot = {6.0f};
        plot.getRenderer().setSeriesStroke(1, new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dot, 0.0f));

        for(int i=2; i<=nOfLines+2; i++)
            plot.getRenderer().setSeriesPaint(i,Color.GRAY);


        return chart;
    }
}
