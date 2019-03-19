package org.docx4j.spring.boot;

import javax.annotation.PostConstruct;

import org.docx4j.Docx4J;
import org.docx4j.events.Docx4jEvent;
import org.docx4j.spring.boot.event.linstener.ApplicationReadyFontMapperistener;
import org.docx4j.template.bus.error.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

@Configuration
@ConditionalOnClass({ Docx4J.class })
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class })
public class Docx4jAutoConfiguration {
	
	@Autowired
	protected MBassador<Docx4jEvent> eventbus;
	
	@PostConstruct
	public void bindEventBus() {
		Docx4J.setEventNotifier(eventbus);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public IPublicationErrorHandler errorHandler() {
		return new Slf4jLogger();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MBassador<Docx4jEvent> eventbus(IPublicationErrorHandler errorHandler) {
		return new MBassador<Docx4jEvent>(errorHandler);
	}
	
	@Bean
	public ApplicationReadyFontMapperistener fontMapperistener(Docx4jProperties docx4jProperties) {
		return new ApplicationReadyFontMapperistener(docx4jProperties);
	}
	
}
