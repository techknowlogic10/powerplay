package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Config

public class SliderTest extends LinearOpMode {

    public static int TEST_LEVEL = 2;
    public static int ITERATIONS = 4;

    boolean sliderThreadWorking = true;
    @Override
    public void runOpMode() throws InterruptedException {

        Slider slider = new Slider(hardwareMap);
        Runnable sliderThreadForStackPickup = new Runnable() {
            @Override
            public void run() {
                slider.fullyExtend();
                sliderThreadWorking = false;
            }
        };
        Runnable sliderThreadForStackDrop = new Runnable() {
            @Override
            public void run() {
                slider.goToHome();
                sliderThreadWorking = false;
            }
        };

        waitForStart();
        for (int i = 0; i < ITERATIONS; i ++){
            new Thread(sliderThreadForStackPickup).start();

            while (sliderThreadWorking){
                sleep(50);
            }

            new Thread(sliderThreadForStackDrop).start();


            while (sliderThreadWorking){
                sleep(50);
            }

            sleep(2000);

        }

    }
}
