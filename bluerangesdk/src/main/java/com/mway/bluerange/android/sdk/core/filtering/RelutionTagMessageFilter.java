//
//  RelutionTagMessageFilter.java
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

package com.mway.bluerange.android.sdk.core.filtering;

import com.mway.bluerange.android.sdk.core.scanning.messages.BeaconMessage;
import com.mway.bluerange.android.sdk.core.scanning.messages.RelutionTagMessageV1;
import com.mway.bluerange.android.sdk.core.streaming.BeaconMessageStreamNode;
import com.mway.bluerange.android.sdk.core.streaming.BeaconMessageStreamNodeReceiver;

import java.util.List;

/**
 * An Relution tag message filter filters iBeacon messages from a stream of beacon messages
 * delivered by all senders and sends the filtered stream to all receivers.
 */
public class RelutionTagMessageFilter extends BeaconMessageFilter {

    private long[] tags = null;

    public RelutionTagMessageFilter(BeaconMessageStreamNode senderNode) {
        super(senderNode);
    }

    public RelutionTagMessageFilter(List<BeaconMessageStreamNode> senderNodes) {
        super(senderNodes);
    }

    public RelutionTagMessageFilter(BeaconMessageStreamNode senderNode, long[] tags) {
        super(senderNode);
        this.tags = tags;
    }

    public RelutionTagMessageFilter(List<BeaconMessageStreamNode> senderNodes, long[] tags) {
        super(senderNodes);
        this.tags = tags;
    }

    @Override
    public void onReceivedMessage(BeaconMessageStreamNode senderNode, BeaconMessage message) {
        // 1. Filter by message types
        if (message instanceof RelutionTagMessageV1) {
            RelutionTagMessageV1 relutionTagMessage = (RelutionTagMessageV1) message;
            // 2. Filter tags if wanted
            if (!useTagFilter() || (useTagFilter() && messageContainsAtLeastOneMatchingTag(relutionTagMessage))) {
                for (BeaconMessageStreamNodeReceiver receiver : getReceivers()) {
                    receiver.onReceivedMessage(this, message);
                }
            }
        }
    }

    private boolean useTagFilter() {
        return tags != null;
    }

    private boolean messageContainsAtLeastOneMatchingTag(RelutionTagMessageV1 relutionTagMessage) {
        for (long filterTag : tags) {
            for (long messageTag : relutionTagMessage.getTags()) {
                if (filterTag == messageTag) {
                    return true;
                }
            }
        }
        return false;
    }
}
