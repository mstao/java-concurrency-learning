package me.mingshan.juc.demo.procon.sync;

public class Consumer implements Runnable {
    private Resource resource;

    public Consumer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                resource.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
