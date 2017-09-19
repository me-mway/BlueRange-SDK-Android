//
//  EddystoneMessageGenerator.java
//  BlueRangeSDK
//
// Copyright (c) 2016-2017, M-Way Solutions GmbH
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//     * Neither the name of the M-Way Solutions GmbH nor the
//       names of its contributors may be used to endorse or promote products
//       derived from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY M-Way Solutions GmbH ''AS IS'' AND ANY
// EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL M-Way Solutions GmbH BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//

package com.mway.bluerange.android.sdk.core.scanning.messages;

import org.altbeacon.beacon.Beacon;

import java.util.List;

public abstract class EddystoneMessageGenerator implements BeaconMessageGenerator {

    public static final int EDDYSTONE_SERVICE_UUID = 0xAAFE;

    public static int EDDY_FRAME_UID = Integer.parseInt("00000000", 2);
    public static int EDDY_FRAME_URL = Integer.parseInt("00010000", 2);
    public static int EDDY_FRAME_TLM = Integer.parseInt("00100000", 2);
    public static int EDDY_FRAME_EID = Integer.parseInt("01000000", 2);

    @Override
    public boolean isValidBeacon(Beacon beacon) {
        try {
            List<Long> dataFields = beacon.getDataFields();
            long serviceUuid = dataFields.get(0);

            if (serviceUuid != EDDYSTONE_SERVICE_UUID) {
                return false;
            }
            return true;

        } catch (Throwable t) {
            return false;
        }
    }
}
