import java.awt.Point;
import java.awt.geom.*;

/**
 * Write a description of class UnitAI0 here.
 * 
 * @author TuTu
 * @version 2007.11.16
 */
public class UnitAI0 extends UnitAI
{
    /**
     * Constructor for objects of class UnitAI0
     */
    public UnitAI0(Unit u, Board b)
    {
        super(u, b);
        fitInBlock = true;
        direction = new Direction();
        midDestination = new Point();
    }
    
    /**
     * Move a unit to the destination block
     * Decide the current heading direction and move it
     * 
     * @param  dest    destination
     * @return        true if reach the destination
     */
    protected boolean move(double timeStep)
    {
        if (fitInBlock)
        {
            Point pos = unit.getBlockShape().getPivotBlock().getPosition();            
            int dx = destination.x - pos.x;
            int dy = destination.y - pos.y;        
            // already on the destination
            if (unit.getBlockShape().contains(board.getBlock(destination)))
            {
// System.out.println();                
// System.out.println("already on the destination, return true");                
                lastDirection = null;
                return true;
            }
        
            direction.setDirection(dx, dy);
            boolean passable;
            int count = 0;
//             midDestination.setLocation(pos.x + direction.x, pos.y + direction.y);
            BlockShape destShape = unit.getBlockShape().getOuterBoundaryBlockShape(direction);
            
            // Decide which direction to go
            if (destShape.isAbleToStepOn())
            {
// System.out.println();                
// System.out.println("Straight direction available");
                destShape.addUnit(unit);
            }
            // this unit has reached the closest point it can reach
            else if (board.getBlock(destination).isUnitOn() 
                     && board.getBlock(destination).getUnit().getBlockShape().intersects(destShape))
            {
// System.out.println();                
// System.out.println("this unit has reached the closest point it can reach, return true");
                lastDirection = null;
                return true;
            }
            // already ran into other object and changed direction last move, prefer moving in the same direction
            else if (lastDirection != null && !lastDirection.equals(direction))
            {
// System.out.println();
// System.out.println("already ran into other object and changed direction last move, prefer moving in the same direction");
                do
                {
// System.out.println("do loop, count = " + count);
// System.out.println("initial lastDirection:" + lastDirection.getDirection());
// System.out.println("initial direction:" + direction.getDirection());
                    Direction lastDirTemp = lastDirection.clone();
                    
                    lastDirTemp.turnAgainst(direction);
                    direction.turnToward(lastDirection);
                    lastDirection = lastDirTemp;
                    destShape = unit.getBlockShape().getOuterBoundaryBlockShape(direction);
// System.out.println("mid lastDirection:" + lastDirection.getDirection());
// System.out.println("mid direction:" + direction.getDirection());
                    if (destShape.isAbleToStepOn())
                    {
// System.out.println("mid direction passable, exit loop");                      
                        passable = true;
                        destShape.addUnit(unit);
                    }
                    else
                    {
// System.out.println("mid direction inpassable, loop again");
                        passable = false;
                        if (destShape.isMoving())
                        {
                            // the units on destShape are all moving, return false and wait for next move
                            return false;
                        }
                        else
                        {
                            count++;
                            // this unit is surrounded and is unable to move
                            if (count > 5)
                            {
    // System.out.println("this unit is surrounded and is unable to move, exit loop, return true");
                                lastDirection = null;
                                return true;
                            }
                        }
                    }
                }while (!passable);
            }
            else
            {
// System.out.println("");
                Direction rightDir = direction.clone();
                Direction leftDir = direction.clone();                           
                do
                {
// System.out.println("do loop - initial contact"); 
                    BlockShape rightBlockShape;
                    BlockShape leftBlockShape;
                    
                    rightDir.turnClockwise();
                    leftDir.turnCounterclockwise();
// System.out.println("direction:" + direction.getDirection());
// System.out.println("rightDir:" + rightDir.getDirection());
// System.out.println("leftDir:" + leftDir.getDirection());
                    rightBlockShape = unit.getBlockShape().getOuterBoundaryBlockShape(rightDir);
                    leftBlockShape = unit.getBlockShape().getOuterBoundaryBlockShape(leftDir);
                    if (rightBlockShape.isAbleToStepOn())
                    {
                        passable = true;
                        if (leftBlockShape.isAbleToStepOn())
                        {
                            double rand = Math.random();
                            if (rand < 0.5)
                            {
// System.out.println("both direction is able to pass, randomly chose right, exit loop");                                
                                direction = rightDir;
                                rightBlockShape.addUnit(unit);
                            }
                            else
                            {
// System.out.println("both direction is able to pass, randomly chose left, exit loop");                                
                                direction = leftDir;
                                leftBlockShape.addUnit(unit);
                            }
                        }
                        else
                        {
// System.out.println("Only right is able to pass, exit loop");                            
                            direction = rightDir;
                            rightBlockShape.addUnit(unit);
                        }
                    }
                    else if (leftBlockShape.isAbleToStepOn())
                    {
// System.out.println("Only left is able to pass, exit loop");                        
                        passable = true;
                        direction = leftDir;
                        leftBlockShape.addUnit(unit);
                    }
                    // this unit is surrounded and unable to move in each direction
                    else if (rightBlockShape.isMoving() || leftBlockShape.isMoving())
                    {
                        // the units on right or left are moving, return false and wait for next move
                        return false;
                    }
                    else if (count > 2)
                    {
// System.out.println("this unit is surrounded and unable to move in each direction, exit loop, return true");
                        lastDirection = null;
                        return true;
                    }
                    else
                    {
// System.out.println("Unable to pass, loop again");
                        passable = false;
                        count++;
                    }
                }while(!passable);
            }
            midDestination.setLocation(pos.x + direction.x, pos.y + direction.y);
            lastDirection = direction.clone();
        }

        // reached the midDestination
        if (move(direction, midDestination, timeStep))
        {
// System.out.println("Reached the mid-dest!");
// System.out.println("drawPosition:" + unit.getDrawPosition().toString());
// System.out.println("pivotPosition:" + unit.getPivotPosition().toString());
// System.out.println("pivotBlockPosition:" + unit.getBlockShape().getPivotBlock().getPosition().toString());
// System.out.println("");
// System.exit(1);
            fitInBlock = true;
            unit.getBlockShape().removeUnit();
            unit.getBlockShape().translate(direction.x, direction.y);
            unit.getBlockShape().removeUnit();
            unit.getBlockShape().addUnit(unit);
            if (unit.getBlockShape().contains(board.getBlock(destination)))
            {
// System.out.println("Reached the final-dest!");
// System.out.println("midDestination:" + midDestination.toString());
// System.out.println("destination:" + destination.toString());
// System.out.println("drawPosition:" + unit.getDrawPosition().toString());
// System.out.println("pivotPosition:" + unit.getPivotPosition().toString());
// System.out.println("pivotBlockPosition:" + unit.getBlockShape().getPivotBlock().getPosition().toString());
// System.out.println("");
// System.exit(1);
                lastDirection = null;
                return true;
            }
            else
            {
// System.out.println("Not reach the final-dest yet.");                
                return false;
            }
        }
        else
        {
// System.out.println("Not reach the mid-dest yet.");            
            fitInBlock = false;
            return false;
        }
    }
    
