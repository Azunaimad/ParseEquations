package rewrote.ui.latex;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class LatexPanel extends JPanel {
    private final static int SIZE = 15;
    private final static Insets BORDERS = new Insets(5, 5, 5, 5);

    private JTextArea latexSource;

    public LatexPanel(JTextArea latexSource){
        super();
        this.latexSource = latexSource;
        this.latexSource.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                render();
            }
        });

    }

    public void render() {

            // get the text and translate to latex notation
            String latex = FormulasToLatex.translate(latexSource.getText());

            // create a formula
            TeXFormula formula = new TeXFormula(latex);

            // render the formula to an icon of the same size as the formula.
            TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, SIZE);

            // insert a border
            icon.setInsets(BORDERS);

            // now create an actual image of the rendered equation
            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                                                    icon.getIconHeight(),
                                                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel jl = new JLabel();
            jl.setForeground(Color.BLACK);
            icon.paintIcon(jl, g2, 0, 0);
            // at this point the image is created, you could also save it with ImageIO

            // now draw it to the screen
            Graphics g = getGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());
            Graphics2D g3 = (Graphics2D) g;
            g3.setPaint(Color.WHITE);
            g3.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
            g.drawImage(image, 0, 0, null);

    }
}
