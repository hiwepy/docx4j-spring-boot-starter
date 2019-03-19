package org.docx4j.spring.boot;

import org.docx4j.Docx4J;
import org.docx4j.template.xhtml.WordprocessingMLHtmlTemplate;
import org.docx4j.template.xhtml.handler.DocumentHandler;
import org.docx4j.template.xhtml.handler.def.XHTMLDocumentHandler;
import org.docx4j.template.xhtml.io.WordprocessingMLPackageBuilder;
import org.jsoup.Jsoup;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(Docx4jDocxTemplateAutoConfiguration.class)
@ConditionalOnClass({ Docx4J.class, Jsoup.class, WordprocessingMLHtmlTemplate.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class, Docx4jXhtmlTemplateProperties.class })
public class Docx4jXhtmlTemplateAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public DocumentHandler documentHandler() {
		return XHTMLDocumentHandler.getDocumentHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public WordprocessingMLPackageBuilder wordMLPackageBuilder() {
		return WordprocessingMLPackageBuilder.getWMLPackageBuilder();
	}

	@Bean
	public WordprocessingMLHtmlTemplate wmlHtmlTemplate(Docx4jProperties docx4jProperties,
			Docx4jXhtmlTemplateProperties templateProperties, DocumentHandler documentHandler,
			WordprocessingMLPackageBuilder wordMLPackageBuilder) {
		WordprocessingMLHtmlTemplate template = new WordprocessingMLHtmlTemplate(docx4jProperties.isLandscape(),
				docx4jProperties.isAltChunk());
		template.setDocHandler(documentHandler);
		template.setWordMLPackageBuilder(wordMLPackageBuilder);
		return template;
	}

}
