package controller;

public class MaxMap {
    private static volatile MaxMap instance;
    private static final int maxMap = 10;

    private MaxMap() {
    }

    public static MaxMap getInstance() {
        if (instance == null) {
            synchronized (MaxMap.class) {

                if (instance == null) {
                    instance = new MaxMap();
                }
            }
        }

        return instance;
    }

    public int getMaxMap() {
        return maxMap;
    }
}
