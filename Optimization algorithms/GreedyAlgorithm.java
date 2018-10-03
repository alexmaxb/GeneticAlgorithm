import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
/**
 * Write a description of class GreedyAlgorithm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GreedyAlgorithm extends JPanel
{
    ArrayList<City> cities;
    Solution s;
    int numCities;
    JFrame frame;
    
    public GreedyAlgorithm(int xBound, int yBound, int nCities)
    {
        cities = new ArrayList<City>();
        for(int i = 0; i < nCities; i++)
        {
            cities.add(new City((int)(Math.random() * xBound),(int)(Math.random() * xBound)));
        }
        numCities = nCities;
        frame = new JFrame("Greedy Algorithm");
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.add(this);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }
    
    public GreedyAlgorithm(ArrayList<City> cityList)
    {
        cities = cityList;
        numCities = cityList.size();
        frame = new JFrame("Greedy Algorithm");
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.add(this);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }
    
    public void findSolution()
    {
        int times = cities.size() - 1;
        ArrayList<City> order = new ArrayList<City>();
        order.add(cities.get(0));
        cities.remove(0);
        for(int i = 0; i < times; i++)
        {
            City closest = cities.get(0);
            int loc = 0;
            for(int n = 0; n < cities.size(); n++)
            {
                if(calcDist(order.get(i),closest) > calcDist(order.get(i),cities.get(n)))
                {
                    loc = n;
                    closest = cities.get(n);
                }
            }
            order.add(closest);
            cities.remove(loc);
        }
        Solution sol = new Solution(order);
        for(int i = 0; i < order.size(); i++)
        {
            sol.order[i] = order.get(i);
        }
        s = sol;
    }
    
    public double calcDist(City c1, City c2)
    {
        return Math.sqrt(Math.pow(c2.x - c1.x,2) + Math.pow(c2.y - c1.y,2));
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1800,1800);
        s.paint(g,Color.GREEN);
        g.setColor(Color.YELLOW);
        for(int i = 1; i < s.order.length; i++)
        {
            g.fillOval(s.order[i].x - 2 + 300, s.order[i].y - 2 + 300,4,4);
        }
        g.setColor(Color.WHITE);
        g.drawString(s + "",20,20);
       
    }
    
    public static void main()
    {
        City[] citylist = {new City(499, 449),new City(49, 190),new City(330, 170),new City(279, 381),new City(468, 397),new City(474, 353),new City(55, 394),new City(127, 73),new City(399, 40),new City(44, 110),new City(117, 243),new City(139, 241),new City(281, 391),new City(124, 173),new City(348, 264),new City(38, 473),new City(371, 94),new City(470, 242),new City(175, 215),new City(266, 201),new City(102, 363),new City(101, 186),new City(210, 370),new City(408, 112),new City(436, 38),new City(368, 184),new City(141, 35),new City(470, 93),new City(260, 120),new City(472, 105),new City(321, 474),new City(108, 262),new City(64, 276),new City(322, 11),new City(114, 29),new City(493, 323),new City(240, 439),new City(234, 317),new City(153, 383),new City(132, 82),new City(498, 244),new City(122, 268),new City(305, 10),new City(106, 67),new City(487, 171),new City(417, 492),new City(261, 9),new City(281, 363),new City(357, 384),new City(120, 36),new City(284, 107),new City(199, 364),new City(117, 392),new City(492, 413),new City(291, 286),new City(471, 276),new City(262, 274),new City(7, 311),new City(56, 74),new City(113, 342),new City(277, 335),new City(343, 232),new City(445, 186),new City(407, 379),new City(243, 185),new City(280, 467),new City(231, 331),new City(472, 322),new City(405, 371),new City(33, 436),new City(158, 94),new City(381, 140),new City(167, 294),new City(133, 32),new City(28, 482),new City(257, 63),new City(138, 283),new City(136, 257),new City(249, 280),new City(433, 217),new City(438, 324),new City(35, 42),new City(368, 57),new City(368, 390),new City(127, 68),new City(377, 279),new City(268, 228),new City(135, 243),new City(234, 339),new City(240, 215),new City(306, 242),new City(442, 456),new City(492, 47),new City(315, 489),new City(323, 72),new City(243, 376),new City(3, 463),new City(475, 198),new City(52, 325),new City(153, 391)};
        ArrayList<City> list = new ArrayList<City>();
        for(int i = 0;i < citylist.length; i++)
        {
            list.add(citylist[i]);
        }
        
        GreedyAlgorithm gra = new GreedyAlgorithm(list);
        gra.findSolution();
        gra.repaint();
    }
}
