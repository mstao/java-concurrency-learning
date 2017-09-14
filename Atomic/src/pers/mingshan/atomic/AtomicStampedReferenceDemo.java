package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用版本号解决CAS中的ABA问题。
 * 每一次修改都记录下版本号, 此版本号+1.
 * @author mingshan
 *
 */
public class AtomicStampedReferenceDemo {
    private static AtomicStampedReference<String> ref = new AtomicStampedReference<String>("abc", 0);

    public static void main(String[] args) throws InterruptedException {
        final int stamp = ref.getStamp();
        final String reference = ref.getReference();

        System.out.println(stamp + " <------> " + reference);
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(stamp + " <------> " + ref.getReference()
                        + "---" + ref.compareAndSet(reference, "abc2", stamp, stamp + 1));
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println(stamp + " <------> " + ref.getReference()
                        + "---" +  ref.compareAndSet(reference, "abc2", stamp, stamp + 1));
            }
        });

        thread1.start();
        thread1.join();

        thread2.start();
        thread2.join();

        System.out.println("--OVER--");
    }

}
