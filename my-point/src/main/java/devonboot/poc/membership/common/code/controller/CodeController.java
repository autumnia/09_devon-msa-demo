/*
 * @(#) CodeController.java
 *
 * Copyright 2012 by LG CNS, Inc.,
 * All rights reserved.
 *
 * Do Not Erase This Comment!!! (이 주석문을 지우지 말것)
 *
 * 본 클래스를 실제 프로젝트에 사용하는 경우 XXXX 프로젝트 담당자에게
 * 프로젝트 정식명칭, 담당자 연락처(Email)등을 mail로 알려야 한다.
 *
 * 소스를 변경하여 사용하는 경우 XXXX 프로젝트 담당자에게
 * 변경된 소스 전체와 변경된 사항을 알려야 한다.
 * 저작자는 제공된 소스가 유용하다고 판단되는 경우 해당 사항을 반영할 수 있다.
 *
 * (주의!) 원저자의 허락없이 재배포 할 수 없으며
 * LG CNS 외부로의 유출을 하여서는 안 된다.
 */
package devonboot.poc.membership.common.code.controller;

import devonboot.poc.membership.common.code.model.Code;
import devonboot.poc.membership.common.code.service.CodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 본 클래스는 코드 정보에 대한 CRUD를 담당하는 Controller 클래스입니다.
 * </pre>
 *
 * @author XXX팀 OOO
 */

@Controller
public class CodeController {

	@Resource(name = "codeService")
	private CodeService codeService;

	@RequestMapping(value = "/common/code/retrieveCode.do")
	public String retrieveCode(Code inputVO, ModelMap model) {
		Code codeVO = codeService.retrieveCode(inputVO);

		model.addAttribute("codeVO", codeVO);
		return "common/code/codeForm";
	}

	@RequestMapping(value = "/common/code/retrieveCodeList.do")
	public String retrieveCodeList(Code inputVO, ModelMap model) {
		List<Code> resultList = codeService.retrieveCodeList(inputVO);
		List<Code> codeGroupList = codeService.retrieveCodeGroupList();

		model.addAttribute("inputVO", inputVO);
		model.addAttribute("resultList", resultList);
		model.addAttribute("codeGroupList", codeGroupList);
		return "common/code/codeList";
	}

	@RequestMapping(value = "/common/code/retrieveCodeForm.do")
	public String retrieveCodeForm(ModelMap model) {
		model.addAttribute("codeVO", new Code());
		return "common/code/codeForm";
	}

	@RequestMapping(value = "/common/code/insertCode.do")
	public String insertCode(Code inputVO, ModelMap model) {
		codeService.insertCode(inputVO);
		return "redirect:/common/code/retrieveCodeList.do?codeGroup=" + inputVO.getCodeGroup() + "&msg=success";
	}

	@RequestMapping(value = "/common/code/updateCode.do")
	public String updateCode(Code inputVO, ModelMap model) {
		codeService.updateCode(inputVO);
		return "redirect:/common/code/retrieveCodeList.do?codeGroup=" + inputVO.getCodeGroup() + "&msg=success";
	}

	@RequestMapping(value = "/common/code/deleteCode.do")
	public String deleteCode(Code inputVO, ModelMap model) {
		codeService.deleteCode(inputVO);
		return "redirect:/common/code/retrieveCodeList.do?codeGroup=" + inputVO.getCodeGroup() + "&msg=success";
	}

	@RequestMapping(value = "/common/code/retrievedepartmentCodeList.do")
	public String retrieveDepartmentCodeList(Code input, ModelMap model) {
		String cg = input.getCodeGroup();
		List<Code> departmentCodeList = cg == null || cg.length() == 0 ? new ArrayList<Code>() : codeService.retrieveDepartmentCodeList(input);
		model.addAttribute("resultList", departmentCodeList);
		return "ajaxView";
	}
}
