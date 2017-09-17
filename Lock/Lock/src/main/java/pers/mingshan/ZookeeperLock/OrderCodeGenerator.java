package pers.mingshan.zookeeperlock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成器
 * @author mingshan
 *
 */
public class OrderCodeGenerator {
    private int i = 0;

    public String getOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss - ");
        Date date = new Date();
        sdf.format(date);
        return sdf.format(date) + ++i;
    }

    public static void main(String[] args) {
        OrderCodeGenerator generator = new OrderCodeGenerator();
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.getOrderCode());
        }
    }
}
