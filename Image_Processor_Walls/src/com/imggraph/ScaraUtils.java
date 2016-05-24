package com.imggraph;

import com.imgmodel.buildingParts.Coordinates;

/**
*
* @author Tudor
*/
public class ScaraUtils{
  
   /* Verifica daca centrul unei scari b se afla in raza cu centrul altei scari a*/
   public static boolean isInCircle(Coordinates a, Coordinates b, float radius)
   {
       
       if ( ((b.getX() - a.getX())*(b.getX() - a.getX()) + (b.getY() - a.getY())*(b.getY() - a.getY())) <= radius*radius )
           return true;
       else return false;
   }
}
