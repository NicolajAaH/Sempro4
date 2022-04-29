package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    final private List<Event> events = new CopyOnWriteArrayList<>();
    final private ArrayList<Attack> attacks = new ArrayList<>();
    private long gameStartTime;

    // Global game variables
    private int score; // total No of killed monsters
    private int money; // to buy towers, goes up when monsters are killed
    private int life; // Dereases when monsters reach end of path, 0 = gameover!


    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(long startTime){
        gameStartTime = startTime;
    }

    public List<Attack> getCurrentAttacks(){
        return attacks.stream().filter(attack -> attack.getAttackTimeMs() + gameStartTime < System.currentTimeMillis() ).collect(Collectors.toList());
    }

    public void addAttack(Attack a){
        attacks.add(a);
    }

    public void removeAttack(Attack a){
        attacks.remove(a);
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money){
        this.money += money;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public String toString(){
        return "Score = " + score + "/nLife " + life + "/nMoney " + money;
    }
}
