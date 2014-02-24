import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.geom.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * The whole game class
 * 
 * @author TuTu
 * @version 2007.11.8
 */
public class Game
{
    public static void main(String[] arg)
    {   
        Game game = new Game();
        game.start();
//         if (game.start() == Thread.State.valueOf("TERMINATED"))
//             System.exit(0);
    }

    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        playerList = new ArrayList<Player>(DEFAULT_NUM_PLAYERS+1); // plus one Mother
        for (int i = 0; i < DEFAULT_NUM_PLAYERS+1; i++)
        {
            if (i == 1)
            {
                humanPlayer = new HumanPlayer(i, i);
                playerList.add(humanPlayer);
            }
            else
            {
                playerList.add(new AIPlayer(i, i));
            }
        }
        panel = new GamePanel();        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
//         frame.setResizable(false);
        frame.pack();
        board = new Board(panel, DEFAULT_BOARD_DIM, (AIPlayer)playerList.get(0), humanPlayer);        
        frame.setVisible(true);
        placeInitialUnits();        
        thread = new GameThread(panel);
    }
    
    private class GameThread extends Thread
    {
        public GameThread(GamePanel p)
        {   
            setName("Game Thread");
            panel = p;
            alive = true;
        }
        
        public void run()
        {
            previousTime = System.currentTimeMillis();
            panel.repaint();            
            while(alive)
            {
                double timeStep = calculateTimeStep();
                for (int i = 0; i < playerList.size(); i++)
                {
                    ((Player)playerList.get(i)).takeAction(timeStep);
                }
                panel.repaint();
// System.out.println("pivotBlockPosition:" + humanPlayer.getUnit(1).getBlockShape().getPivotBlock().getPosition().toString());
// System.out.println("drawPosition:" + humanPlayer.getUnit(1).getDrawPosition().toString());
// System.out.println("pivotPosition:" + humanPlayer.getUnit(1).getPivotPosition().toString());
            }
        }
        
/*        public double calculateTimeStep()
        {
            double delta;
            long currentTime;
            
            // sleep more, make the cpu cooler
            try
            {
                Thread.sleep (10);
            }
            catch (InterruptedException exception)
            {
                System.out.println("InterruptedException");
                Thread.currentThread().interrupt();
            }
            currentTime = System.currentTimeMillis();
            delta = (currentTime - previousTime)/1000.;
            
//            do
//            {
//                currentTime = System.currentTimeMillis();
//                delta = (currentTime - previousTime)/1000.;
//                
//                if (delta == 0.)
//                {
//                    try
//                    {
//                        Thread.sleep (1);
//                    }
//                    catch (InterruptedException exception)
//                    {
//                        System.out.println("InterruptedException");
//                        Thread.currentThread().interrupt();
//                    }
//                }                
//            }while(delta == 0.);
            
            previousTime = currentTime;
            return delta;
        }*/
     
    	private double calculateTimeStep() {
    		double delta;
    		long delta_ms;
    		long currentTime;
    		
    		if (previousTime == 0) {
    			previousTime = System.currentTimeMillis();
    		}
    		currentTime = System.currentTimeMillis();    
    		delta_ms = currentTime - previousTime;
            if (delta_ms < MIN_DELTA_MS) {
    			// sleep more, make the cpu cooler
    	        try {
    	            Thread.sleep (MIN_DELTA_MS);
    	        }
    	        catch (InterruptedException exception) {
    	            System.out.println("InterruptedException");
    	            Thread.currentThread().interrupt();
    	        }
            }
            currentTime = System.currentTimeMillis();    
            delta = (currentTime - previousTime)/1000.;
            previousTime = currentTime;
            return delta;
    	}
        
        GamePanel panel;
        long previousTime;
        boolean alive;
    }
 
    /**
     * Write a description of class GamePanel here.
     * 
     * @author TuTu
     * @version 2007.11.8
     */
    public class GamePanel extends JPanel
    {
        /**
         * Constructor for objects of class GamePanel
         */
        public GamePanel()
        {
            // temp
            setPreferredSize(new Dimension(640, 640));
            setBackground(Color.WHITE);
            setDoubleBuffered(true);
//             timer = new Timer(TIMER_DELAY, new timerPerformer());
        }
    
        /**
         * override
         * 
         * @param  y   a sample parameter for a method
         * @return     the sum of x and y 
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;       
            render(g2);            
        }
        
        private void render(Graphics2D g2)
        {
            g2.setStroke(BASIC_STROKE);
            board.makeCoordTransform();
            g2.transform(board.getUserToDeviceCoordTransform());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
           
            if (board != null)
            {
                board.draw(g2);          
            }
        }
        
        private final BasicStroke BASIC_STROKE = new BasicStroke(0);
    }    
    
//     /**
//      * Get the board of the game
//      * 
//      * @return     board 
//      */
//     public final Board getBoard()
//     {
//         return board;
//     }
    
    /**
     * Get the players of the game
     * 
     * @return     player 
     */
    public final Player getPlayer(int i)
    {
        return (Player)playerList.get(i);
    }    

    /**
     * Generate a random double number with specified min and max
     * 
     * @return     double 
     */
    public static double getRandom(double min, double max)
    {
        return Math.random()*(max - min) + min;
    }    

//     /**
//      * Get the panel of this game
//      * 
//      * @return     panel 
//      */
//     public final JPanel getPanel()
//     {
//         return panel;
//     }

    /**
     * Place the initial units
     */
    public void placeInitialUnits()
    {
        new BaseUnit(getPlayer(1), board, new Point(0, 0));
        new BaseUnit(getPlayer(2), board, 
                     new Point(board.getDimension().width - BaseUnit.DIMENSION.width, 0));
        new BaseUnit(getPlayer(3), board, 
                     new Point(0, board.getDimension().height-BaseUnit.DIMENSION.height));
        new BaseUnit(getPlayer(4), board, 
                     new Point(board.getDimension().width - BaseUnit.DIMENSION.width,
                               board.getDimension().height - BaseUnit.DIMENSION.height));
        for (int i = 1; i < playerList.size(); i++)
        {
            for (int j = 0; j < BaseUnit.DEFAULT_INI_NUM_UNITS; j++)
            {
                if (!((BaseUnit)(getPlayer(i).getUnit(0))).generateUnit(Unit.TYPE_BASIC_BATTLE))
                    throw new RuntimeException("BaseUnit is unable to find place to initially generate units");
// System.out.println(((BaseUnit)(getPlayer(i).getUnit(0))).getBlockPosition(j).toString());
//                 new BasicBattleUnit(getPlayer(i), board, ((BaseUnit)(getPlayer(i).getUnit(0))).getBlockPosition(j));
            }
        }
        
//         new Battl
    }
    
    /**
     * Start the game
     */
    public Thread.State start()
    {
        thread.start();
        return thread.getState();
    }    
    
//     /**
//      * Let the units act
//      */
//     private void moveUnits(double timeStep)
//     {
// //         for (int i = 0; i < numberOfPlayers; i++)
//         MathVector2D v = new MathVector2D(0, 1);
//         double speed = 0.5;
//         players[Player.TOP_LEFT_SIDE].getUnit(0).move(v, speed, timeStep);
//     }    
    
    private ArrayList<Player> playerList;
    private HumanPlayer humanPlayer;
    private Board board;
//     public final int numberOfPlayers;
    private GamePanel panel;
    public GameThread thread;
    
//     private Timer timer;
//     private static final int TIMER_DELAY = 25; // mili-seconds
    
    private static final int DEFAULT_NUM_PLAYERS = 4;
    private static final int DEFAULT_BOARD_WIDTH = 80;
    private static final int DEFAULT_BOARD_HEIGHT = 80;
    private static final Dimension DEFAULT_BOARD_DIM
        = new Dimension(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT);
    public static final BasicStroke BASIC_STROKE = new BasicStroke(0);
	private static final long MIN_DELTA_MS = 20;
}
