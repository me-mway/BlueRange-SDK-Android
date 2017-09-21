//
//  RelutionTagMessage.java
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

import android.annotation.SuppressLint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mway.bluerange.android.sdk.utils.string.ByteArrayConverter;

import org.altbeacon.beacon.Beacon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value={ "tags" }, allowGetters=true)
@JsonTypeName("RelutionTagMessage")
public class RelutionTagMessage extends EddystoneUidMessage {

    // Default constructor necessary for JSON deserialization
    public RelutionTagMessage() {}

    public RelutionTagMessage(String namespaceUid, String instanceId) {
        super(namespaceUid, instanceId);
    }

    public RelutionTagMessage(Beacon beacon) {
        super(beacon);
    }

    public RelutionTagMessage(String namespaceUid, int[] tags) throws Exception {
        this(namespaceUid, getInstanceIdFromTags(tags));
    }

    static String getInstanceIdFromTags(int[] tags) throws Exception {
        byte[] bytes = getBytesFromTags(tags);
        String identifier = getHexString(bytes);
        return identifier;
    }

    private static byte[] getBytesFromTags(int[] tags) throws Exception {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (int i = 0; i < tags.length; ++i) {
                dos.writeShort(tags[i]);
            }
            byte[] tmp = baos.toByteArray();
            byte[] result = new byte[6];

            for(int i=0; i < tmp.length; i++){
                result[i] = tmp[i];
            }

            return result;
        } catch(Exception e){
            throw new Exception("Tag list cannot be converted "+e.getMessage());
        }
    }

    private static String getHexString(byte[] raw) {
        final String HEXES = "0123456789ABCDEF";
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public List<Integer> getTags() {
        return getTagsFromInstanceId(this.instanceId);
    }

    @SuppressLint("DefaultLocale")
    static List<Integer> getTagsFromInstanceId(String instanceId) {
        String instance = instanceId.toUpperCase();
        byte[] bytes = getBytesFromInstance(instance);
        List<Integer> tags = getTagsFromBytes(bytes);
        return tags;
    }

    private static byte[] getBytesFromInstance(String instanceId) {
        return ByteArrayConverter.hexStringToByteArray(instanceId);
    }

    private static List<Integer> getTagsFromBytes(byte[] bytes) {
        List<Integer> tags = new ArrayList<>();
        for (int i = 0;i < bytes.length; i+=2) {
            int b1 = getUnsignedByte(bytes[i+0]);
            int b2 = getUnsignedByte(bytes[i+1]);
            int tag = (b1 << 8) + (b2 << 0);
            // Tag 0 is interpreted as padding.
            if (tag != 0) {
                tags.add(tag);
            }
        }
        return tags;
    }

    public static int getUnsignedByte(byte b) {
        return b & 0xFF;
    }

    @Override
    protected BeaconMessage copy() {
        return new RelutionTagMessage(this.namespaceUid, this.instanceId);
    }

    @Override
    protected String getDescription() {
        String str = "";
        List<Integer> relutionTags = this.getTags();
        String outputString = "RelutionTagMessageV2: tags = ";
        for (int i = 0;i < relutionTags.size();i++) {
            if (i != 0) {
                outputString += ", ";
            }
            outputString += relutionTags.get(i);
        }
        return str + outputString;
    }
}
