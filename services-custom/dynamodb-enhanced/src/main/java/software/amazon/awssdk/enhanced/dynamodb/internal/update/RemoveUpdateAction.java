/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.enhanced.dynamodb.internal.update;

import static software.amazon.awssdk.enhanced.dynamodb.model.UpdateExpression.keyRef;
import static software.amazon.awssdk.enhanced.dynamodb.model.UpdateExpression.listKeyRef;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class RemoveUpdateAction implements UpdateAction {

    private final String actionExpression;
    private final Map<String, String> expressionNames;
    private final String attributeName;

    public RemoveUpdateAction(BuilderImpl builder) {
        this.actionExpression = builder.actionExpression;
        this.expressionNames = builder.expressionNames == null ? Collections.emptyMap() : builder.expressionNames;
        this.attributeName = builder.attributeName;
    }

    @Override
    public UpdateActionType type() {
        return UpdateActionType.REMOVE;
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public static UpdateAction remove(String attributeName) {
        return builder().attributeName(attributeName)
                        .expression(keyRef(attributeName))
                        .expressionNames(Collections.singletonMap(keyRef(attributeName), attributeName))
                        .build();
    }

    public static UpdateAction removeFromList(String attributeName, int index) {
        return builder().attributeName(attributeName)
                        .expression(listKeyRef(attributeName, index))
                        .expressionNames(Collections.singletonMap(keyRef(attributeName), attributeName))
                        .build();
    }

    @Override
    public String expression() {
        return actionExpression;
    }

    @Override
    public Map<String, String> expressionNames() {
        return expressionNames;
    }

    @Override
    public Map<String, AttributeValue> expressionValues() {
        return new HashMap<>();
    }

    @Override
    public String attributeName() {
        return attributeName;
    }

    public static class BuilderImpl implements UpdateAction.Builder {

        private String attributeName;
        private String actionExpression;
        private Map<String, String> expressionNames;

        @Override
        public BuilderImpl type(UpdateActionType updateActionType) {
            return this;
        }

        @Override
        public BuilderImpl attributeName(String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        @Override
        public BuilderImpl expression(String actionExpression) {
            this.actionExpression = actionExpression;
            return this;
        }

        @Override
        public BuilderImpl expressionNames(Map<String, String> expressionNames) {
            this.expressionNames = expressionNames;
            return this;
        }

        @Override
        public Builder expressionValues(Map<String, AttributeValue> expressionValues) {
            return this;
        }

        public RemoveUpdateAction build() {
            return new RemoveUpdateAction(this);
        }
    }
}
