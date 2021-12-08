package devonboot.poc.pay.common.config.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import devonframe.exception.BusinessException;
import devonframe.exception.DevonException;
import devonframe.exception.ExceptionResolverConstants;
import devonframe.exception.SystemException;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class AjaxExceptionView extends AbstractView {

    private String exceptionAttribute = ExceptionResolverConstants.DEFAULT_EXCEPTION_ATTRIBUTE;

    private String exceptionCodeAttribute = ExceptionResolverConstants.DEFAULT_EXCEPTION_CODE_ATTRIBUTE;

    /**
     * Set the exceptionAttribute
     *
     * @param exceptionAttribute
     */
    public void setExceptionAttribute(String exceptionAttribute) {
        this.exceptionAttribute = exceptionAttribute;
    }

    /**
     * Set the exceptionCodeAttribute
     *
     * @param exceptionCodeAttribute
     */
    public void setExceptionCodeAttribute(String exceptionCodeAttribute) {
        this.exceptionCodeAttribute = exceptionCodeAttribute;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Exception ex = (Exception) model.get(this.exceptionAttribute);

        if(ex != null) {
            String code = (String)model.get(this.exceptionCodeAttribute);
            String message = ex.getMessage();

            Map<String, Object> exceptionModel = new HashMap<String, Object>();
            exceptionModel.put("message", message);
            exceptionModel.put("error", "true");

            if(ex instanceof BusinessException) {
                //비즈니스 예외처리
                writeExceptionContent(response, HttpServletResponse.SC_OK, exceptionModel);
            } else if(ex instanceof DevonException) {
                //devframe 예외처리
                writeExceptionContent(response, HttpServletResponse.SC_OK, exceptionModel);
            } else if(ex instanceof SystemException) {
                //System 예외처리
                writeExceptionContent(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exceptionModel);
            } else {
                exceptionModel.put("code", code);
                writeExceptionContent(response, HttpServletResponse.SC_OK, exceptionModel);
            }

        }
    }

    protected void writeExceptionContent(HttpServletResponse response, int httpStatus, Map <String, Object> model) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(model);

        response.setCharacterEncoding("utf-8");
        response.setStatus(httpStatus);
        response.getWriter().write(jsonStr);
    }
}
