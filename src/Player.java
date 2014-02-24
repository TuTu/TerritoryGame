import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Write a description of class Player here.
 * 
 * @author TuTu
 * @version 2007.11.8
 */
public class Player
{
    /**
     * Constructor for objects of class Player
     */
    public Player(final int i, final int tm)
    {
//         index = i;
        color = COLORS[i];
        team = tm;
//         switch (s)
//         {
//             case (MOTHER):
//                 color = Color.WHITE;
//                 break;
//             case (TOP_LEFT_SIDE):
//                 color = Color.RED;
//                 break;
//             case (TOP_RIGHT_SIDE):
//                 color = Color.BLUE;
//                 break;
//             case (BOTTOM_LEFT_SIDE):
//                 color = Color.GREEN.darker().darker();
//                 break;
//             case (BOTTOM_RIGHT_SIDE):
//                 color = Color.YELLOW.darker();
//                 break;
//         }
        unitList = new ArrayList<Unit>(INI_UNIT_CAPACITY);
    }

    /**
     * Get the color of the player
     * 
     * @return     color 
     */
    public final Color getColor()
    {
        return color;
    }
    
    /**
     * Get the team number of this player
     * 
     * @return     team number 
     */
    protected final int getTeam()
    {
        return team;
    }    
    
    /**
     * Return true if the specified player is a teammate of this player
     * 
     * @return     true if the specified player is a teammate of this player 
     */
    public boolean isTeammate(Player p)
    {
        if (team == p.getTeam())
            return true;
        else
            return false;
    }    

//     /**
//      * Get the side of the player
//      * 
//      * @return     color 
//      */
//     public final int getSide()
//     {
//         return side;
//     }    
    
    /**
     * Add a unit to this player
     * 
     * @param   unit to be added
     */
    public void addUnit(Unit u)
    {
        unitList.add(u);
    }
    
    /**
     * Remove a specified unit from this player
     * 
     * @param   unit to be removed
     */
    public void removeUnit(Unit u)
    {
        if (unitList.contains(u))
        {
            unitList.remove(u);
        }
        else
        {
            System.out.println("Player is trying to remove a unit not belongs to him!");
        }
    }    
    
//     /**
//      * Draw the units of this playerd][
//      * 
//      * @param  g2   the context of the graphics
//      */
//     public void drawUnits(Graphics2D g2)
//     {
//         for (int i = 0; i < unitList.size(); i++)
//         {
//             ((Unit)unitList.get(i)).drawBody(g2);
//         }
//         
//         for (int i = 0; i < unitList.size(); i++)
//         {
//             ((Unit)unitList.get(i)).drawHealth(g2);
//         }        
//     }    
    
    /**
     * Get the specified unit of this player
     * 
     * @return   unit
     */
    public Unit getUnit(int i)
    {
        if (i >= 0 && i < unitList.size())
            return (Unit)unitList.get(i);
        else
            throw new IndexOutOfBoundsException("Unit index out of size");
    }
    
//     /**
//      * Get the whole unit list of this player
//      * 
//      * @return   unitList
//      */
//     public final ArrayList getUnitList()
//     {
//         return unitList;
//     }    
    
//     /**
//      * Get number of units which this player owns
//      * 
//      * @return   numOfUnits
//      */
//     public final int getNumOfUnits()
//     {
//         return unitList.size();
//     }    
    
    /**
     * Get the color of the MOTHER
     * 
     * @return   Mother Color
     */
    public static final Color getMotherColor()
    {
        return COLORS[0];
    }  
    
    /**
     * Make the units of this player take action
     * 
     * @param   timeStep    time step for taking action
     */
    public void takeAction(double timeStep)
    {
        for (int i = 0; i < unitList.size(); i++)
        {
            ((Unit)unitList.get(i)).takeAction(timeStep);
        }
    }
    
    private ArrayList<Unit> unitList;
    private Color color;
    private int team;
//     private final int side;
    private static final int INI_UNIT_CAPACITY = 50;

    private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN.darker(), Color.YELLOW};
    private static final int TEAM0 = 0;
    private static final int TEAM1 = 1;
    private static final int TEAM2 = 2;
    private static final int TEAM3 = 3;
    private static final int TEAM4 = 4;
//     public static final Color MOTHER = Color.WHITE;
//     public static final Color RED = Color.RED;   
//     public static final Color BLUE = Color.BLUE;
//     public static final Color GREEN = Color.GREEN;
//     public static final Color YELLOW = Color.YELLOW;
}
