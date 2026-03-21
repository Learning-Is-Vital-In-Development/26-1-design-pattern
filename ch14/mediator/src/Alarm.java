public class Alarm extends Colleague {

    public void ring() {
        System.out.println("[알람] 알람이 울렸습니다!");
        mediator.notify(this, "alarm");
    }
}
