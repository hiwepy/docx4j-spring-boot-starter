package org.docx4j.spring.boot;

import org.docx4j.Docx4J;
import org.docx4j.template.webit.WordprocessingMLWebitTemplate;
import org.docx4j.template.xhtml.WordprocessingMLHtmlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import webit.script.Engine;

@Configuration
@AutoConfigureAfter(Docx4jXhtmlTemplateAutoConfiguration.class)
@ConditionalOnClass({ Docx4J.class, Engine.class , WordprocessingMLWebitTemplate.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class, Docx4jWebitTemplateProperties.class })
public class Docx4jWebitTemplateAutoConfiguration {

	@Bean
	public WordprocessingMLWebitTemplate wmlWebitTemplate(
			Docx4jProperties docx4jProperties,
			Docx4jWebitTemplateProperties templateProperties, 
			WordprocessingMLHtmlTemplate wmlHtmlTemplate,
			@Autowired(required = false) Engine engine) {
		WordprocessingMLWebitTemplate template = new WordprocessingMLWebitTemplate(wmlHtmlTemplate);
		template.setEngine(engine);
		return template;
	}
	
}
