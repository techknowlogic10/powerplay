package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Elevator Test")
@Config

public class ElevatorTest extends LinearOpMode {

    public static int TEST_LEVEL = 2;

    boolean elevatorThreadWorking = true;
    @Override
    public void runOpMode() throws InterruptedException {

        Elevator elevator = new Elevator(hardwareMap);
        waitForStart();

        elevator.goToStackPickup(   1);
        elevator.holdElevator(3000);

    }
}
