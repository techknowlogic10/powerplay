package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class AdditionalConeDropper {

    public static int WAIT_AFTER_DROP = 100;
    public static int WAIT_BEFORE_PICKUP = 300;
    public static int WAIT_AFTER_PICKUP = 400;

    private HardwareMap hardwareMap;

    private Slider slider;
    private Elevator elevator;
    private Arm arm;
    private Grabber grabber;

    private int junctionLevel;

    Runnable sliderRunnableForStackPickup = new Runnable() {
        @Override
        public void run() {
            slider.fullyExtend();
        }
    };
    Thread sliderThreadForStackPickup = new Thread(sliderRunnableForStackPickup);

    Runnable elevatorRunnableForStackPickup = new Runnable() {
        @Override
        public void run() {
            elevator.prepareForPickup();
        }
    };
    Thread elevatorThreadForStackPickup = new Thread(elevatorRunnableForStackPickup);

    Runnable armRunnableForStackPickup = new Runnable() {
        @Override
        public void run() {
            arm.goHome();
        }
    };
    Thread armThreadForStackPickup = new Thread(armRunnableForStackPickup);

    Runnable sliderRunnableForStackDrop = new Runnable() {
        @Override
        public void run() {
            slider.goToHome();
        }
    };
    Thread sliderThreadForStackDrop = new Thread(sliderRunnableForStackDrop);

    Runnable elevatorRunnableForStackDrop = new Runnable() {
        @Override
        public void run() {
            elevator.goToLevel(junctionLevel);
        }
    };
    Thread elevatorThreadForStackDrop = new Thread(elevatorRunnableForStackDrop);

    Runnable armRunnableForStackDrop = new Runnable() {
        @Override
        public void run() {
            arm.move(RedRight.ARM_POSITION);
        }
    };
    Thread armThreadForStackDrop = new Thread(armRunnableForStackDrop);

    private int numberOfConesLeftInStack = 5;

    public AdditionalConeDropper(HardwareMap hardwareMap, int junctionLevel) {
        this.hardwareMap = hardwareMap;

        slider = new Slider(hardwareMap);
        elevator = new Elevator(hardwareMap);
        arm = new Arm(hardwareMap);
        grabber = new Grabber(hardwareMap);

        this.junctionLevel = junctionLevel;


    }

    public void pickAndDropAdditionalCone() {

        sleep(WAIT_AFTER_DROP);

        sliderThreadForStackPickup.start();
        elevatorThreadForStackPickup.start();
        armThreadForStackPickup.start();

        while(sliderThreadForStackPickup.isAlive() || elevatorThreadForStackPickup.isAlive() || armThreadForStackPickup.isAlive()) {
            sleep(50);
        }

        elevator.goToStackPickup(numberOfConesLeftInStack);

        //sleep(WAIT_BEFORE_PICKUP);

        grabber.pickup();
        numberOfConesLeftInStack--;

        sleep(WAIT_AFTER_PICKUP);

        elevatorThreadForStackDrop.start();
        sleep(300);

        armThreadForStackDrop.start();
        sliderThreadForStackDrop.start();

        while(sliderThreadForStackDrop.isAlive() || elevatorThreadForStackDrop.isAlive() || armThreadForStackDrop.isAlive()) {
            sleep(50);
        }

        elevator.dropBeforeRelease();
        grabber.release();
        elevator.liftAfterRelease();
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
