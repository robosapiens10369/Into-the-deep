package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Robot_DriveBase extends LinearOpMode {
    private DcMotor left_front = null;
    private DcMotor left_back = null;
    private DcMotor right_front = null;
    private DcMotor right_back = null;
    private ElapsedTime runtime = new ElapsedTime();

    double Count_Per_Motor_Rev = 28;    //Rev Motor Encoder count/revolution
    double Motor_Gear_Ratio = 37.9;       //Motor Gear Ratio
    double drive_gear_reduction = 1.0;   //external gearing
    double wheel_diam = 3.5;
    double Counts_Per_Inch = (Count_Per_Motor_Rev * Motor_Gear_Ratio * drive_gear_reduction) / (wheel_diam * 3.1415);

    public Robot_DriveBase() {
        super();
    }

    public void initHardware() {
        left_front = hardwareMap.dcMotor.get("Left_Front");
        left_back = hardwareMap.dcMotor.get("Left_Back");
        right_front = hardwareMap.dcMotor.get("Right_Front");
        right_back = hardwareMap.dcMotor.get("Right_Back");

        left_front.setDirection(DcMotor.Direction.FORWARD);
        left_back.setDirection(DcMotor.Direction.REVERSE);
        right_front.setDirection(DcMotor.Direction.REVERSE);
        right_back.setDirection(DcMotor.Direction.FORWARD);

        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void initencoder(){
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       right_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stop_drive_motor() {
        left_front.setPower(0);
        left_back.setPower(0);
        right_front.setPower(0);
        right_back.setPower(0);
    }

    public void move_forward(double power,double time) {
        left_front.setPower(power);
        left_back.setPower(power);
        right_front.setPower(power);
        right_back.setPower(power);
        runtime.reset();
        while (runtime.seconds() <= time){
            telemetry.addData("PATH","Forward Movement: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData ("power",power);
            telemetry.update();
        }
        stop_drive_motor();
    }

    public void move_backward(double power , double time) {
        left_front.setPower(-power);
        left_back.setPower(-power);
        right_front.setPower(-power);
        right_back.setPower(-power);
        runtime.reset();
        while (runtime.seconds() <= time){
            telemetry.addData("PATH","backward Movement: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData ("power",power);
            telemetry.update();
        }
        stop_drive_motor();
    }

    public void turn_right(double power , double time) {
        left_front.setPower(-power);
        left_back.setPower(-power);
        right_front.setPower(power);
        right_back.setPower(power);
        runtime.reset();
        while (runtime.seconds() <= time){
            telemetry.addData("PATH","right movement: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData ("power",power);
            telemetry.update();
        }
        stop_drive_motor();
    }

    public void turn_left(double power , double time) {
        left_front.setPower(power);
        left_back.setPower(power);
        right_front.setPower(-power);
        right_back.setPower(-power);
        runtime.reset();
        while (runtime.seconds() <= time){
            telemetry.addData("PATH","left Movement: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData ("power",power);
            telemetry.update();
        }
        stop_drive_motor();
    }

    public void move_forward_using_encoder(double power, int drive_distance){
        int newleftfronttarget;
        int newleftbacktarget;
        int newrightfronttarget;
        int newrightbacktarget;

        if (opModeIsActive()){
            newleftfronttarget = left_front.getCurrentPosition() + (int)(drive_distance * Counts_Per_Inch);
            newleftbacktarget =  left_back.getCurrentPosition() + (int)(drive_distance * Counts_Per_Inch);
            newrightfronttarget =  right_front.getCurrentPosition() + (int)(drive_distance * Counts_Per_Inch);
            newrightbacktarget =  right_back.getCurrentPosition() + (int)(drive_distance * Counts_Per_Inch);

            left_front.setTargetPosition(newleftfronttarget);
            left_back.setTargetPosition(newleftbacktarget);
            right_front.setTargetPosition(newrightfronttarget);
            right_back.setTargetPosition(newrightbacktarget);

            left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            left_front.setPower(power);
            left_back.setPower(power);
            right_front.setPower(power);
            right_back.setPower(power);

            while(opModeIsActive() && left_front.isBusy() && left_back.isBusy() && right_front.isBusy() && right_back.isBusy() ){
                telemetry.addData("Actual Position", " Running at %7d :%7d :%7d :%7d",
                        right_back.getCurrentPosition(),
                        left_back.getCurrentPosition(),
                        right_front.getCurrentPosition(),
                        left_front.getCurrentPosition());
                telemetry.addData("Target Position", "Running at %7d :%7d :%7d :%7d",
                        newleftbacktarget,
                        newleftfronttarget,
                        newrightbacktarget,
                        newrightfronttarget);
                telemetry.update();
            }
        }
        stop_drive_motor();
        initencoder();
    }

public void move_backward_using_encoder(double power, int drive_distance){
    int newleftfronttarget;
    int newleftbacktarget;
    int newrightfronttarget;
    int newrightbacktarget;

    if (opModeIsActive()){
        newleftfronttarget = left_front.getCurrentPosition() + (int)(-1*drive_distance * Counts_Per_Inch);
        newleftbacktarget =  left_back.getCurrentPosition() + (int)(-1*drive_distance * Counts_Per_Inch);
        newrightfronttarget =  right_front.getCurrentPosition() + (int)(-1*drive_distance * Counts_Per_Inch);
        newrightbacktarget =  right_back.getCurrentPosition() + (int)(-1*drive_distance * Counts_Per_Inch);

        left_front.setTargetPosition(newleftfronttarget);
        left_back.setTargetPosition(newleftbacktarget);
        right_front.setTargetPosition(newrightfronttarget);
        right_back.setTargetPosition(newrightbacktarget);

        left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        runtime.reset();

        left_front.setPower(power);
        left_back.setPower(power);
        right_front.setPower(power);
        right_back.setPower(power);

        while(opModeIsActive() && left_front.isBusy() && left_back.isBusy() && right_front.isBusy() && right_back.isBusy() ){
            telemetry.addData("Actual Position", " Running at %7d :%7d :%7d :%7d",
                    right_back.getCurrentPosition(),
                    left_back.getCurrentPosition(),
                    right_front.getCurrentPosition(),
                    left_front.getCurrentPosition());
            telemetry.addData("Target Position", "Running at %7d :%7d :%7d :%7d",
                    newleftbacktarget,
                    newleftfronttarget,
                    newrightbacktarget,
                    newrightfronttarget);
            telemetry.update();
        }
    }
    stop_drive_motor();
    initencoder();
    }
}