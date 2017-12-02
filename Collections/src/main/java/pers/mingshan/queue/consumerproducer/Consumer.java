package pers.mingshan.queue.consumerproducer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者
 * @author mingshan
 *
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;
    private static final int SLEEPTIME = 1000;
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("start Consumner id --> " + Thread.currentThread().getId());
        Random r = new Random();
        try {
            while(true) {
                PCData data = (PCData) queue.take();
                if (data != null) {
                    int re = data.getData() * data.getData();
                    logger.info(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
