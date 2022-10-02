package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MacnumTeleOp extends OpMode {

    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor elevator = null;
    DcMotor slider = null;
    Servo arm = null;
    Servo grabber = null;
    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");

        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontRight = hardwareMap.dcMotor.get("frontRight");

        slider = hardwareMap.dcMotor.get("slider");

        backRight = hardwareMap.dcMotor.get("backRight");

        elevator = hardwareMap.dcMotor.get("elevator");

        arm = hardwareMap.servo.get("arm");

        grabber = hardwareMap.servo.get("grabber");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {

        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx =gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 2);;
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        double elevatorPower = gamepad2.right_stick_y;
        double armPower = 0.1;

        elevator.setPower(elevatorPower);

        //slider code

        if (gamepad1.right_bumper) {
            slider.setPower(1);
        }
        if (gamepad1.left_bumper) {
            slider.setPower(-1);
        }
        slider.setPower(0);

        //arm code

        if (gamepad2.dpad_right) {
            if(armPower < 1) {
                armPower = armPower + 0.05;
                arm.setPosition(armPower);
            }
        }
        if (gamepad2.dpad_left) {
            if(armPower > 0) {
                armPower = armPower - 0.05;
                arm.setPosition(armPower);
            }
        }

        //grabber code

        if (gamepad2.right_bumper) {
            grabber.setPosition(0.3);
        }
        if (gamepad2.left_bumper) {
            grabber.setPosition(0.01);
        }


        telemetry.log().add("arm position is" + arm.getPosition());

    }
}