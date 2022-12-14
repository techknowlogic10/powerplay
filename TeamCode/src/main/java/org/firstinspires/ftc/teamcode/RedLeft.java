package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Left")
@Config
public class RedLeft extends BaseAutonomous {

    public static Pose2d STARTING_POSITION = new Pose2d(-37, -60, Math.toRadians(90));

    public static int JUNCTION_LEVEL = 1;

    @Override
    public Pose2d getStartingPosition() {
        return STARTING_POSITION;
    }

    @Override
    public int getJunctionLevel() {
        return JUNCTION_LEVEL;
    }
}