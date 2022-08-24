package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous
public class FirstProgram extends LinearOpMode {

    @Override
    public void runOpMode() {
        waitForStart();
        telemetry.log().add("first program");
        sleep(3000);

    }
}
