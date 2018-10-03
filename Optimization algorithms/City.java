public class City
{
    int x;
    int y;
    public City(int _x, int _y, String cityName)
    {
        x = _x;
        y = _y;
    }
    
    public City(int xBound, int yBound)
    {
        x = (int)(Math.random() * xBound);
        y = (int)(Math.random() * yBound);
    }
}
