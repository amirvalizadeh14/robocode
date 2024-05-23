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
        

        while(true)
        {
            turnRadarRight(Double.POSITIVE_INFINITY);
            double distance = Math.random() * 300;
            double angle = Math.random() * 45;
            turnRight(angle);
            ahead(distance);
        }
    }
        public void onScannedRobot(ScannedRobotEvent e)
        {
            double enemyAbsBearing = e.getBearingRadians() + e.getHeadingRadians();
            turnRight(enemyAbsBearing + 90);

            turnGunRight(enemyAbsBearing);

            double distanceOfEnemy = e.getDistance();

            if(distanceOfEnemy < 200)
            {
                fire(3);
            } else if(distanceOfEnemy < 500){
                fire(1.5);
            } else{
                fire(1);
            }
        }

        public void onHitByBullet(HitByBulletEvent e)
        {
            back(90);
        }

        public void onHitWall(HitWallEvent e)
        {
            moveDirection *= -1;
            ahead(100 * moveDirection);
        }
    

}
