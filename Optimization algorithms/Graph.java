import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Graph implements MouseMotionListener
{
    int width, height, x, y;//x,y on screen it will be placed. width and height it will be in pixels
    double yAxisLargestNum;
    double xAxisLargestNum;
    ArrayList<GraphPoint> points;
    int mouseX,mouseY;
    
    public Graph(int xLoc, int yLoc, int _width, int _height, GraphPoint initializationPoint)
    {
        x = xLoc;
        y = yLoc;
        width = _width;
        height = _height;
        points = new ArrayList<GraphPoint>();
        yAxisLargestNum = initializationPoint.yPoint;
        xAxisLargestNum = initializationPoint.xPoint;
        points.add(initializationPoint);
    }
    
    public void addPoint(GraphPoint gp)
    {
        points.add(gp);
        if(gp.xPoint > xAxisLargestNum)
        xAxisLargestNum = gp.xPoint;
        if(gp.yPoint > yAxisLargestNum)
        yAxisLargestNum = gp.yPoint;
    }
    
    public Graphics paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawLine(x,y,x,y + height + 5);
        g.drawLine(x - 5,y+height,x + width,y+height);
        
        g.drawLine(x - 5,y + height / 2, x - 5 + 10, y + height / 2);
        g.drawLine(x - 5,y + height / 4, x - 5 + 10, y + height / 4);
        g.drawLine(x - 5,y + height / 4 * 3, x - 5 + 10, y + height / 4 * 3);
        g.drawLine(x - 5,y + height, x - 5 + 10, y + height);
        g.drawString("" + yAxisLargestNum, x - 5, y - 10);
        
        g.drawLine(x + width / 2, y + height - 5, x + width / 2, y + height - 5 + 10);
        g.drawLine(x + width / 4, y + height - 5, x + width / 4, y + height - 5 + 10);
        g.drawLine(x + width / 4 * 3, y + height - 5, x + width / 4 * 3, y + height - 5 + 10);
        g.drawLine(x + width, y + height - 5, x + width, y + height - 5 + 10);
        g.drawString("" + xAxisLargestNum, x + width - 15, y + height + 15);
        
        for(int i = 0; i < points.size(); i++)
        {
            int xPt =(int)( x + (points.get(i).xPoint / xAxisLargestNum) * width);
            int yPt =(int)( y + (height - (points.get(i).yPoint / yAxisLargestNum) * height));
            g.fillOval(xPt - 2,yPt - 2,4,4);
        }
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height)
        {
            g.fillRect(mouseX,mouseY+25,120,50);
            g.setColor(Color.RED);
            g.drawLine(mouseX,mouseY,mouseX,mouseY + 25);
            g.drawRect(mouseX,mouseY+25,120,50);
            g.drawString("Generation:" + ((int)(((mouseX - x + 0.0) / width) * xAxisLargestNum)), mouseX + 10, mouseY + 45);
            g.drawString("Fitness:" + ((int)(((height - (mouseY - y + 0.0)) / height) * yAxisLargestNum)), mouseX + 10, mouseY + 65);
            g.drawLine(x,mouseY,x+width,mouseY);
            g.drawLine(mouseX,y,mouseX,y+height);
        }
        return g;
    }
    
    public void mouseMoved(MouseEvent me)
    {
        mouseX = me.getX();
        mouseY = me.getY() - 25;
    }
    
    public void mouseDragged(MouseEvent me)
    {
        
    }
    
}