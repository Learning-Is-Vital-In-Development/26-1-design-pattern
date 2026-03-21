public class Calendar extends Colleague {

    private final String dayOfWeek;

    public Calendar(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isWeekend() {
        return dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일");
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void checkDay() {
        System.out.println("[캘린더] 오늘은 " + dayOfWeek + "입니다. (주말: " + isWeekend() + ")");
    }
}