    /**
     * Move a unit to the direction with specified time step
     * 
     * @param  dir      direction
     * @param  timeStep time step
     * @param  dest     destination point
     * @return  true if the unit has reached the destination point
     */
    protected boolean move(Direction dir, Point dest, double timeStep)
    {
        double dX = dir.normalX * unit.getSpeed() * timeStep;
        double dY = dir.normalY * unit.getSpeed() * timeStep;
        Point2D.Double pos = unit.getPivotPosition();
        double newX = pos.x + dX;
        double newY = pos.y + dY;
        // return true if moving too far or is reached the dest, ie. exceed or at the destination position
        if (((newX >= dest.x && dest.x >= pos.x) || (newX <= dest.x && dest.x <= pos.x))
            && ((newY >= dest.y && dest.y >= pos.y) || (newY <= dest.y && dest.y <= pos.y)))
        {          
            unit.setPivotPosition(dest.x, dest.y);
            return true;
        }
        else
        {
            unit.setPivotPosition(newX, newY);
            return false;
        }
    }    

    /**
     * Generate a unit with specified type
     * 
     * @param  motherUnit    the unit which is doing the generation
     * @param  childType    unit type to be generated
     * @return        true if the generation succeed
     */
    public boolean generateUnit(final int type)
    {
        switch (type)
        {
            case (Unit.TYPE_BASIC_BATTLE):
                boolean isVacant;
                Direction dir = new Direction();
                // let the random dir be vertical or horizontal
                while (dir.x != 0 && dir.y != 0)
                {
                    dir.setDirection(Direction.getRandomDirection());
                }
                BlockShape[] boundaryBlockShape = new BlockShape[BasicBattleUnit.DIMENSION.width];
//                 BlockShape boundaryBlockShape;
                // run through total 4 directions
                for (int j = 0; j < 4; j++)
                {
//                     boundaryBlockShape = unit.getBlockShape().getOuterBoundaryBlockShape(dir);
                    boundaryBlockShape[0] = unit.getBlockShape().getOuterBoundaryBlockShape(dir); 
                    for (int i = 1; i < BasicBattleUnit.DIMENSION.width; i++)
                    {
                        boundaryBlockShape[i] = boundaryBlockShape[i-1].getOuterBoundaryBlockShape(dir);
                    }
                    
                    for (int i = 0; i < boundaryBlockShape[0].getSize() - BasicBattleUnit.DIMENSION.width + 1; i += BasicBattleUnit.DIMENSION.width)
                    {
                        isVacant = true;
                        for (int k = 0; k < BasicBattleUnit.DIMENSION.width; k++)
                        {
                            for (int l = 0; l < BasicBattleUnit.DIMENSION.width; l++)
                            {
                                if(!boundaryBlockShape[k].getBlock(i+l).isAbleToStepOn())
                                {
                                    isVacant = false;
                                    // this square is occupied, find next
                                }                                                     
                            }
                        }
                        if (isVacant)
                        {
                            if (dir.equals(Direction.DOWN) || dir.equals(Direction.RIGHT))
                                new BasicBattleUnit(unit.getOwner(), unit.getBoard(), boundaryBlockShape[0].getBlock(i).getPosition());
                            else
                                new BasicBattleUnit(unit.getOwner(), unit.getBoard(), boundaryBlockShape[BasicBattleUnit.DIMENSION.width-1].getBlock(i).getPosition());
                            return true;
                        }
                    }                    
                    // turn 90 degrees
                    dir.turnClockwise();
                    dir.turnClockwise();
                }
                return false;
            default:
                throw new IllegalArgumentException("Undefined unit type or that type cannot be generated");
        }
    }
    
