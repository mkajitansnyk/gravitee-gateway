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
package io.gravitee.rest.api.service.cockpit.services;

import io.gravitee.rest.api.model.api.ApiEntityResult;
import io.gravitee.rest.api.service.cockpit.model.DeploymentMode;
import java.util.List;

/**
 * @author Julien GIOVARESCO (julien.giovaresco at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface ApiServiceCockpit {
    ApiEntityResult createApi(
        String apiId,
        String userId,
        String swaggerDefinition,
        String environmentId,
        DeploymentMode mode,
        List<String> labels
    );
    ApiEntityResult updateApi(
        String apiId,
        String userId,
        String swaggerDefinition,
        String environmentId,
        DeploymentMode mode,
        List<String> labels
    );
}
