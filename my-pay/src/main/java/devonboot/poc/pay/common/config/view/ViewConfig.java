package devonboot.poc.pay.common.config.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class ViewConfig {
	
	// Ajax Veiw 설정
	@Bean
	public MappingJackson2JsonView ajaxView() {
		return new MappingJackson2JsonView();
	}
	
	@Bean
	public SingleJsonListMappingJacksonJsonView singleJsonView() {
		return new SingleJsonListMappingJacksonJsonView();
	}
	
	@Bean
	public AjaxExceptionView ajaxErrorView() {
		return new AjaxExceptionView();
	}
	
}
