package pers.mingshan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，
 * 复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
 * 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
 * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 * 
 * @author mingshan
 *
 */
public class CopyOnWriteArrayListTest {
    private static final int THREAD_POOL_MAX_NUM = 10;
    private List<String> mList = new CopyOnWriteArrayList<String>();

    public static void main(String args[]){
            new CopyOnWriteArrayListTest().start();
    } 

    private void initData() {  
        for(int i = 0 ; i <= THREAD_POOL_MAX_NUM; i++){
            this.mList.add("...... Line " + ( i + 1) + " ......");
        }
    }

    private void start(){
        initData();

        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM);  
        for(int i = 0 ; i < THREAD_POOL_MAX_NUM ; i ++){  
            service.execute(new ListReader(this.mList));  
            service.execute(new ListWriter(this.mList,i));  
        }  
        service.shutdown();  
    }  

    private class ListReader implements Runnable{
        private List<String> mList ;
        public ListReader(List<String> list) {
            this.mList = list;
        }

        @Override
        public void run() {
             if (this.mList != null) {
                for (String str : this.mList) {
                 System.out.println(Thread.currentThread().getName() + " : " + str);
                }
             }
        }
    }

    private class ListWriter implements Runnable{
        private List<String> mList ;
        private int mIndex;
        public ListWriter(List<String> list, int index) {
            this.mList = list;
            this.mIndex = index;
        }

        @Override
        public void run() {
            if (this.mList != null) {
                    //this.mList.remove(this.mIndex);
                     this.mList.add("...... add " + mIndex +" ......");
             }
        }
    }
}