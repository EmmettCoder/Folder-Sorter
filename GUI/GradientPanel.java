package GUI;
import java.awt.*;
import javax.swing.JPanel;

/**
 * Custom panel to have a gradient background.
 * 
 * @author Emmett Grebe
 * @version 3-6-2026
 */
public class GradientPanel extends JPanel {

    private Point startPoint;
    private Point endPoint;

    private Color startColor;
    private Color endColor;


    /**
     * Explicit constructor.
     * 
     * @param startColor Starting color. Color.
     * @param endColor Starting color. Color.
     * @param startPoint Start point. Point.
     * @param endPoint End point. Point.
     */
    public GradientPanel(Color startColor, Color endColor, Point startPoint, Point endPoint) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Sets the colors.
     * 
     * @param startColor The color to start with.
     * @param endColor The color to end with.
     */
    public void setColors(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (endPoint.getX() == 0 && endPoint.getY() == 0) endPoint = new Point(getWidth(), getHeight());

        // Create the GradientPaint object:
        // x1, y1: Start point coordinates (0, 0 is top-left)
        // color1: Start color
        // x2, y2: End point coordinates
        // color2: End color
        GradientPaint gp = new GradientPaint(
            startPoint, startColor,
            endPoint, endColor
        );

        // Set the paint and fill the component
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
