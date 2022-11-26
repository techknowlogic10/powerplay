package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class DriverOperatorMain extends OpMode {
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor ELEVATOR = null;
    DcMotor Slider = null;


    Servo Arm = null;
    Servo Grabber = null;
    Servo DRS = null;
    Servo Brake = null;



    DistanceSensor SliderDistance = null;
    DistanceSensor ConeToArm = null;
    DigitalChannel ConeSensor = null;


    double SliderSpeed = 0;
    double SliderSpeedFront = 1;
    double SliderSpeedRear = -1;
    double ArmPos = 0;
    double GrabberPos = 0;
    double Drivepower = 2;
    double Sliderdistance = 0;
    double SliderLimitRear = 100;
    double ElevatorPower = 0;
    double DRS_pos = 0;

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
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Arm = hardwareMap.get(Servo.class, "arm");

        Arm.scaleRange(0, 1);

        Grabber = hardwareMap.get(Servo.class, "grabber");

        ConeSensor = hardwareMap.get(DigitalChannel.class, "cone sensor");
        SliderDistance = hardwareMap.get(DistanceSensor.class, "Slider Distance");
        ConeToArm = hardwareMap.get(DistanceSensor.class, "cone to arm sensor");

        Brake = hardwareMap.get(Servo.class, "brake");
        Brake.setPosition(0);
        // set the digital channel to input.
        ConeSensor.setMode(DigitalChannel.Mode.INPUT);

        DRS = hardwareMap.get(Servo.class, "DRS");


    }

    @Override
    public void loop() {

        telemetry.addLine("distance from arm to cone in cm is "+ ConeToArm.getDistance(DistanceUnit.CM));

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

        if (gamepad2.start){
            double position = Brake.getPosition();

            position = position + 0.01;

            Brake.setPosition(position);


        } else if (gamepad2.share){

            double position = Brake.getPosition();

            position = position - 0.01;

            Brake.setPosition(position);

        }

        telemetry.addLine("Slider is" + SliderDistance.getDistance(DistanceUnit.CM));
        telemetry.addLine("brake is" + Brake.getPosition());
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
        if (gamepad2.right_stick_y > .75){
            ElevatorPower = 1;
        }

        if (gamepad2.right_stick_y < -.75){
            ElevatorPower = -1;
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
            if (ConeSensor.getState() == false){
                    Slider.setPower(.1);
                    gamepad1.rumble(100);
            }else
                Slider.setPower(.8);
        } else if (gamepad1.left_bumper) {
            Slider.setPower(SliderSpeedRear);
        } else{
           Slider.setPower(0);
        }

        if (gamepad1.dpad_right){
            SliderSpeedFront = SliderSpeedFront + 0.05;
            SliderSpeedRear = SliderSpeedRear - 0.05;
        }

        if (gamepad1.dpad_left){
            SliderSpeedFront = SliderSpeedFront - 0.05;
            SliderSpeedRear = SliderSpeedRear + 0.05;
        }
telemetry.addLine("slider speed front is "+ SliderSpeedFront);
        telemetry.addLine("slider speed rear is "+ SliderSpeedRear);
        if (SliderSpeedFront >= 1){
            SliderSpeedFront = 1;
        }
        if (SliderSpeedFront <= 0){
            SliderSpeedFront = 0;
        }

        if (SliderSpeedRear >= 0){
            SliderSpeedRear = 0;
        }
        if (SliderSpeedRear <= -1){
            SliderSpeedRear = -1;
        }

        if (gamepad1.a){
            SliderSpeedFront = .5;
            SliderSpeedRear = -.5;
        }

        if (gamepad1.y){
            SliderLimitRear = SliderDistance.getDistance(DistanceUnit.CM);
        }
        if (gamepad1.x){
            SliderLimitRear = 0;
        }
        if (Sliderdistance >= SliderLimitRear){
            if (SliderSpeed == 1){
                SliderSpeed = 0;
            }
        }
        if (Sliderdistance > SliderLimitRear){
            Slider.setPower(-1);
        }
        if (Sliderdistance == SliderLimitRear){
            SliderSpeed = 0;
        }
        telemetry.addLine("Arm position ="+ ArmPos);
        telemetry.addLine("Slider Limit is"+ SliderLimitRear);




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
            GrabberPos = .4;
        }
        Grabber.setPosition(GrabberPos);

        if (gamepad1.dpad_up){
            DRS_pos = .5;
        }

        if (gamepad1.dpad_down){
            DRS_pos = 0;
        }

        DRS.setPosition(DRS_pos);

        telemetry.addLine("DRS position is "+DRS.getPosition());




        telemetry.update();

    }

}
