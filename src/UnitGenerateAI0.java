
/**
 * Write a description of class UnitGenerateAI0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UnitGenerateAI0 implements UnitGenerateAI
{
    /**
     * Constructor for objects of class UnitGenerateAI0
     */
    public UnitGenerateAI0()
    {        
    }

//     /**
//      * Generate a unit with specified type
//      * 
//      * @param  motherUnit    the unit which is doing the generation
//      * @param  childType    unit type to be generated
//      * @return        true if the generation succeed
//      */
//     public static boolean generateUnit(Generatable mUnit, final int type)
//     {
//         Unit unit = (Unit)mUnit;
//         switch (type)
//         {
//             case (Unit.TYPE_BASIC_BATTLE):
//                 Direction dir = new Direction();
//                 BlockShape boundaryBlockShape;
//                 // run through total 8 directions
//                 for (int j = 0; j < 8; j++)
//                 {
//                     boundaryBlockShape = motherUnit.getBlockShape().getOuterBoundaryBlockShape(dir);
//                     for (int i = 0; i < boundaryBlockShape.getSize(); i++)
//                     {
//                         if(boundaryBlockShape.getBlock(i).isAbleToStepOn())
//                         {
//                             new BasicBattleUnit(motherUnit.getOwner(), motherUnit.getBoard(), boundaryBlockShape.getBlock(i).getPosition());
//                             return true;
//                         }
//                     }
//                     dir.turnClockwise();
//                 }
//                 return false;
//             default:
//                 throw new IllegalArgumentException("Undefined unit type or that type cannot be generated");
//         }
//     }
}
