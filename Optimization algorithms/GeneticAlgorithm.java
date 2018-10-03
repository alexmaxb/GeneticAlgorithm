import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GeneticAlgorithm extends JPanel implements KeyListener
{
    ArrayList<Solution> generation;
    ArrayList<City> cities;
    int generationNum = 0;
    JFrame frame;
    Solution bestOfGeneration;
    double genTotalFitness;
    boolean showAll = true;
    static boolean play = false;
    double lowestFitnessValue;
    Graph gr;
    
    public GeneticAlgorithm(int numCities, int xBound, int yBound, int generationSize)
    {
        cities = new ArrayList<City>();
        generation = new ArrayList<Solution>();
        for(int i = 0; i < numCities; i++)
        {
            cities.add(new City(xBound,yBound));
        }
        String s = "{";
        for(int i = 0; i < numCities; i++)
        {
            s += "new City(" + cities.get(i).x + ", " + cities.get(i).y + "),";
        }
        s += "}";
        System.out.println(s);
        
        for(int i = 0; i < generationSize; i++)
        {
            generation.add(new Solution(cities));
        }
        frame = new JFrame("Genetic Algorithm");
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        bestOfGeneration = generation.get(0);
        for(int i = 0; i < generation.size(); i++)
        {
            if(generation.get(i).getFitness() < bestOfGeneration.getFitness())
            bestOfGeneration = generation.get(i);
        }

        
        lowestFitnessValue = bestOfGeneration.getFitness();
        gr = new Graph(700,150,500,500,new GraphPoint(0,(int)lowestFitnessValue));
        frame.addMouseMotionListener(gr);
        setRanges();
        frame.repaint();
    }
    
    public GeneticAlgorithm(ArrayList<City> cityList, int generationSize)
    {
        cities = cityList;
        generation = new ArrayList<Solution>();
        for(int i = 0; i < generationSize; i++)
        {
            generation.add(new Solution(cities));
        }
        frame = new JFrame("Genetic Algorithm");
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        bestOfGeneration = generation.get(0);
        for(int i = 0; i < generation.size(); i++)
        {
            if(generation.get(i).getFitness() < bestOfGeneration.getFitness())
            bestOfGeneration = generation.get(i);
        }
        lowestFitnessValue = bestOfGeneration.getFitness();
        setRanges();
        frame.repaint();
    }
    
    public void moveToNextGeneration()
    {
        ArrayList<Solution> nextGen = new ArrayList<Solution>();
        nextGen.add(new Solution(bestOfGeneration));
        for(int i = 0; i < 5; i++)
        {
            nextGen.add(new Solution(bestOfGeneration));
            nextGen.get(nextGen.size() - 1).switchRandomCities();
        }
        while(nextGen.size() < generation.size())
        {
            double parent1 = (Math.random() * genTotalFitness);
            double parent2 = (Math.random() * genTotalFitness);
            
            Solution p1 = null;
            Solution p2 = null;
            int i = 0;
            while((p1 == null || p2 == null) && i < generation.size())
            {
                if(p1 == null)
                {
                    if(parent1 >= generation.get(i).rangeMin && parent1 <= generation.get(i).rangeMax)
                    p1 = generation.get(i);
                }
                
                if(p2 == null)
                {
                    if(parent2 >= generation.get(i).rangeMin && parent2 <= generation.get(i).rangeMax)
                    p2 = generation.get(i);
                }
                i++;
            }
            
            nextGen.add(new Solution(p1,p2));
            
        }
        
        generation = nextGen;
        generationNum++;
        bestOfGeneration = generation.get(0);
        /*for(int i = 1; i < generation.size(); i++)
        {
            if(generation.get(i).getFitness() < bestOfGeneration.getFitness())
            bestOfGeneration = generation.get(i);
        }*/
        findBestOfGen();
        if(bestOfGeneration.fitnessValue < lowestFitnessValue)
        gr.addPoint(new GraphPoint(generationNum,(int)bestOfGeneration.fitnessValue));
        if(bestOfGeneration.fitnessValue < lowestFitnessValue)
        lowestFitnessValue = bestOfGeneration.fitnessValue;
        //System.out.println("Gen:" + generationNum + ", Best of Generation: " + bestOfGeneration.getFitness());
        setRanges();
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1800,1800);
        g.setColor(Color.GRAY);
        g.fillRect(15,150 - 15,530,530);
        g.setColor(Color.WHITE);
        g.fillRect(25,145,510,510);
        if(showAll)
        for(int i = 0; i < generation.size(); i ++)
        {
            generation.get(i).paint(g, Color.RED);
        }
        bestOfGeneration.paint(g, Color.BLUE);
        g.setColor(Color.BLUE);
        g.fillOval(cities.get(0).x - 5 + 30,cities.get(0).y - 5 + 150,10,10);
        g.setColor(Color.GREEN);
        for(int i = 1; i < cities.size(); i++)
        {
            g.fillOval(cities.get(i).x - 2 + 30,cities.get(i).y - 2 + 150,4,4);
        }
        g.setColor(Color.BLACK);
        g.drawString("Generation: " + generationNum, 10, 20);
        g.drawString("Best Solution Fitness Value: " + bestOfGeneration.getFitness(), 10, 50);
        g.drawString("Generation Size: " + generation.size(), 10, 80);
        g.drawString("LowestValue: " + lowestFitnessValue, 10, 110);
        gr.paint(g);
        //g.drawString("Best of Gen's chance of being chosen: " + (bestOfGeneration.fitnessValue / (1/genTotalFitness) * 100), 10, 140);
        //g.drawString("Average Fitness: " + ((1/genTotalFitness) / generation.size()), 10, 170);
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        GeneticAlgorithm ga = new GeneticAlgorithm(100,500,500,800);
        for(int i = 0; ;i++)
        {
            if(play)
            {ga.moveToNextGeneration();}
            ga.repaint();
            Thread.sleep(25);
        }
    }
    
    public void setRanges()
    {
        double totalFitness = 0;
        for(int i = 0; i < generation.size(); i++)
        {
            generation.get(i).rangeMin = totalFitness;
            totalFitness += 1 / generation.get(i).getFitness();
            generation.get(i).rangeMax = totalFitness;
        }
        
        genTotalFitness = totalFitness;
    }
    
    public void findBestOfGen()
    {
        Solution s = generation.get(0);
        for(int i = 1; i < generation.size(); i++)
        {
            if(generation.get(i).getFitness() < s.getFitness())
            {s = generation.get(i);}
        }
        
        bestOfGeneration = s;
    }
    
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    public void keyPressed(KeyEvent e)
    {
        int i = e.getKeyCode();
        if(i == KeyEvent.VK_SPACE)
        {
            showAll = !showAll;
        }
        if(i == KeyEvent.VK_P)
        {
            play = !play;
        }
    }
    
    public void keyTyped(KeyEvent e)
    {
        int i = e.getKeyCode();
        if(i == KeyEvent.VK_SPACE)
        {
            showAll = !showAll;
        }
    }
    
    public static void test() throws InterruptedException
    {
        ArrayList<City> cityList = new ArrayList<City>();
        cityList.add(new City(20,200-5));
        cityList.add(new City(85,200-0));
        cityList.add(new City(140,200-10));
        cityList.add(new City(150,200-0));
        cityList.add(new City(195,200-35));
        cityList.add(new City(165,200-90));
        cityList.add(new City(120,200-70));
        cityList.add(new City(95,200-80));
        cityList.add(new City(115,200-100));
        cityList.add(new City(140,200-120));
        cityList.add(new City(175,200-140));
        cityList.add(new City(120,200-190));
        cityList.add(new City(65,200-160));
        cityList.add(new City(55,200-165));
        cityList.add(new City(35,200-155));
        cityList.add(new City(30,200-140));
        cityList.add(new City(10,200-140));
        cityList.add(new City(45,200-120));
        cityList.add(new City(40,200-70));
        cityList.add(new City(15,200-35));
        GeneticAlgorithm ga = new GeneticAlgorithm(cityList,200);
        for(int i = 0; ;i++)
        {
            ga.moveToNextGeneration();
            ga.repaint();
            Thread.sleep(25);
        }
    }
    
    
}