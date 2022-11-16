package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Config

public class ElevatorTest extends LinearOpMode {

    public static int TEST_LEVEL = 2;

    @Override
    public void runOpMode() throws InterruptedException {

        Elevator elevator = new Elevator(hardwareMap);

        waitForStart();

        elevator.goToLevel(TEST_LEVEL);

        sleep(1000);

        elevator.goToStackPickup(5);


    }
}
