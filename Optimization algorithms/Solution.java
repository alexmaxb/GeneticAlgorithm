import java.util.ArrayList;
import java.awt.*;

public class Solution
{
    City[] order;
    double fitnessValue;
    double rangeMin, rangeMax;
    
    public Solution(ArrayList<City> allCities)
    {
        ArrayList<City> citiesClone = cloneArrayList(allCities);
        order = new City[citiesClone.size()];
        order[0] = citiesClone.get(0);
        citiesClone.remove(0);
        for(int i = 1; i < order.length; i++)
        {
            int num = (int)(Math.random() * citiesClone.size());
            order[i] = citiesClone.get(num);
            citiesClone.remove(num);
        }
        fitnessValue = getFitness();
        rangeMin = 0;
        rangeMax = 0;
    }
    
    public Solution(Solution s1, Solution s2)
    {
        order = new City[s1.order.length];
        if(Math.random() <= .7)
        {
            int crossNum = (int)(Math.random() * (s1.order.length - 1)) + 1;
            for(int i = 0; i < s1.order.length && i < crossNum; i++)
            {
                order[i] = s1.order[i];
            }
            ArrayList<City> s2CityOrder = new ArrayList<City>();
            for(int i = 0; i < s2.order.length; i++)
            {
                boolean alreadyUsed = false;
                for(int n = 0; n < crossNum; n++)
                {
                    if(order[n].x == s2.order[i].x && order[n].y == s2.order[i].y)
                    {alreadyUsed = true;}
                }
                if(!alreadyUsed)
                s2CityOrder.add(s2.order[i]);
            }
            int n = 0;
            for(int i = crossNum; i < order.length && n < s2CityOrder.size(); i++,n++)
            {
                order[i] = s2CityOrder.get(n);
            }
        }
        else order = s1.order;
        
        fitnessValue = getFitness();
        rangeMin = 0;
        rangeMax = 0;
        mutate();
    }
    
    public Solution(Solution toBeCopied)
    {
        order = new City[toBeCopied.order.length];
        for(int i = 0; i < order.length; i++)
        {
            order[i] = toBeCopied.order[i];
        }
        fitnessValue = getFitness();
    }
    
    public ArrayList<City> cloneArrayList(ArrayList<City> allCities)
    {
        ArrayList<City> ans = new ArrayList<City>();
        for(int i = 0; i < allCities.size(); i++)
        {
            ans.add(allCities.get(i));
        }
        return ans;
    }
    
    public void print()
    {
        String s = "Randomized:";
        for(int i = 0; i < order.length; i++)
        {
            s = s + ", City " + i + ": (" + order[i].x + ", " + order[i].y + ")";
        }
        System.out.println(s);
    }
    
    public static void print(ArrayList<City> list)
    {
        String s = "Original:";
        for(int i = 0; i < list.size(); i++)
        {
            s = s + ", City " + i + ": (" + list.get(i).x + ", " + list.get(i).y + ")";
        }
        System.out.println(s);
    }
    
    
    
    public double getFitness()
    {
        double fitness = 0;
        for(int i = 0; i < order.length - 1; i++)
        {
            fitness += Math.sqrt(Math.pow((order[i+1].x - order[i].x),2) + Math.pow((order[i+1].y - order[i].y),2));
        }
        fitness += Math.sqrt(Math.pow((order[order.length - 1].x - order[0].x),2) + Math.pow((order[order.length - 1].y - order[0].y),2));
        return fitness;
    }
    
    public void paint(Graphics g, Color c)
    {
        //fitnessValue = getFitness();
        g.setColor(c);
        for(int i = 0; i < order.length - 1; i++)
        {
            g.drawLine(order[i].x + 30,order[i].y + 150,order[i+1].x + 30,order[i+1].y + 150);
        }
        g.drawLine(order[0].x + 30,order[0].y + 150,order[order.length - 1].x + 30,order[order.length - 1].y + 150);
    }
    
    public void mutate()
    {
        double r = Math.random();
        int t = 0;
        
        if(Math.random() < .025)
            t = 3;
            else if(Math.random() < .05)
                    t = 2;
                    else if(Math.random() < .2)
                            t = 1;
                            
        for(int i = 0; i < t; i++)
        switchRandomCities();
    }
    
    public void switchRandomCities()
    {
        int firstCity = (int)(Math.random() * (order.length - 1)) + 1;
        int secondCity = (int)(Math.random() * (order.length - 1)) + 1;
        City firstCityPointer = order[firstCity];
        order[firstCity] = order[secondCity];
        order[secondCity] = firstCityPointer;
    }
    
    
    
    public static void main(String[] args)
    {
        ArrayList<City> test = new ArrayList<City>();
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        test.add(new City(300,300));
        print(test);
        Solution s = new Solution(test);
        s.print();
        
    }
    
    
}

