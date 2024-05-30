package amirvalizadeh;

import java.awt.*;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;


public class amirvalizadeh extends Robot{

    int moveDirection = 1;

    public void run()
    {

        setBodyColor(new Color(0, 0, 0));
		setGunColor(new Color(128, 0, 128));
		setRadarColor(new Color(190, 190, 190));
		setScanColor(Color.white);
		setBulletColor(Color.red);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        while(true)
        {
            turnRadarRight(360);
        }
    }
        public void onScannedRobot(ScannedRobotEvent e)
        {
            double enemyBearing = e.getBearing();
            double enemyDistance = e.getDistance();
            double enemyAbsBearing = getHeading() + enemyBearing;
            double bearingFromGun = normalizeBearing(enemyAbsBearing - getGunHeading());

            if(bearingFromGun > 0)
            {
                turnGunRight(bearingFromGun);
            } else{
                turnGunLeft(bearingFromGun);
            }
            if(getGunHeat() == 0)
            {
                if (enemyDistance < 100)
                {
                    fire(3);
                } else if (enemyDistance < 300)
                {
                    fire(2);
                } else
                {
                    fire(1);
                }
            }
            
            turnRight(normalizeBearing(enemyBearing + 90 - (15 * moveDirection)));
            if(enemyDistance > 100)
            {
                ahead(enemyDistance - 100);
            } else {
                back(50);
            }
        }


        public void onHitByBullet(HitByBulletEvent e)
        {
            moveDirection *= -1;
            back(150 * moveDirection);
        }

        public void onHitWall(HitWallEvent e)
        {
            moveDirection *= -1;
            ahead(100 * moveDirection);
        }

        public double normalizeBearing(double angle)
        {
            angle %= 360;
            if(angle < 0)
            {
                angle += 360;
            }
            return angle;
        }

}
