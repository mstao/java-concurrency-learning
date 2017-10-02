package pers.mingshan.ZookeeperLock;

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
}
