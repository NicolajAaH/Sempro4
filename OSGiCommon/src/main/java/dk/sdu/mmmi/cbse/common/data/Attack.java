package dk.sdu.mmmi.cbse.common.data;

public class Attack {

    private int attackTimeMs;
    private int attackNumber;

    public Attack(int attackTimeMs, int attackNumber){
        this.attackTimeMs = attackTimeMs;
        this.attackNumber = attackNumber;
    }

    public int getAttackTimeMs(){
        return attackTimeMs;
    }

    public int getAttackNumber(){
        return attackNumber;
    }
    
}
