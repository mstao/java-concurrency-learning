package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 根据一个boolean标记记录值是否更改， 这个标记在true和false之间切换， 并不能
 * 记录这个值被修改过多少次，并不能彻底解决CAS的ABA问题。
 * @author mingshan
 *
 */
public class AtomicMarkableReferenceDemo {
    private static AtomicMarkableReference<String> ref = new AtomicMarkableReference<String>("abc", false);

    public static void main(String[] args) throws InterruptedException {
        String reference = ref.getReference();
        boolean mark = ref.isMarked();
        System.out.println("refernece ==> " + reference);
        System.out.println("mark ==> " + mark);

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println(mark + " <------> " + ref.getReference()
                + "---" + ref.compareAndSet(reference, "abc2", false, true));
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                System.out.println(mark + " <------> " + ref.getReference()
                + "---" + ref.compareAndSet(reference, "abc2", false, true));
            }
        };

        thread1.start();
        thread1.join();

        thread2.start();
        thread2.join();

        System.out.println("--OVER--");
    }
}
