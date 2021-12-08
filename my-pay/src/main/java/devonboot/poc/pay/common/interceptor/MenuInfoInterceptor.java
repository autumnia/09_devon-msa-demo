/*
 * Copyright ⓒ LG CNS, Inc. All rights reserved.
 *
 * Do Not Erase This Comment!!! (이 주석문을 지우지 말것)
 *
 * DevOn Framework의 클래스를 실제 프로젝트에 사용하는 경우 DevOn Framework 개발담당자에게
 * 프로젝트 정식명칭, 담당자 연락처(Email)등을 mail로 알려야 한다.
 *
 * 소스를 변경하여 사용하는 경우 DevOn Framework 개발담당자에게
 * 변경된 소스 전체와 변경된 사항을 알려야 한다.
 * 저작자는 제공된 소스가 유용하다고 판단되는 경우 해당 사항을 반영할 수 있다.
 *
 * (주의!) 원저자의 허락없이 재배포 할 수 없으며 LG CNS 외부로의 유출을 하여서는 안 된다.
 */
package devonboot.poc.pay.common.interceptor;

import devonframe.web.servlet.handler.UrlPatternInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 *
 * @author DevOn Framework, LG CNS,Inc., devon@lgcns.com
 * @version DevOnFrame 1.0.0
 * @since DevOnFrame 1.0.0
 */
public class MenuInfoInterceptor extends UrlPatternInterceptorAdapter {

    @Override
    public boolean checkHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	HttpSession session = request.getSession(true);
    	
    	String menu1 = null;
    	String menu2 = null;
    	String menu3 = null;
    	String menu4 = null;
		session.removeAttribute("MenuInfoInterceptor_menu1");
		session.removeAttribute("MenuInfoInterceptor_menu2");
		session.removeAttribute("MenuInfoInterceptor_menu3");
		session.removeAttribute("MenuInfoInterceptor_menu4");
    	
    	if(request.getParameter("menu1") != null){
    		menu1 = request.getParameter("menu1");
    	} else {
    		menu1 = (String) session.getAttribute("MenuInfoInterceptor_menu1");
    	} 
    	
    	if(request.getParameter("menu2") != null){
    		menu2 = request.getParameter("menu2");
    	} else {
    		menu2 = (String) session.getAttribute("MenuInfoInterceptor_menu2");
    	} 
    	
    	if(request.getParameter("menu3") != null){
    		menu3 = request.getParameter("menu3");
    	} else {
    		menu3 = (String) session.getAttribute("MenuInfoInterceptor_menu3");
    	} 

    	if(request.getParameter("menu4") != null){
    		menu4 = request.getParameter("menu4");
    	} else {
    		menu4 = (String) session.getAttribute("MenuInfoInterceptor_menu4");
    	} 

    	session.setAttribute("MenuInfoInterceptor_menu1", menu1);
    	session.setAttribute("MenuInfoInterceptor_menu2", menu2);
    	session.setAttribute("MenuInfoInterceptor_menu3", menu3);
    	session.setAttribute("MenuInfoInterceptor_menu4", menu4);
		 
        return true;
    }

}
