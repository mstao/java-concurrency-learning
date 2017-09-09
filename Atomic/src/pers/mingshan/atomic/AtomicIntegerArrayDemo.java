package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.Test;

import junit.framework.Assert;

/**
 * 测试AtomicIntegerArray
 * 其中的元素可以原子方式进行更新
 * 
 * @author mingshan
 *
 */
public class AtomicIntegerArrayDemo {

    /**
     *  AtomicIntegerArray 中的方法，与AtomicInteger api类似。
     *
     *  void set(int i, int newValue)
     *      将位置 i处的元素设置为给定值，由于为数组，所有会有数组越界问题。
     *  void lazySet(int i, int newValue)
     *      最终以原子方式更新位置i处的值
     *  int getAndSet(int i, int newValue)
     *      将位置 i的元素原子设置为给定值并返回旧值。 
     *  boolean compareAndSet(int i, int expect, int update)
     *      比较位置i处的元素值 == 期望的值， 将位置 i处的元素设置为给定的更新值。 
     *  boolean weakCompareAndSet(int i, int expect, int update)
     *  int getAndIncrement(int i)
     *      将位置i处的元素的值以原子形式加1，并返回旧值。
     *  int getAndDecrement(int i)
     *      将位置i处的元素的值以原子形式减1，并返回旧值。
     *  int getAndAdd(int i, int delta)
     *      将位置i处的元素的值以原子形式加上给定的值，并返回更新后的值。
     *  int incrementAndGet(int i)
     *      将位置i处的元素的值以原子形式加1，并返回更新后的值。
     *  int decrementAndGet(int i)
     *      将位置i处的元素的值以原子形式减1，并返回更新后的值。
     *  int addAndGet(int i, int delta)
     *      原子地将索引 i的给定值添加到元素。 
     */
    @Test
    public void test() {
        int[] arr = new int[]{1, 2, 3, 4};
        final AtomicIntegerArray array = new AtomicIntegerArray(arr);
        Assert.assertEquals(array.getAndAdd(1, 5), 2);
        Assert.assertEquals(array.get(1), 7);
    }
}
