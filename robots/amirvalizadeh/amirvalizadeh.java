package amirvalizadeh;

import java.awt.*;
import java.awt.geom.Point2D;

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
            //double distance = Math.random() * 300;
            //double angle = Math.random() * 45;
            //turnRight(angle);
            //ahead(distance);
        }
    }
        public void onScannedRobot(ScannedRobotEvent e)
        {
            double enemyBearing = e.getBearing();
            double enemyDistance = e.getDistance();
            double enemyAbsBearing = getHeading() + enemyBearing;
            double bearingFromGun = normalizeBearing(enemyAbsBearing - getGunHeading());

            if(Math.abs(bearingFromGun) <= 3)
            {
                turnGunRight(bearingFromGun);
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
            } else{
                turnGunRight(bearingFromGun);
            }
            
            turnRight(normalizeBearing(enemyBearing + 90 - (15 * moveDirection)));
            if (enemyDistance > 100)
            {
                ahead((enemyDistance - 100) * moveDirection);
            } else {
                back(50 * moveDirection);
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
        
        
        public double absoluteBearing(double x1, double y1, double x2, double y2)
        {
            double xo = x2 - x1;
            double yo = y2 - y1;
            //double hypotenuse = Point2D.distance(x1, y1, x2, y2);
            double bearing = Math.toDegrees(Math.atan2(xo, yo));

            return bearing;
        }
        

        public double normalizeBearing(double angle)
        {
            if (angle > 180)
            {
                angle -= 360;
            } else if(angle < 180)
            {
                angle += 360;
            }

            return angle;
        }

}
