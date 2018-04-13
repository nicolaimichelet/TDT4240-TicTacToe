package com.mygdx.game.domain;

import com.mygdx.game.powerups.Powerup;

import java.util.ArrayList;

/**
 * Created by eiriksandberg on 09.04.2018.
 */

public class Player {
    private int id;
    private ArrayList<Powerup> powerups = new ArrayList<Powerup>();

    public Player(int id, ArrayList<Powerup> powerups) {
        this.id = id;
        this.powerups = powerups;
    }

    public boolean havePowerupsAvailable(){
        if (powerups.size() > 0){
            return true;
        } else {
            return false;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public void setPowerups(ArrayList<Powerup> powerups) {
        this.powerups = powerups;
    }
}
