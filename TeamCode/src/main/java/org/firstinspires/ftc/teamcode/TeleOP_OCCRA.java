package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class TeleOP_OCCRA extends OpMode {
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor ELEVATOR = null;
    DcMotor Slider = null;
    DcMotor elevator = null;


    Servo Arm = null;
    Servo Grabber = null;
    Servo LeftEncoder = null;
    Servo RightEncoder = null;
    Servo RCG = null;
    Servo LCG = null;

    DistanceSensor SliderDistance = null;


    double SliderSpeed = 0;
    double ArmPos = 0;
    double GrabberPos = 0;
    double Drivepower = 2;
    double Sliderdistance = 0;
    double SliderLimitFront = 0;
    double ElevatorPower = 0;
    double LCGPos =  0.9036276;
    double RCGPos =  0.34348196;

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

        ELEVATOR = hardwareMap.dcMotor.get("elevator");
        ELEVATOR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ELEVATOR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Slider = hardwareMap.dcMotor.get("slider");
        SliderDistance = hardwareMap.get(DistanceSensor.class, "Slider Distance");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Arm = hardwareMap.get(Servo.class, "arm");

        Arm.scaleRange(0, 1);

        Grabber = hardwareMap.get(Servo.class, "grabber");

        LeftEncoder = hardwareMap.get(Servo.class, "LeftEncoder");
        RightEncoder = hardwareMap.get(Servo.class, "RightEncoder");

        LCG = hardwareMap.get(Servo.class, "LeftConeGrabber");
        RCG = hardwareMap.get(Servo.class, "RightConeGrabber");

        RightEncoder.setDirection(Servo.Direction.REVERSE);
        LeftEncoder.setPosition(1);
        RightEncoder.setPosition(1.0);
        telemetry.addLine("leftEncoder position" + LeftEncoder.getPosition());
        telemetry.addLine("RightEncoder position" + RightEncoder.getPosition());

    }

    @Override
    public void loop() {
        ELEVATOR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Sliderdistance = SliderDistance.getDistance(DistanceUnit.CM);
        Elevator elevator = new Elevator(hardwareMap);
        Drivepower = 4;
        if (gamepad1.right_trigger > 0.5){
            Drivepower = 2.5;
        }
        if (gamepad1.left_stick_button){
            Drivepower = 1.5;
        }

        telemetry.addLine("Slider is" + SliderDistance.getDistance(DistanceUnit.CM));
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

        ElevatorPower = gamepad2.right_stick_y;
        if (gamepad2.right_trigger>.5){
            ElevatorPower = ElevatorPower/2;
        }

        ELEVATOR.setPower(-ElevatorPower);
        if (gamepad2.dpad_down){
            ArmPos = 0;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_up){
            ArmPos = .673564;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_left){
            ArmPos = .321123;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_right){
            ArmPos = 1;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.left_stick_x>.1){
            ArmPos = ArmPos + gamepad2.left_stick_x/100;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.left_stick_x<-.1){
            ArmPos = ArmPos + gamepad2.left_stick_x/100;
            Arm.setPosition(ArmPos);
        }


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
        if (gamepad1.y){
            SliderLimitFront = SliderDistance.getDistance(DistanceUnit.CM);
        }
        if (gamepad1.x){
            SliderLimitFront = 0;
        }
        if (Sliderdistance <= SliderLimitFront){
            if (SliderSpeed == 1){
                SliderSpeed = 0;
            }
        }
        if (Sliderdistance < SliderLimitFront){
            Slider.setPower(-1);
        }
        if (Sliderdistance == SliderLimitFront){
            SliderSpeed = 0;
        }
        telemetry.addLine("Arm position ="+ ArmPos);
        telemetry.addLine("Slider Limit is"+ SliderLimitFront);

        Slider.setPower(SliderSpeed);

        if (ArmPos < 0){
            ArmPos = 0;
        }
        if (ArmPos > 1){
            ArmPos = 1;
        }
        if (gamepad2.b){
            LCGPos = 1.0;
            RCGPos = 0.14570603;
        }
        if (gamepad2.a){
            RCGPos = 0.34348196;
            LCGPos = 0.9036276;
        }
        LCG.setPosition(LCGPos);
        RCG.setPosition(RCGPos);
        if(gamepad2.left_bumper){
            GrabberPos = 0.01;
        }
        if (gamepad2.right_bumper){
            GrabberPos = .4;
        }
        Grabber.setPosition(GrabberPos);



        if (gamepad1.left_trigger>.5 && gamepad1.right_trigger>.5){
            elevator.goToLevel(1);
            Arm.setPosition(.533);
            while (SliderDistance.getDistance(DistanceUnit.CM) > 9.5){
                Slider.setPower(.5);

            }
            while (SliderDistance.getDistance(DistanceUnit.CM) < 9.5){
                Slider.setPower(-.5);


            }




        }


        telemetry.update();


    }

}
