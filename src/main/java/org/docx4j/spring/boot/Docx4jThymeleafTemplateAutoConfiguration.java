package org.docx4j.spring.boot;

import org.docx4j.Docx4J;
import org.docx4j.template.thymeleaf.WordprocessingMLThymeleafTemplate;
import org.docx4j.template.xhtml.WordprocessingMLHtmlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

@Configuration
@AutoConfigureAfter(Docx4jXhtmlTemplateAutoConfiguration.class)
@ConditionalOnClass({ Docx4J.class, TemplateEngine.class , WordprocessingMLThymeleafTemplate.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class, Docx4jThymeleafTemplateProperties.class })
public class Docx4jThymeleafTemplateAutoConfiguration {

	@Bean
	public WordprocessingMLThymeleafTemplate wmlThymeleafTemplate(
			Docx4jProperties docx4jProperties,
			Docx4jThymeleafTemplateProperties templateProperties, 
			WordprocessingMLHtmlTemplate wmlHtmlTemplate,
			@Autowired(required = false) TemplateEngine engine) {
		WordprocessingMLThymeleafTemplate template = new WordprocessingMLThymeleafTemplate(wmlHtmlTemplate);
		template.setEngine(engine);
		return template;
	}
	
}
