package pers.mingshan.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新引用类型
 * @author mingshan
 *
 */
public class AtomicReferenceDemo {
    private AtomicReference<User> userRef = new AtomicReference<User>();

    private void work() {
        User user = new User();
        user.setUserName("han");
        user.setAge(21);
        userRef.set(user);
        System.out.println("user ===> " + userRef.get());
    }

    public static void main(String[] args) {
        AtomicReferenceDemo demo = new AtomicReferenceDemo();
        demo.work();
    }

    static class User {
        private String userName;
        private int age;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User [userName=" + userName + ", age=" + age + "]";
        }

    }
}
