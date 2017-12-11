/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import resources.Assets;
import utility.Sprite;

/**
 *
 * @author fabcol
 */
public class Bullet extends Sprite{

	public Bullet(double x, double y){
		super();
		super.setImage(Assets.bullet);
                super.setPosition(x, y);
        }
        
}