package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Arm Test")
@Config

public class ArmTest extends LinearOpMode {

    public static int TEST_LEVEL = 2;

    boolean elevatorThreadWorking = true;
    @Override
    public void runOpMode() throws InterruptedException {

        Arm arm = new Arm(hardwareMap);

        waitForStart();

        for (int i = 0; i < 4; i++){
            arm.goHome();

            sleep(1000);

            arm.move(0.7);

            sleep(1000);
        }

    }
}
