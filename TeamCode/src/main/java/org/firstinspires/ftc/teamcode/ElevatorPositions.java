package org.firstinspires.ftc.teamcode;

public class ElevatorPositions {
    public static int MID_JUNCTION_TICKS = 1550;
    public static int PREPARE_FOR_PICKUP_TICKS = 400;
    public static int LEVEL5TICKS = 50;

    public static int getMediumJunctionTicks(){

        return MID_JUNCTION_TICKS;
    }

    public static int getPrepareForPickup(){
        return PREPARE_FOR_PICKUP_TICKS;

    }

    public static int getStackLevelTicks(int level){

        if (level == 5){
            return LEVEL5TICKS;
        } else {
            return 0;
        }

    }

}
