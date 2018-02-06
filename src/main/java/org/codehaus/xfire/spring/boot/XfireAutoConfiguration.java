package org.codehaus.xfire.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = XfireProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ XfireProperties.class })
public class XfireAutoConfiguration {
	
	/**
	1、首先定义CasConfiguration(loginUrl,prefixUrl),loginUrl为完整的cas登录地址,比如client项目的https://passport.sqzryang.com/login?service=https://client.sqzryang.com,prefixUrl则为cas路径前缀,根据cas的版本号拼接请求地址,用于验证sts是否正确并且返回登录成功后的信息。
	2、定义CasClient,property configuration(CasConfiguration) and callbackUrl(String),在pac4j中,每一个client相当于一种认证协议,比如我们需要weibo登录则应该配置一个WeiboClient,具体回掉的时候应该采用哪个client进行验证授权则需要下面配置的Clients
	3、定义Clients,在这里面可以定义你所有的Client以及默认的client还有关于如何区分回掉的哪个client应该取某个参数的配置,具体详细看源码
	4、定义Config,在config里面还有关于权限方面的配置以及session存储的一些配置,由于这部分我交给shiro去管理,所以只传入了clients即可
	5、以上四个都是pac4j的配置,接下来配置一个由buji-pac4j提供用于和shiro结合的filter:CallbackFilter,直接传入config即可
	6、定义好CallbackFilter以后,在ShiroFilterFactoryBean中注册好filters,并且配置好filterChainDefinitions
    *//*
	*//**
	 * 1、首先定义CasConfiguration(loginUrl,prefixUrl),loginUrl为完整的cas登录地址,
	 * 比如client项目的https://passport.sqzryang.com/login?service=https://client.sqzryang.com,
	 * prefixUrl则为cas路径前缀,根据cas的版本号拼接请求地址,用于验证sts是否正确并且返回登录成功后的信息。
	 
	CasConfiguration casConfiguration(String loginUrl,String prefixUrl) {
		return casConfiguration(loginUrl, prefixUrl);
	}*//*
	
	*//**
	 * 2、定义CasClient,property configuration(CasConfiguration) and callbackUrl(String),
	 * 在pac4j中,每一个client相当于一种认证协议,比如我们需要weibo登录则应该配置一个WeiboClient,
	 * 具体回掉的时候应该采用哪个client进行验证授权则需要下面配置的Clients
	 *//*
	
	

	*//**
	1、首先定义CasConfiguration(loginUrl,prefixUrl),loginUrl为完整的cas登录地址,比如client项目的https://passport.sqzryang.com/login?service=https://client.sqzryang.com,prefixUrl则为cas路径前缀,根据cas的版本号拼接请求地址,用于验证sts是否正确并且返回登录成功后的信息。
	2、定义CasClient,property configuration(CasConfiguration) and callbackUrl(String),在pac4j中,每一个client相当于一种认证协议,比如我们需要weibo登录则应该配置一个WeiboClient,具体回掉的时候应该采用哪个client进行验证授权则需要下面配置的Clients
	3、定义Clients,在这里面可以定义你所有的Client以及默认的client还有关于如何区分回掉的哪个client应该取某个参数的配置,具体详细看源码
	4、定义Config,在config里面还有关于权限方面的配置以及session存储的一些配置,由于这部分我交给shiro去管理,所以只传入了clients即可
	5、以上四个都是pac4j的配置,接下来配置一个由buji-pac4j提供用于和shiro结合的filter:CallbackFilter,直接传入config即可
	6、定义好CallbackFilter以后,在ShiroFilterFactoryBean中注册好filters,并且配置好filterChainDefinitions
    *//*
	*//**
	 * 1、首先定义CasConfiguration(loginUrl,prefixUrl),loginUrl为完整的cas登录地址,
	 * 比如client项目的https://passport.sqzryang.com/login?service=https://client.sqzryang.com,
	 * prefixUrl则为cas路径前缀,根据cas的版本号拼接请求地址,用于验证sts是否正确并且返回登录成功后的信息。
	 
	CasConfiguration casConfiguration(String loginUrl,String prefixUrl) {
		return casConfiguration(loginUrl, prefixUrl);
	}*//*
	
	*//**
	 * 2、定义CasClient,property configuration(CasConfiguration) and callbackUrl(String),
	 * 在pac4j中,每一个client相当于一种认证协议,比如我们需要weibo登录则应该配置一个WeiboClient,
	 * 具体回掉的时候应该采用哪个client进行验证授权则需要下面配置的Clients
	 *//*
	
	
	*//**
	 * 单点登录Session监听器
	 *//*
	@Bean("singleSignOutHttpSessionListener")
	@ConditionalOnMissingBean(name = "singleSignOutHttpSessionListener")
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener(XfireProperties properties) {  
    	ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>();
        listener.setEnabled(properties.isEnabled());  
        listener.setListener(new SingleSignOutHttpSessionListener());  
        listener.setOrder(1);  
        return listener;  
    }  
    
	*//**
	 * CAS SignOut Filter </br>
	 * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
	 *//*
	@Bean
	public FilterRegistrationBean singleSignOutFilter(XfireProperties properties) {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new SingleSignOutFilter());
		filterRegistration.setEnabled(properties.isEnabled());
		
		filterRegistration.addInitParameter(ConfigurationKeys.ARTIFACT_PARAMETER_NAME.getName(), properties.getArtifactParameterName());
		filterRegistration.addInitParameter(ConfigurationKeys.LOGOUT_PARAMETER_NAME.getName(), properties.getLogoutParameterName());
		filterRegistration.addInitParameter(ConfigurationKeys.RELAY_STATE_PARAMETER_NAME.getName(), properties.getRelayStateParameterName());
		filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), properties.getCasServerUrlPrefix());
		filterRegistration.addInitParameter(ConfigurationKeys.ARTIFACT_PARAMETER_OVER_POST.getName(), Boolean.toString(properties.isArtifactParameterOverPost()));
		filterRegistration.addInitParameter(ConfigurationKeys.EAGERLY_CREATE_SESSIONS.getName(), Boolean.toString(properties.isEagerlyCreateSessions()));
		
		filterRegistration.addUrlPatterns(properties.getSignOutFilterUrlPatterns());
		filterRegistration.setOrder(1);
		return filterRegistration;
	}
	
	
	*//**
	 * CAS Authentication Filter </br>
	 * 该过滤器负责用户的认证工作
	 *//*
	@Bean
	public FilterRegistrationBean authenticationFilter(XfireProperties properties){
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setEnabled(properties.isEnabled());  
		if(Protocol.SAML11.equals(properties.getProtocol())) {
			filterRegistration.setFilter(new Saml11AuthenticationFilter());
		}
		else {
			filterRegistration.setFilter(new AuthenticationFilter());
		}

		filterRegistration.addInitParameter(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS.getName(), properties.getAuthenticationRedirectStrategyClass());
		filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), properties.getCasServerLoginUrl());
		filterRegistration.addInitParameter(ConfigurationKeys.ENCODE_SERVICE_URL.getName(), Boolean.toString(properties.isEncodeServiceUrl()));
		filterRegistration.addInitParameter(ConfigurationKeys.GATEWAY.getName(), Boolean.toString(properties.isGateway()));
		filterRegistration.addInitParameter(ConfigurationKeys.GATEWAY_STORAGE_CLASS.getName(), properties.getGatewayStorageClass());
		filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_PATTERN.getName(), properties.getIgnorePattern());
		filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_URL_PATTERN_TYPE.getName(), properties.getIgnoreUrlPatternType().toString());
		filterRegistration.addInitParameter(ConfigurationKeys.RENEW.getName(), Boolean.toString(properties.isRenew()));
		filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), properties.getServerName());
		filterRegistration.addInitParameter(ConfigurationKeys.SERVICE.getName(), properties.getServiceUrl());
		
		filterRegistration.addUrlPatterns(properties.getAuthenticationFilterUrlPatterns());
		filterRegistration.setOrder(4);  
	    return filterRegistration;
	}
	
	*//**
	 * CAS Ticket Validation Filter </br>
	 * 该过滤器负责对Ticket的校验工作
	 *//*
	@Bean
	public FilterRegistrationBean ticketValidationFilter(XfireProperties properties ){
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setEnabled(properties.isEnabled()); 
		if(Protocol.CAS1.equals(properties.getProtocol())) {
			filterRegistration.setFilter(new Cas10TicketValidationFilter());
		}
		else if(Protocol.CAS2.equals(properties.getProtocol())) {
			
			filterRegistration.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
			
			// Cas20ProxyReceivingTicketValidationFilter
			filterRegistration.addInitParameter(ConfigurationKeys.ACCEPT_ANY_PROXY.getName(), Boolean.toString(properties.isAcceptAnyProxy()));
			filterRegistration.addInitParameter(ConfigurationKeys.ALLOWED_PROXY_CHAINS.getName(), properties.getAllowedProxyChains());
			filterRegistration.addInitParameter(ConfigurationKeys.ARTIFACT_PARAMETER_OVER_POST.getName(), Boolean.toString(properties.isArtifactParameterOverPost()));
			filterRegistration.addInitParameter(ConfigurationKeys.ARTIFACT_PARAMETER_NAME.getName(), properties.getArtifactParameterName());
			filterRegistration.addInitParameter(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS.getName(), properties.getAuthenticationRedirectStrategyClass());
			filterRegistration.addInitParameter(ConfigurationKeys.CIPHER_ALGORITHM.getName(), properties.getCipherAlgorithm());
			filterRegistration.addInitParameter(ConfigurationKeys.EAGERLY_CREATE_SESSIONS.getName(), Boolean.toString(properties.isEagerlyCreateSessions()));
			filterRegistration.addInitParameter(ConfigurationKeys.GATEWAY.getName(), Boolean.toString(properties.isGateway()));
			filterRegistration.addInitParameter(ConfigurationKeys.GATEWAY_STORAGE_CLASS.getName(), properties.getGatewayStorageClass());
			filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_CASE.getName(), Boolean.toString(properties.isIgnoreCase()));
			filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_PATTERN.getName(), properties.getIgnorePattern());
			filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_URL_PATTERN_TYPE.getName(), properties.getIgnoreUrlPatternType().toString());
			filterRegistration.addInitParameter(ConfigurationKeys.LOGOUT_PARAMETER_NAME.getName(), properties.getLogoutParameterName());
			filterRegistration.addInitParameter(ConfigurationKeys.MILLIS_BETWEEN_CLEAN_UPS.getName(), Long.toString(properties.getMillisBetweenCleanUps()));
			filterRegistration.addInitParameter(ConfigurationKeys.PROXY_RECEPTOR_URL.getName(), properties.getProxyReceptorUrl());
			filterRegistration.addInitParameter(ConfigurationKeys.PROXY_CALLBACK_URL.getName(), properties.getProxyCallbackUrl());
			filterRegistration.addInitParameter(ConfigurationKeys.PROXY_GRANTING_TICKET_STORAGE_CLASS.getName(), properties.getProxyGrantingTicketStorageClass());
			filterRegistration.addInitParameter(ConfigurationKeys.RELAY_STATE_PARAMETER_NAME.getName(), properties.getRelayStateParameterName());
			filterRegistration.addInitParameter(ConfigurationKeys.ROLE_ATTRIBUTE.getName(), properties.getRoleAttribute());
			filterRegistration.addInitParameter(ConfigurationKeys.SECRET_KEY.getName(), properties.getSecretKey());
			filterRegistration.addInitParameter(ConfigurationKeys.TICKET_VALIDATOR_CLASS.getName(), properties.getTicketValidatorClass());
			filterRegistration.addInitParameter(ConfigurationKeys.TOLERANCE.getName(), Long.toString(properties.getTolerance()));
			
		}
		else if(Protocol.CAS3.equals(properties.getProtocol())) {
			filterRegistration.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
		}
		else if(Protocol.SAML11.equals(properties.getProtocol())) {
			filterRegistration.setFilter(new Saml11TicketValidationFilter());
			// Saml11TicketValidationFilter
			filterRegistration.addInitParameter(ConfigurationKeys.TOLERANCE.getName(), Long.toString(properties.getTolerance()));
		}
		
		// Cas10TicketValidationFilter、Cas20ProxyReceivingTicketValidationFilter、Cas30ProxyReceivingTicketValidationFilter、Saml11TicketValidationFilter
		filterRegistration.addInitParameter(ConfigurationKeys.ENCODE_SERVICE_URL.getName(), Boolean.toString(properties.isEncodeServiceUrl()));
		filterRegistration.addInitParameter(ConfigurationKeys.ENCODING.getName(), properties.getEncoding());
		filterRegistration.addInitParameter(ConfigurationKeys.EXCEPTION_ON_VALIDATION_FAILURE.getName(), Boolean.toString(properties.isExceptionOnValidationFailure()));
		filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), properties.getCasServerLoginUrl());
		filterRegistration.addInitParameter(ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName(), properties.getCasServerUrlPrefix());
		filterRegistration.addInitParameter(ConfigurationKeys.HOSTNAME_VERIFIER.getName(), properties.getHostnameVerifier());
		filterRegistration.addInitParameter(ConfigurationKeys.HOSTNAME_VERIFIER_CONFIG.getName(), properties.getHostnameVerifierConfig());
		filterRegistration.addInitParameter(ConfigurationKeys.REDIRECT_AFTER_VALIDATION.getName(), Boolean.toString(properties.isRedirectAfterValidation()));
		filterRegistration.addInitParameter(ConfigurationKeys.RENEW.getName(), Boolean.toString(properties.isRenew()));
		filterRegistration.addInitParameter(ConfigurationKeys.SERVER_NAME.getName(), properties.getServerName());
		filterRegistration.addInitParameter(ConfigurationKeys.SERVICE.getName(), properties.getServiceUrl());
		filterRegistration.addInitParameter(ConfigurationKeys.SSL_CONFIG_FILE.getName(), properties.getSslConfigFile());
		filterRegistration.addInitParameter(ConfigurationKeys.USE_SESSION.getName(), Boolean.toString(properties.isUseSession()));
		
		filterRegistration.addUrlPatterns(properties.getTicketValidationFilterUrlPatterns());
		filterRegistration.setOrder(5);  
	    return filterRegistration;
	}
	
	*//**
	 * CAS HttpServletRequest Wrapper Filter </br>
	 * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
	 *//*
	@Bean
	public FilterRegistrationBean httpServletRequestWrapperFilter(XfireProperties properties ){
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new HttpServletRequestWrapperFilter());
		filterRegistration.setEnabled(properties.isEnabled()); 
		filterRegistration.addInitParameter(ConfigurationKeys.IGNORE_CASE.getName(), String.valueOf(properties.isIgnoreCase()));
		filterRegistration.addInitParameter(ConfigurationKeys.ROLE_ATTRIBUTE.getName(), properties.getRoleAttribute());
		filterRegistration.addUrlPatterns(properties.getRequestWrapperFilterUrlPatterns());
		filterRegistration.setOrder(6);  
	    return filterRegistration;
	}

	*//**
	 * CAS Assertion Thread Local Filter</br>
	 * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder 来获取用户的登录名。
	 * 比如 AssertionHolder.getAssertion().getPrincipal().getName()。
	 * 这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
	 *//*
	@Bean
	public FilterRegistrationBean assertionThreadLocalFilter(XfireProperties properties) {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new AssertionThreadLocalFilter());
		filterRegistration.setEnabled(properties.isEnabled());
		filterRegistration.addUrlPatterns(properties.getAssertionThreadLocalFilterUrlPatterns());
		filterRegistration.setOrder(7);  
		return filterRegistration;
	}*/
	
}
