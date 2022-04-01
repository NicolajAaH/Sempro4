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

    public void setAttackNumber(int attackNumber) {
        this.attackNumber = attackNumber;
    }

    public void setAttackTimeMs(int attackTimeMs) {
        this.attackTimeMs = attackTimeMs;
    }
}
