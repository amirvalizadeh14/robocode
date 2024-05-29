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
            double distance = Math.random() * 300;
            double angle = Math.random() * 45;
            turnRight(angle);
            ahead(distance);
        }
    }
        public void onScannedRobot(ScannedRobotEvent e)
        {
            double enemyBearing = e.getBearing();
            double enemyDistance = e.getDistance();

            double myHeading = getHeading();
            double myX = getX();
            double myY = getY();
            double enemyAbsBearing = myHeading + enemyBearing;

            double enemyX = myX + enemyDistance * Math.sin(Math.toRadians(enemyAbsBearing));
            double enemyY = myY + enemyDistance * Math.cos(Math.toRadians(enemyAbsBearing));
            // ^ - polar coordinates translated to cartesian coordinates; x = r*sin(theta); y = r*cos(theta)

            double absDegree = absoluteBearing(myX, myY, enemyX, enemyY);

            double bearingFromGun = normalizeBearing(absDegree - getGunHeading());

            if(bearingFromGun > 0)
            {
                turnGunRight(bearingFromGun);
            } else{
                turnGunLeft(bearingFromGun);
            }

            
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
            double hypotenuse = Point2D.distance(x1, y1, x2, y2);
            double bearing = Math.toDegrees(Math.asin(xo / hypotenuse));

            if (xo > 0 && yo < 0) //lower right quadrant
            {
                bearing = 180 - bearing;
            } else if (xo < 0 && yo < 0) //lower left quadrant
            {
                bearing = 180 - bearing;
            } else if (xo < 0 && yo > 0) //upper left quadrant
            {
                bearing = 360 + bearing;
            } else if (xo > 0 && yo > 0) //upper right quadrant
            {
                bearing = bearing;
            }

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
