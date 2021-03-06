/*
 * Copyright (c) 2008-2018, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.quorum.replicatedmap;

import com.hazelcast.config.Config;
import com.hazelcast.core.ReplicatedMap;
import com.hazelcast.quorum.AbstractQuorumTest;
import com.hazelcast.quorum.QuorumException;
import com.hazelcast.quorum.QuorumType;
import com.hazelcast.test.HazelcastParametersRunnerFactory;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Comparator;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(HazelcastParametersRunnerFactory.class)
@Category({QuickTest.class})
public class ReplicatedMapQuorumReadTest extends AbstractQuorumTest {

    @Parameterized.Parameter
    public static QuorumType quorumType;

    @Parameterized.Parameters(name = "quorumType:{0}")
    public static Iterable<Object[]> parameters() {
        return asList(new Object[][]{{QuorumType.READ}, {QuorumType.READ_WRITE}});
    }

    @BeforeClass
    public static void setUp() {
        initTestEnvironment(new Config(), new TestHazelcastInstanceFactory());
    }

    @AfterClass
    public static void tearDown() {
        shutdownTestEnvironment();
    }

    @Test
    public void get_successful_whenQuorumSize_met() {
        map(0).get("foo");
    }

    @Test(expected = QuorumException.class)
    public void get_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).get("foo");
    }

    @Test
    public void containsKey_successful_whenQuorumSize_met() {
        map(0).containsKey("foo");
    }

    @Test(expected = QuorumException.class)
    public void containsKey_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).containsKey("foo");
    }

    @Test
    public void containsValue_successful_whenQuorumSize_met() {
        map(0).containsValue("foo");
    }

    @Test(expected = QuorumException.class)
    public void containsValue_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).containsValue("foo");
    }

    @Test
    public void keySet_successful_whenQuorumSize_met() {
        map(0).keySet();
    }

    @Test(expected = QuorumException.class)
    public void keySet_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).keySet();
    }

    @Test
    public void values_successful_whenQuorumSize_met() {
        map(0).values();
    }

    @Test(expected = QuorumException.class)
    public void values_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).values();
    }

    @Test
    public void valuesWithComparator_successful_whenQuorumSize_met() {
        map(0).values(new Comparator() {
            @Override
            public int compare(Object left, Object right) {
                return 0;
            }
        });
    }

    @Test(expected = QuorumException.class)
    public void valuesWithComparator_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).values(new Comparator() {
            @Override
            public int compare(Object left, Object right) {
                return 0;
            }
        });
    }

    @Test
    public void entry_successful_whenQuorumSize_met() {
        map(0).entrySet();
    }

    @Test(expected = QuorumException.class)
    public void entry_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).entrySet();
    }

    @Test
    public void size_successful_whenQuorumSize_met() {
        map(0).size();
    }

    @Test(expected = QuorumException.class)
    public void size_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).size();
    }

    @Test
    public void isEmpty_successful_whenQuorumSize_met() {
        map(0).isEmpty();
    }

    @Test(expected = QuorumException.class)
    public void isEmpty_failing_whenQuorumSize_notMet() {
        // no exception expected since the data are all local anyway
        map(3).isEmpty();
    }

    protected ReplicatedMap map(int index) {
        return replmap(index, quorumType);
    }
}
