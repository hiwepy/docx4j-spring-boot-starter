/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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

import org.docx4j.template.Docx4jConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Docx4jDocxTemplateProperties.PREFIX)
public class Docx4jDocxTemplateProperties {

	public static final String PREFIX = "docx4j.template.docx";

	/**
	 * 变量占位符开始位，默认: ${
	 */
	private String placeholderStart = "${";
	/**
	 * 变量占位符结束位，默认: }
	 */
	private String placeholderEnd = "}";
	/**
	 * 模板文档读取编码，默认: UTF-8
	 */
	private String inputEncoding = Docx4jConstants.DEFAULT_CHARSETNAME;
	/**
	 * 渲染后的文档输出编码，默认: UTF-8
	 */
	private String outputEncoding = Docx4jConstants.DEFAULT_CHARSETNAME;
	/**
	 * 是否自动删除模板文件
	 */
	private boolean autoDelete = false;

	public String getPlaceholderStart() {
		return placeholderStart;
	}

	public void setPlaceholderStart(String placeholderStart) {
		this.placeholderStart = placeholderStart;
	}

	public String getPlaceholderEnd() {
		return placeholderEnd;
	}

	public void setPlaceholderEnd(String placeholderEnd) {
		this.placeholderEnd = placeholderEnd;
	}

	public String getInputEncoding() {
		return inputEncoding;
	}

	public void setInputEncoding(String inputEncoding) {
		this.inputEncoding = inputEncoding;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}

	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

}
