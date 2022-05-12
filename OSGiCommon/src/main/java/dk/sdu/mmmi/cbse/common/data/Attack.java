package dk.sdu.mmmi.cbse.common.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attack {

    private int attackTimeMs;
    private int attackNumber;

    public Attack(int attackTimeMs, int attackNumber){
        this.attackTimeMs = attackTimeMs;
        this.attackNumber = attackNumber;
    }
}
