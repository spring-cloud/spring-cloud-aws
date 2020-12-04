/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.aws.autoconfigure.context;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.autoconfigure.context.properties.AwsRegionProperties;
import org.springframework.cloud.aws.core.region.DefaultAwsRegionProviderChainDelegate;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.cloud.aws.core.region.StaticRegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Region auto configuration, based on <a
 * href=https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html#_configuring_region>cloud.aws.region</a>
 * settings.
 *
 * @author Agim Emruli
 * @author Petromir Dzhunev
 * @author Maciej Walkowiak
 * @author Eddú Meléndez
 */
// @checkstyle:off
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsRegionProperties.class)
public class ContextRegionProviderAutoConfiguration {

	private final AwsRegionProperties properties;

	public ContextRegionProviderAutoConfiguration(AwsRegionProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public RegionProvider regionProvider() {
		if (this.properties.isStatic()) {
			return new StaticRegionProvider(this.properties.getStatic());
		}
		return new DefaultAwsRegionProviderChainDelegate();
	}

}
