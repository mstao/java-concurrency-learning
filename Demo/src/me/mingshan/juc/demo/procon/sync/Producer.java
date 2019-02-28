package me.mingshan.juc.demo.procon.sync;

public class Producer implements Runnable {
    private Resource resource;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                resource.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
