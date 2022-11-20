package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ElevatorPositions {
    public static int MID_JUNCTION_TICKS = 1166;



    public static double MULTIPLIER = 1.5;


    public static int HIGH_JUNCTION_TICKS = 1733;
    public static int LOW_JUNCTION_TICKS = 500;
    public static int PREPARE_FOR_PICKUP_TICKS = 533;


    public static int STACK_LEVEL5_TICKS = 233;
    public static int STACK_LEVEL4_TICKS = 200;
    public static int STACK_LEVEL3_TICKS = 166;
    public static int STACK_LEVEL2_TICKS = 133;
    public static int STACK_LEVEL1_TICKS = 100;

    public static int getLowJunctionTicks(){

        return LOW_JUNCTION_TICKS;
    }

    public static int getMediumJunctionTicks(){

        return MID_JUNCTION_TICKS;
    }

    public static int getHighJunctionTicks(){
        return HIGH_JUNCTION_TICKS;
    }

    public static int getPrepareForPickup(){
        return PREPARE_FOR_PICKUP_TICKS;

    }

    public static int getStackLevelTicks(int level){

        if (level == 5){
            return STACK_LEVEL5_TICKS;
        } else if(level == 4){
            return STACK_LEVEL4_TICKS;
        } else if(level ==3){
            return STACK_LEVEL3_TICKS;
        } else if(level == 2){
            return STACK_LEVEL2_TICKS;
        } else if(level == 1){
            return STACK_LEVEL1_TICKS;
        }
        else {
            return 0;
        }

    }

}
