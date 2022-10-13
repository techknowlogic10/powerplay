package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Config
public class AprilTagSignalDetectorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagSignalDetector detector = new AprilTagSignalDetector(hardwareMap, telemetry, true);

        //Detection continue to happen throughout init
        detector.startDetection();

        waitForStart();

        long start = System.currentTimeMillis();

        //As detection continue to happen since init, we can stop detection (stop streaming)
        detector.stopDetection();

        int signalPosition = detector.getSignalPosition();
        telemetry.log().add("signal position is " + signalPosition);

        long end = System.currentTimeMillis();

        long elapsed = end - start;

        telemetry.log().add("Time took to scan " + elapsed);

        telemetry.log().add("Signal is in " + signalPosition);

        while (opModeIsActive()) {
            sleep(20);
        }
    }
}
