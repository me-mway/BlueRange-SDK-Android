//
//  SimpleMovingAverageFilter.java
//  BlueRangeSDK
//
// Copyright (c) 2016-2017, M-Way Solutions GmbH
// All rights reserved.
//
// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//

package com.mway.bluerange.android.sdk.core.aggregating.averaging;

import java.util.List;

/**
 * This class implements the {@link MovingAverageFilter} interface and computes the average value
 * by just calculating the average value of all values without considering their time points.
 */
public class SimpleMovingAverageFilter implements MovingAverageFilter {
    @Override
    public float getAverage(long startTime, long endTime, List<Long> timePoints, List<Float> values) {
        float averageValue = 0.0f;
        for (float value : values) {
            averageValue += value;
        }
        return averageValue / values.size();
    }
}
