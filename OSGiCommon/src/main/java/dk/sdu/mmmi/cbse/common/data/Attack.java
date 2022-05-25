package dk.sdu.mmmi.cbse.common.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attack {

    private int attackTimeMs; // when to launch the attack compared to game start
    private int attackNumber; // number of enemies in an attack

    public Attack(int attackTimeMs, int attackNumber){
        this.attackTimeMs = attackTimeMs;
        this.attackNumber = attackNumber;
    }
}
