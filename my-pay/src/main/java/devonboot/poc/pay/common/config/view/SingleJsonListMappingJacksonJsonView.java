package devonboot.poc.pay.common.config.view;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;


public class SingleJsonListMappingJacksonJsonView extends MappingJackson2JsonView{

    @Override
    protected Object filterModel(Map<String, Object> model)
    {	
       Object result = super.filterModel(model);
       if (!(result instanceof Map))
       {
          return result;
       }
       
       @SuppressWarnings("rawtypes")
       Map map = (Map) result;
       if (map.size() == 1)
       {
          return map.values().toArray()[0];
       }
       return map;
    }

}
