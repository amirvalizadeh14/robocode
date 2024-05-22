package amirvalizadeh;

import java.awt.*;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class amirvalizadeh extends Robot{

    public void run()
    {

        setBodyColor(new Color(0, 0, 0));
		setGunColor(new Color(128, 0, 128));
		setRadarColor(new Color(190, 190, 190));
		setScanColor(Color.white);
		setBulletColor(Color.red);

        double battlefieldWidth = getBattleFieldWidth();
        double battlefieldHeight = getBattleFieldHeight();

        while(true)
        {
            double distance = Math.random() * 300;
            double angle = Math.random() * 45;
            turnRight(angle);
            ahead(distance);
            ahead(55);
            back(70);
            turnGunRight(90);

        }
    }
        public void onScannedRobot(ScannedRobotEvent e)
        {
            double distanceOfEnemy = e.getDistance();

             
            if(distanceOfEnemy > battlefieldWidth - 300)
            {
                fire(1);
            }
        }
    
}
