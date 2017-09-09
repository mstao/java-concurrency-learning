package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import junit.framework.Assert;

/**
 * 
 * @author mingshan
 *
 */
public class AtomicIntegerDemo {

    /**
     * AtomicInteger 拥有的的方法
     * 
     *  1. int addAndGet(int delta)
     *      以原子方式将给定的值与当前值相加，相当于线程安全版本的i = i + delta, 返回更新后的值。
     *  2. boolean compareAndSet(int expect, int update)
     *      如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。 如果成功就返回true，否则返回false，并且不修改原值。
     *  3. int decrementAndGet()
     *      以原子方式将当前值减一。
     *  4. int get()
     *      获取当前值。
     *  5. int getAndAdd(int delta)
     *      以原子方式将给定的值与当前值相加， 相当于 int t = i; i = i + delta; return t;
     *      返回旧值。
     *  6. int getAndDecrement()
     *      以原子方式将当前值减一，并返回旧值。
     *  7. int getAndIncrement()
     *      以原子方式将当前值加一， 并返回旧值。
     *  8. int getAndSet(int newValue)
     *      以原子方式设置为给定的值， 并返回旧值， 相当于int t = i; i = newValue; return t;
     *  9. int incrementAndGet()
     *      以原子方式将当前值加 1。 相当于线程安全版本的++i操作。
     *  10. void lazySet(int newValue)
     *      最终设定为给定值。
     *  11. void set(int newValue)
     *      设置为给定值。 直接修改原始值，也就是i=newValue操作。
     *  12. boolean weakCompareAndSet(int expect, int update)
     *      如果当前值 ==为预期值，则将值设置为给定更新值。 
     */
    @Test
    public void testAtomicInteger() {
        final AtomicInteger incrementNum = new AtomicInteger();

        System.out.println(incrementNum.incrementAndGet());
        Assert.assertEquals(incrementNum.compareAndSet(1, 2), true);
        Assert.assertEquals(incrementNum.get(), 2);
        Assert.assertEquals(incrementNum.decrementAndGet(), 1);
        incrementNum.set(1);
        Assert.assertEquals(incrementNum.getAndIncrement(), 1);
    }

}
