package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class SignalDetector {

    OpenCvWebcam webcam;

    // color brown 27, color green around 60 color blue 48


    private Point point1 = new Point(20,30);
    private Point point2 = new Point(40,60);

    private String elementPosition = null;

    private HardwareMap hardwareMap = null;
    private Telemetry telemetry = null;

    Mat inputInYCRCB = new Mat();
    Mat inputInCB = new Mat();

    public SignalDetector(HardwareMap hardwareMap, Telemetry telemetry, boolean monitorViewNeeded) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        if(monitorViewNeeded) {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            this.webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "IDK"), cameraMonitorViewId);
        } else {
            this.webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "IDK"));
        }

        webcam.setPipeline(new TeamShippingElementDetectorPipeline());
        webcam.setMillisecondsPermissionTimeout(2500);
    }

    public void startDetection() {

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(176, 144, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.log().add("Not able to open Camera");
            }
        });
    }

    public void stopDetection() {
        webcam.stopStreaming();
    }

    public String getElementPosition() {
        return elementPosition;
    }

    class TeamShippingElementDetectorPipeline extends OpenCvPipeline {

        @Override
        public Mat processFrame(Mat frame) {


            Imgproc.rectangle(frame, point1, point2, new Scalar(0, 0, 255), 2);
           // Imgproc.rectangle(frame, rightRectanglePoint1, rightRectanglePoint2, new Scalar(0, 255, 0), 2);

            Imgproc.cvtColor(frame, inputInYCRCB, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(inputInYCRCB, inputInCB, 1);

            Mat leftRectangleFrame = frame.submat(new Rect(point1, point2));
           // Mat rightRectangleFrame = frame.submat(new Rect(rightRectanglePoint1, rightRectanglePoint2));

            int meanColor = (int) Core.mean(leftRectangleFrame).val[0];
           // int rightRectangleMean = (int) Core.mean(rightRectangleFrame).val[0];

            telemetry.log().add("meanColor is " + meanColor);
            //telemetry.log().add("rightRectangleMean is " + rightRectangleMean);


            return frame;
        }
    }

    private final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

