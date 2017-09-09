package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 字段的原子更新<br>
 * 包括  <code>AtomicIntegerFieldUpdater<T></code><br>
 *     <code>AtomicLongFieldUpdater<T></code><br>
 *     <code>AtomicReferenceFieldUpdater<T,V></code>
 * 
 * 1. 由于是基于反射进行原子方式更新字段的值，所以 private修饰的字段不能被访问到，
 *    进而无法进行字段更新
 * 2. 字段须由volatile 关键字进行修饰
 * 3. AtomicIntegerFieldUpdater和AtomicLongFieldUpdater不能修改包装类型的字段，
 *    所以要修改Integer/Long字段需要使用AtomicReferenceFieldUpdater<T,V>
 * 
 * @author mingshan
 *
 */
public class AtomicIntegerFieldUpdaterDemo {

    class Data {
        public volatile int value1 = 1;
        volatile int value2 = 2;
        protected volatile int value3 = 3;
        private volatile int value4 = 4;
        volatile Integer value5 = 5;
    }

    private AtomicIntegerFieldUpdater<Data> getUpdater(String fileName) {
        return AtomicIntegerFieldUpdater.newUpdater(Data.class, fileName);
    }

    private AtomicReferenceFieldUpdater<Data, Integer> getUpdater2(String fileName) {
        return AtomicReferenceFieldUpdater.newUpdater(Data.class, Integer.class, fileName);
    }

    private void work() {
        Data data = new Data();
        System.out.println("value1 ====> " + getUpdater("value1").addAndGet(data, 1));
        System.out.println("value2 ====> " + getUpdater("value2").addAndGet(data, 1));
        System.out.println("value3 ====> " + getUpdater("value3").addAndGet(data, 1));
        //System.out.println("value4 ====> " + getUpdater("value4").addAndGet(data, 1));
        getUpdater2("value5").set(data, 6);
        System.out.println("value5 ====> " + getUpdater2("value5").get(data));
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterDemo demo = new AtomicIntegerFieldUpdaterDemo();
        demo.work();
    }
}
