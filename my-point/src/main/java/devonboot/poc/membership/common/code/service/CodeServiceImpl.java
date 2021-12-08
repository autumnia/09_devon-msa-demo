/*
 * @(#) CodeServiceImpl.java
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
package devonboot.poc.membership.common.code.service;

import devonboot.poc.membership.common.code.model.Code;
import devonframe.dataaccess.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 본 클래스는 코드 정보에 대한 CRUD를 담당하는 ServiceImpl 클래스입니다.
 * </pre>
 *
 * @author XXX팀 OOO
 */

@Service("codeService")
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CommonDao commonDao;

	public Code retrieveCode(Code codeVO) {
		return commonDao.select("Code.retrieveCode", codeVO);
	}

	public List<Code> retrieveCodeList(Code codeVO) {
		return commonDao.selectList("Code.retrieveCodeList", codeVO);
	}

	public List<Code> retrieveCodeList(String codeGroup) {
		Code codeVO = new Code();
		codeVO.setCodeGroup(codeGroup);
		return commonDao.selectList("Code.retrieveCodeList", codeVO);
	}

	public List<Code> retrieveCodeGroupList() {
		return commonDao.selectList("Code.retrieveCodeGroupList");
	}

	public void insertCode(Code codeVO) {
		commonDao.insert("Code.insertCode", codeVO);
	}

	public void updateCode(Code codeVO) {
		commonDao.update("Code.updateCode", codeVO);
	}

	public void deleteCode(Code codeVO) {
		commonDao.delete("Code.deleteCode", codeVO);
	}

	public List<Code> retrieveSkillCodeList() {
		return commonDao.selectList("Code.retrieveSkillCodeList");
	}

	public List<Code> retrieveDivisionCodeList() {
		return commonDao.selectList("Code.retrieveDivisionCodeList");
	}

	public List<Code> retrieveDepartmentCodeList() {
		return commonDao.selectList("Code.retrieveDepartmentCodeList");
	}

	public List<Code> retrieveDepartmentCodeList(Code codeGroup) {
		return commonDao.selectList("Code.retrieveDepartmentCodeList", codeGroup);
	}

	public List<Code> retrieveJobLevelCodeList() {
		return commonDao.selectList("Code.retrieveJobLevelCodeList");
	}
}
