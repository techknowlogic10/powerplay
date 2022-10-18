package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class DriverOperator extends OpMode {
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor Elevator = null;
    DcMotor Slider = null;


    Servo Arm = null;
    Servo Grabber = null;


    DistanceSensor SliderDistance = null;


    double SliderSpeed = 0;
    double ArmPos = 0;
    double GrabberPos = 0;
    double Drivepower = 2;
    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft = hardwareMap.dcMotor.get("backLeft");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight = hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Elevator = hardwareMap.dcMotor.get("elevator");
        Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Slider = hardwareMap.dcMotor.get("slider");
        SliderDistance = hardwareMap.get(DistanceSensor.class, "Slider Distance");

        Arm = hardwareMap.get(Servo.class, "arm");
        Arm.scaleRange(0, 1);

        Grabber = hardwareMap.get(Servo.class, "grabber");


    }

    @Override
    public void loop() {
        Drivepower = 2;
        if (gamepad1.b){
            Drivepower = 4;
        }
        if (gamepad1.left_stick_button){
            Drivepower = 1.5;
        }
        double y = gamepad1.left_stick_y; // Remember, this is reversed!
        double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = -gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), Drivepower);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        Elevator.setPower(-gamepad2.right_stick_y);
        if (gamepad2.dpad_down){
            ArmPos = 0;
        }
        if (gamepad2.dpad_up){
            ArmPos = .673564;
        }
        if (gamepad2.dpad_left){
            ArmPos = .321123;
        }
        if (gamepad2.dpad_right){
            ArmPos = 1;
        }
        ArmPos = ArmPos + gamepad2.left_stick_x/1000;
        Arm.setPosition(ArmPos);
        if (gamepad1.right_bumper){
            SliderSpeed = -1;
        } else if (gamepad1.left_bumper) {
            SliderSpeed = 1;
        } else{
            SliderSpeed = 0;
        }



        if (gamepad1.a){
            SliderSpeed = SliderSpeed/2;
        }
        telemetry.log().add("Arm position ="+ ArmPos);

        Slider.setPower(SliderSpeed);

        if (ArmPos < 0){
            ArmPos = 0;
        }
        if (ArmPos > 1){
            ArmPos = 1;
        }


        if(gamepad2.left_bumper){
            GrabberPos = 0.01;
        }
        if (gamepad2.right_bumper){
            GrabberPos = .3;
        }
        Grabber.setPosition(GrabberPos);

        telemetry.log().add("elevator distance is "+ Elevator.getCurrentPosition());



    }

}
