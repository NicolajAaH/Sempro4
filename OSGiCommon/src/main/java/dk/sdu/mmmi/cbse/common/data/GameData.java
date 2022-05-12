package dk.sdu.mmmi.cbse.common.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    final private ArrayList<Attack> attacks = new ArrayList<>();
    private long gameStartTime;
    private int wave;
    private int highestScore = 0;

    private boolean isPlayerDead = false;

    private String screenMessage = "";



    // Global game variables
    private int score; // total No of killed monsters
    private int money; // to buy towers, goes up when monsters are killed
    private int life; // Decreases when monsters reach end of path, 0 = gameover!


    public List<Attack> getCurrentAttacks(){
        return attacks.stream().filter(attack -> attack.getAttackTimeMs() + gameStartTime < System.currentTimeMillis() ).collect(Collectors.toList());
    }

    public void addAttack(Attack a){
        attacks.add(a);
    }

    public void removeAttack(Attack a){
        attacks.remove(a);
    }

    public void addMoney(int money){
        this.money += money;
    }

    @Override
    public String toString(){
        return "Score = " + score + "/nLife " + life + "/nMoney " + money;
    }

    public boolean isPlayerDead() {
        return isPlayerDead;
    }

}
