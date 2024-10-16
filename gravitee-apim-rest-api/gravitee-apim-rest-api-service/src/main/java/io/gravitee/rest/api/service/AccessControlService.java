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
package io.gravitee.rest.api.service;

import io.gravitee.rest.api.model.PageEntity;
import io.gravitee.rest.api.model.api.ApiEntity;

/**
 * @author Guillaume Cusnieux (guillaume.cusnieux at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface AccessControlService {
    boolean canAccessApiFromPortal(String apiId);

    boolean canAccessApiFromPortal(ApiEntity apiEntity);

    boolean canAccessPageFromPortal(final String environmentId, PageEntity pageEntity);

    boolean canAccessPageFromPortal(final String environmentId, String apiId, PageEntity pageEntity);

    boolean canAccessPageFromConsole(final String environmentId, ApiEntity apiEntity, PageEntity pageEntity);
}
