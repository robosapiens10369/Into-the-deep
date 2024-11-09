package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous (group = "Autonomous", name = "Autonomous_Left")

public class Autonomous_Left extends Robot_DriveBase{
    @Override
    public void runOpMode() throws InterruptedException {
        double drive_power = 1;
        double drive_time = 1;
        int drive_distance = 10;
        boolean run_flag = true;
        initHardware();
        initencoder();

        waitForStart();

        while (opModeIsActive() && run_flag) {
            //move_forward(drive_power,drive_time);
            //move_backward(drive_power,drive_time);
            //turn_left(drive_power,drive_time);
            //turn_right(drive_power,drive_time);
            move_backward_using_encoder(drive_power,drive_distance);
            stop();
            sleep(5000);
            run_flag=false;
        }
    }
}
