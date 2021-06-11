/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.util.concurrent;

import io.netty.util.internal.UnstableApi;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default implementation which uses simple round-robin to choose next {@link EventExecutor}.
 */
@UnstableApi
public final class DefaultEventExecutorChooserFactory implements EventExecutorChooserFactory {

    public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

    private DefaultEventExecutorChooserFactory() {
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    @Override
    public EventExecutorChooser newChooser(EventExecutor[] executors) {
        if (isPowerOfTwo(executors.length)) {
            // 若参数EventExecutor[]的大小是2的幂次方, 则使用PowerOfTwoEventExecutorChooser
            return new PowerOfTwoEventExecutorChooser(executors);
        } else {
            // 其它情况则创建GenericEventExecutorChooser.
            // GenericEventExecutorChooser和PowerOfTwoEventExecutorChooser的区别，
            // 主要是next()方法的区别，通过不同的选择逻辑，高效率地快速选择事件执行器：
            return new GenericEventExecutorChooser(executors);
        }
    }

    private static final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final EventExecutor[] executors;

        PowerOfTwoEventExecutorChooser(EventExecutor[] executors) {
            this.executors = executors;
        }

        @Override
        public EventExecutor next() {
            // 这个设计专门针对EventExecutor[]数组是2的幂次方的情况, 挺巧妙, 有点像hashMap对 capacity的设计一样, 具体逻辑为：
            // 1.首先明确一点, 减运算“-”比 与运算“&”更高级, 所以先执行减法. 2的幂次方有个特点就是其二进制最高位为1, 其它位为0, 若减一后, 则变为最高位为0, 其它位为1;
            // 2.然后将下标idx与 第一步得到的结果进行求余, 就可以保证依次顺序地获取每一个下标.这点很像hashMap计算capacity的算法, 力保每个桶都能被用到, 有效避免hash冲突.
            return executors[idx.getAndIncrement() & executors.length - 1];
        }
    }

    private static final class GenericEventExecutorChooser implements EventExecutorChooser {
        // Use a 'long' counter to avoid non-round-robin behaviour at the 32-bit overflow boundary.
        // The 64-bit long solves this by placing the overflow so far into the future, that no system
        // will encounter this in practice.
        private final AtomicLong idx = new AtomicLong();
        private final EventExecutor[] executors;

        GenericEventExecutorChooser(EventExecutor[] executors) {
            this.executors = executors;
        }

        @Override
        public EventExecutor next() {
            // 很简单, 就是通过将下标idx对EventExecutor[]数组求余, 然后将idx累加一
            return executors[(int) Math.abs(idx.getAndIncrement() % executors.length)];
        }
    }
}
