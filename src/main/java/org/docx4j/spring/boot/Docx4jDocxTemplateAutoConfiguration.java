package org.docx4j.spring.boot;

import java.io.IOException;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.ConversionHTMLScriptElementHandler;
import org.docx4j.convert.out.ConversionHTMLStyleElementHandler;
import org.docx4j.convert.out.ConversionHyperlinkHandler;
import org.docx4j.template.WordprocessingMLDocxTemplate;
import org.docx4j.template.handler.OutputConversionHTMLScriptElementHandler;
import org.docx4j.template.handler.OutputConversionHTMLStyleElementHandler;
import org.docx4j.template.handler.OutputConversionHyperlinkHandler;
import org.docx4j.template.io.WordprocessingMLPackageExtractor;
import org.docx4j.template.io.WordprocessingMLPackageWriter;
import org.docx4j.template.io.WordprocessingMLTemplateWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(Docx4jAutoConfiguration.class)
@ConditionalOnClass({ Docx4J.class, WordprocessingMLDocxTemplate.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jDocxTemplateProperties.class })
public class Docx4jDocxTemplateAutoConfiguration {

	protected static Logger LOG = LoggerFactory.getLogger(Docx4jDocxTemplateAutoConfiguration.class);

	@Bean
	public WordprocessingMLDocxTemplate wmlDocxTemplate(Docx4jDocxTemplateProperties templateProperties)
			throws IOException {
		WordprocessingMLDocxTemplate template = new WordprocessingMLDocxTemplate();
		template.setAutoDelete(templateProperties.isAutoDelete());
		template.setInputEncoding(templateProperties.getInputEncoding());
		template.setOutputEncoding(templateProperties.getOutputEncoding());
		template.setPlaceholderStart(templateProperties.getPlaceholderStart());
		template.setPlaceholderEnd(templateProperties.getPlaceholderEnd());
		return template;
	}

	@Bean
	@ConditionalOnMissingBean
	public ConversionHyperlinkHandler hyperlinkHandler() {
		return OutputConversionHyperlinkHandler.getHyperlinkHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public ConversionHTMLStyleElementHandler styleElementHandler() {
		return OutputConversionHTMLStyleElementHandler.getStyleElementHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public ConversionHTMLScriptElementHandler scriptElementHandler() {
		return OutputConversionHTMLScriptElementHandler.getScriptElementHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public WordprocessingMLPackageExtractor wmlPackageExtractor() {
		return WordprocessingMLPackageExtractor.getWMLPackageExtractor();
	}

	@Bean
	@ConditionalOnMissingBean
	public WordprocessingMLPackageWriter wmlPackageWriter() {
		return WordprocessingMLPackageWriter.getWMLPackageWriter();
	}

	@Bean
	@ConditionalOnMissingBean
	public WordprocessingMLTemplateWriter wmlTemplateWriter() {
		return WordprocessingMLTemplateWriter.getWMLTemplateWriter();
	}

}
