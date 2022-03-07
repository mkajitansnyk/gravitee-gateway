/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.flow.policy;

/**
 * Contains all the information related to the policy
 * - the name of the policy
 * - the configuration of the policy made by the user
 * - the condition to evaluate to execute the policy
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */
public class PolicyMetadata {

    private final String name;
    private final String configuration;
    private final String condition;

    public PolicyMetadata(String name, String configuration) {
        this.name = name;
        this.configuration = configuration;
        this.condition = null;
    }

    public PolicyMetadata(String name, String configuration, String condition) {
        this.name = name;
        this.configuration = configuration;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getConfiguration() {
        return configuration;
    }

    public String getCondition() {
        return condition;
    }
}
