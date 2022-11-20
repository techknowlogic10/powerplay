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
        Runnable elevatorThreadForPreloadDrop = new Runnable() {
            @Override
            public void run() {
                elevator.goToLevel(2);
            }
        };

        Runnable elevatorThreadForStackPickup = new Runnable() {
            @Override
            public void run() {
                elevator.goToStackPickup(5);
                 elevatorThreadWorking = false;
            }
        };
        waitForStart();

        new Thread(elevatorThreadForPreloadDrop).start();

        sleep(1000);

        new Thread(elevatorThreadForStackPickup).start();

        while (elevatorThreadWorking){
            sleep(50);
        }

        sleep(2000);

    }
}
