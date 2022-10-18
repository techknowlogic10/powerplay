package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.Encoder;

@Autonomous
@Disabled
public class EncoderTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //Encoder leftEncoder = (Encoder) hardwareMap.get("frontLeft"); //left encoder

        Encoder leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "frontLeft"));
        leftEncoder.setDirection(Encoder.Direction.REVERSE);

        Encoder rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "frontRight"));
        Encoder backEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "backLeft"));

        waitForStart();

        while(opModeIsActive()) {
            telemetry.log().add("leftEncoder position = " + leftEncoder.getCurrentPosition());
            //telemetry.log().add("rightEncoder position = " + rightEncoder.getCurrentPosition());
            //telemetry.log().add("backEncoder position = " + backEncoder.getCurrentPosition());
            sleep(1000);
        }


    }
}
