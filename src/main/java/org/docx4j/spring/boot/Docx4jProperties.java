/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.docx4j.spring.boot;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Docx4jProperties.PREFIX)
public class Docx4jProperties {

	public static final String PREFIX = "docx4j";

	/**
	 * Enable Docx4j.
	 */
	private boolean enabled = false;
	/** Font Mapper. */
	private Map<String /* Font Name */, String /* Location */> fontMapper = new LinkedHashMap<String, String>();
	/** Font Fix Mapper. 解决中文乱码问题*/
	private Map<String /* Font Name */, String /* Font Alias */> fontAliasMapper = new LinkedHashMap<String, String>();
	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Map<String, String> getFontMapper() {
		return fontMapper;
	}

	public void setFontMapper(Map<String, String> fontMapper) {
		this.fontMapper = fontMapper;
	}

	public Map<String, String> getFontAliasMapper() {
		return fontAliasMapper;
	}

	public void setFontAliasMapper(Map<String, String> fontAliasMapper) {
		this.fontAliasMapper = fontAliasMapper;
	}

}
