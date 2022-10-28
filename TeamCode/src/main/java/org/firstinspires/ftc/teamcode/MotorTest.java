package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.Arrays;
import java.util.List;

@Autonomous
@Disabled
public class MotorTest extends LinearOpMode {

    List<DcMotorEx> motors;
    private DcMotorEx frontLeft, backLeft, backRight, frontRight;
    private static final boolean RUN_USING_ENCODER = true;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        motors = Arrays.asList(frontLeft, backLeft, backRight, frontRight);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        if (RUN_USING_ENCODER) {
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        // Reverse the necessary motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 5000;

        while(System.currentTimeMillis() <= endTime) {
            frontLeft.setPower(1.0);
            backLeft.setPower(1.0);
            frontRight.setPower(1.0);
            backRight.setPower(1.0);
        }

        telemetry.log().add("Front left ticks = " + frontLeft.getCurrentPosition());
        telemetry.log().add("Back left ticks = " + backLeft.getCurrentPosition());
        telemetry.log().add("Front right ticks = " + frontRight.getCurrentPosition());
        telemetry.log().add("Back right ticks = " + backRight.getCurrentPosition());

        while (!isStopRequested()) {
            sleep(1000);
        }
    }

    public void setMode(DcMotor.RunMode runMode) {
        for (DcMotorEx motor : motors) {
            motor.setMode(runMode);
        }
    }
}
