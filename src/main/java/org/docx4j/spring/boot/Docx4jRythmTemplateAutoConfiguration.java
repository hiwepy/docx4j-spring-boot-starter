package org.docx4j.spring.boot;

import org.docx4j.Docx4J;
import org.docx4j.template.rythm.WordprocessingMLRythmTemplate;
import org.docx4j.template.xhtml.WordprocessingMLHtmlTemplate;
import org.rythmengine.RythmEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(Docx4jXhtmlTemplateAutoConfiguration.class)
@ConditionalOnClass({ Docx4J.class, RythmEngine.class , WordprocessingMLRythmTemplate.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class, Docx4jRythmTemplateProperties.class })
public class Docx4jRythmTemplateAutoConfiguration {

	@Bean
	public WordprocessingMLRythmTemplate wmlRythmTemplate(
			Docx4jProperties docx4jProperties,
			Docx4jRythmTemplateProperties templateProperties, 
			WordprocessingMLHtmlTemplate wmlHtmlTemplate,
			@Autowired(required = false) RythmEngine engine) {
		WordprocessingMLRythmTemplate template = new WordprocessingMLRythmTemplate(wmlHtmlTemplate);
		template.setEngine(engine);
		return template;
	}
	
}
