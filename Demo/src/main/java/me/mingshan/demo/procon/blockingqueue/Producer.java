package me.mingshan.demo.procon.blockingqueue;

/**
 * @author mingshan
 */
public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("Processing producer handle data, Current Thread: " +
                        Thread.currentThread().getName());
                buffer.add("1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
