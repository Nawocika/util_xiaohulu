package com.thread.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 */
public class Disruptor_Example {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        /**
         * 创建 Disruptor 对象。
         Disruptor 类是 Disruptor 项目的核心类，另一个核心类之一是 RingBuffer。
         如果把 Disruptor 比作计算机的 cpu ，作为调度中心的话，那么 RingBuffer ，就是计算机的 Memory 。
         第一个参数，是一个 EventFactory 对象，它负责创建 ValueEvent 对象，并填充到 RingBuffer 中；
         第二个参数，指定 RingBuffer 的大小。这个参数应该是2的幂，否则程序会抛出异常：
         第三个参数，就是之前创建的 ExecutorService 对象。
         */
        Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, 4, exec, ProducerType.MULTI, new TimeoutBlockingWaitStrategy(1000, TimeUnit.MINUTES));

        /*
        通过实现 EventHandler 接口，创建了一个 EventHandler 对象，用来处理消费者拿到的消息
         */
        EventHandler<ValueEvent> handler1 = new EventHandler<ValueEvent>() {
            // event will eventually be recycled by the Disruptor after it wraps
            public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                System.out.println("handler1:  Sequence: " + sequence + "   ValueEvent: " + event.getValue());
            }
        };
        /**
         * 创建另一个 EventHandler 对象，多消费者情况下，需要创建多个 EventHandler。 EventHandler 对象和消费者一一对应。
         */
        final EventHandler<ValueEvent> handler2 = new EventHandler<ValueEvent>() {
            // event will eventually be recycled by the Disruptor after it wraps
            public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                System.out.println("handler2:  Sequence: " + sequence + "   ValueEvent: " + event.getValue());
            }
        };

        /**
         * 将 EventHandler 对象传入 Disruptor ，Disruptor 依据 EventHandler  参数个数，创建相等数量消费者对象。
         */
        disruptor.handleEventsWith(handler1, handler2);
        //disruptor.handleEventsWith(handler1);
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        int bufferSize = ringBuffer.getBufferSize();
        System.out.println("bufferSize =  " + bufferSize);

        /**
         * 生产者线程（main线程）通过 next 方法，获取 RingBuffer 可写入的消息索引号 seq；
         通过 seq 检索消息；
         修改消息的 value 属性；
         通过 publish 方法，告知消费者线程，当前索引位置的消息可被消费了
         */
        for (long i = 0; i < 10; i++) {
            long seq = ringBuffer.next();
            try {
                String uuid = String.valueOf(i * i);
                ValueEvent valueEvent = ringBuffer.get(seq);
                valueEvent.setValue(uuid);
            } finally {
                ringBuffer.publish(seq);
            }
        }
        //停止 Disruptor系统（停止消费者线程）
        disruptor.shutdown();
        exec.shutdown();
    }
}
