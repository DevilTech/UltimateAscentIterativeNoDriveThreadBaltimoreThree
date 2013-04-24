package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter 
{
    CANJaguar shoot;
    boolean hasSeenMax = false;
    final double aCHigh = 25;
    final double aCLow  = 19;
    boolean atMax;
    
    public Shooter(int port)
    {
        try {
            shoot = new CANJaguar(port);
            shoot.setVoltageRampRate(Wiring.RAMP_VOLTS_PER_SECOND);
        } catch (CANTimeoutException ex) {
            SmartDashboard.putString("Error Table Flip", "CAN TIMEOUT");
            ex.printStackTrace();
        }
    }
    
    public void shoot()
    {
        //System.out.println("Shooting");
        try 
        {
            shoot.setX(1.0);

        }
        catch (CANTimeoutException ex)
        {
            SmartDashboard.putString("Error Table Flip", "CAN TIMEOUT");
            ex.printStackTrace();
        }
        try 
        {
            if(hasSeenMax && shoot.getOutputCurrent() < aCLow)
            {
                SmartDashboard.putBoolean("Shooter Up To Speed", true);
                atMax = true;
            }
            else if (shoot.getOutputCurrent() > aCHigh)
            {
                hasSeenMax = true;
            }
        } 
        catch (CANTimeoutException ex) {
            SmartDashboard.putString("Error Table Flip", "CAN TIMEOUT");
            ex.printStackTrace();
        }
    }
    
    public void stop()
    {
        try {
            shoot.setX(0);
            hasSeenMax = false;
            atMax = false;
            SmartDashboard.putBoolean("Shooter Up To Speed", false);
        } catch (CANTimeoutException ex) {
            SmartDashboard.putString("Error Table Flip", "CAN TIMEOUT");
            ex.printStackTrace();
        }
    }
    public boolean atSpeed(){
        if(!hasSeenMax){
            atMax = false;
        }
        return atMax;
    }
}