    /**
     * Perform the attack action
     * 
     * @param  timeStep    the timeStep
     */
    protected void attack(double timeStep)
    {       
        Attackable attackUnit = (Attackable)unit;
        Block targetBlock = unit.getBlockShape().getClosestBlock(target.getBlockShape());
        int targetBlockIndex = target.getBlockShape().getBlockIndex(targetBlock);
// System.out.println("fitInBlock: " + fitInBlock);
// System.out.println("Board.getDistance(unit, target): " + Board.getDistance(unit.getBlockShape().getPivotBlock(), target.getBlockShape().getPivotBlock()));
// System.out.println("attackUnit.getAttackRangeUpperLimit(): " + attackUnit.getAttackRangeUpperLimit());
        if (fitInBlock && Board.getDistance(unit.getBlockShape().getPivotBlock(), targetBlock) 
            <= attackUnit.getAttackRangeUpperLimit())
        {
            attackUnit.countDownAttackTimeCounter(timeStep);
            if (attackUnit.getAttackTimeCounter() <= 0.)
            {
                attackUnit.shoot(target, targetBlockIndex);
                attackUnit.resetAttackTimeCounter();
            }
            // Be able to attack, that is this unit has reached the destination of this "attack move" 
            // Note: After the pathfinding mechanism is set up, this assignment can be removed!
            lastDirection = null;
        }
        else
        {// target is out of range
//             destination = target.getBlockShape().getPivotBlock().getPosition();
            destination = targetBlock.getPosition();
            move(timeStep);
        }
    }
    
    private Direction direction;
    private Direction lastDirection;
    private Point midDestination;
    private boolean fitInBlock;
    Block targetBlock;
}
