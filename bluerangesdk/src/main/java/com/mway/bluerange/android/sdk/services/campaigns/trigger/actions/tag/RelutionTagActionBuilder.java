//
//  RelutionTagActionBuilder.java
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

package com.mway.bluerange.android.sdk.services.campaigns.trigger.actions.tag;

import com.mway.bluerange.android.sdk.core.scanning.messages.BeaconMessage;
import com.mway.bluerange.android.sdk.services.campaigns.trigger.RelutionActionBuilder;
import com.mway.bluerange.android.sdk.services.campaigns.trigger.RelutionAction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 *
 */
public class RelutionTagActionBuilder extends RelutionActionBuilder {
    @Override
    protected RelutionAction createActionFromJSONIfPossible(JSONObject actionObject, BeaconMessage message) throws JSONException {
        String actionType = actionObject.getString(RelutionAction.TYPE_PARAMETER);
        if (actionType.equals(RelutionTagAction.kTypeVariableVisited)) {
            String tagString = actionObject.getString(RelutionTagAction.kContentParameter);
            RelutionTagVisit tag = new RelutionTagVisit(new Date(), tagString, message.getRssi());
            RelutionTagAction action = new RelutionTagAction("",tag);
            return action;
        } else {
            return null;
        }
    }
}
